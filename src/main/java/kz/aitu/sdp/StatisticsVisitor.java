package kz.aitu.sdp;

public class StatisticsVisitor implements Visitor {
    private int fileCount = 0;
    private int directoryCount = 0;
    private int maxDepth = 0;
    private int currentDepth = 0;

    @Override
    public void visit(FileNode file) {
        if (currentDepth == 0) reset();
        fileCount++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }

    @Override
    public void visit(DirectoryNode directory) {
        if (currentDepth == 0) reset();
        directoryCount++;
        maxDepth = Math.max(maxDepth, currentDepth);

        currentDepth++;
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
        currentDepth--;
    }

    private void reset() {
        fileCount = 0;
        directoryCount = 0;
        maxDepth = 0;
    }

    public int getFileCount() { return fileCount; }
    public int getDirectoryCount() { return directoryCount; }
    public int getMaxDepth() { return maxDepth; }
}