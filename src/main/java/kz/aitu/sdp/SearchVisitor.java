package kz.aitu.sdp;

import java.util.*;

public class SearchVisitor implements Visitor {
    private final String keyword;
    private final long minSize;
    private final List<String> matches = new ArrayList<>();
    private final Deque<String> pathStack = new ArrayDeque<>();
    private int depth = 0;

    public SearchVisitor(String keyword, long minSize) {
        if (keyword == null || minSize < 0) throw new IllegalArgumentException("Invalid search criteria");
        this.keyword = keyword;
        this.minSize = minSize;
    }

    @Override
    public void visit(FileNode file) {
        if (depth == 0) {
            matches.clear();
            pathStack.clear();
        }
        if (file.getName().contains(keyword) && file.getSize() >= minSize) {
            String prefix = String.join("/", pathStack);
            matches.add(prefix.isEmpty() ? file.getName() : prefix + "/" + file.getName());
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        boolean isRoot = (depth == 0);
        if (isRoot) {
            matches.clear();
            pathStack.clear();
        } else {
            pathStack.addLast(directory.getName());
        }

        depth++;
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
        depth--;

        if (!isRoot) {
            pathStack.removeLast();
        } else {
            Collections.sort(matches); // Deterministic lexicographical order
        }
    }

    public List<String> getMatches() {
        return Collections.unmodifiableList(matches);
    }
}