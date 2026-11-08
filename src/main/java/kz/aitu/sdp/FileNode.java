package kz.aitu.sdp;

public class FileNode implements Element {
    private final String name;
    private final long size;
    private DirectoryNode parent;

    public FileNode(String name, long size) {
        if (name == null || name.isBlank() || name.contains("/")) {
            throw new IllegalArgumentException("Invalid file name");
        }
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() { return name; }

    public long getSize() { return size; }

    @Override
    public DirectoryNode getParent() { return parent; }

    @Override
    public void setParent(DirectoryNode parent) { this.parent = parent; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this); // Double dispatch step 2
    }
}