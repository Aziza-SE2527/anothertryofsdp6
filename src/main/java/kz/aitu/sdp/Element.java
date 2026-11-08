package kz.aitu.sdp;

public interface Element
{
    String getName();
    DirectoryNode getParent();
    void setParent(DirectoryNode parent);
    void accept(Visitor visitor);
}