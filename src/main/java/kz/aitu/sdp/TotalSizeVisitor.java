package kz.aitu.sdp;

public class TotalSizeVisitor implements Visitor {
    private long totalSize = 0;
    private int depth = 0;

    @Override
    public void visit(FileNode file) {
        if (depth == 0) totalSize = 0; // Reset if starting on a single file
        totalSize += file.getSize();
    }

    @Override
    public void visit(DirectoryNode directory) {
        if (depth == 0) totalSize = 0; // Reset at root so repeat runs don't multiply state
        depth++;
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
        depth--;
    }

    public long getTotalSize() { return totalSize; }
}