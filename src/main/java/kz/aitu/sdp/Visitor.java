package kz.aitu.sdp;

public interface Visitor
{
    void visit(FileNode file);
    void visit(DirectoryNode directory);
}