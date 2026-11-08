package kz.aitu.sdp;

import java.util.*;

public class DirectoryNode implements Element {
    private final String name;
    private final Map<String, Element> children = new LinkedHashMap<>();
    private DirectoryNode parent;

    public DirectoryNode(String name) {
        if (name == null || name.isBlank() || name.contains("/")) {
            throw new IllegalArgumentException("Invalid directory name");
        }
        this.name = name;
    }

    public void addChild(Element child) {
        if (child == null) throw new IllegalArgumentException("Child cannot be null");
        if (child == this) throw new IllegalArgumentException("Cannot add directory to itself");
        if (child.getParent() != null) throw new IllegalStateException("Element already has a parent");
        if (createsCycle(child)) throw new IllegalArgumentException("Cycle detected");
        if (children.containsKey(child.getName())) throw new IllegalArgumentException("Duplicate name");

        children.put(child.getName(), child);
        child.setParent(this);
    }

    private boolean createsCycle(Element candidate) {
        DirectoryNode curr = this.parent;
        while (curr != null) {
            if (curr == candidate) return true;
            curr = curr.getParent();
        }
        return false;
    }

    public Collection<Element> getChildren() {
        return Collections.unmodifiableCollection(children.values());
    }

    @Override
    public String getName() { return name; }

    @Override
    public DirectoryNode getParent() { return parent; }

    @Override
    public void setParent(DirectoryNode parent) { this.parent = parent; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this); // Double dispatch step 2
    }
}