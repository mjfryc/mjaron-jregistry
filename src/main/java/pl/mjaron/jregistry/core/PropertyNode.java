package pl.mjaron.jregistry.core;

import java.util.Map;
import java.util.TreeMap;

public class PropertyNode implements IPropertyNode {

    private final Map<String, IPropertyNode> children = new TreeMap<>();
    protected PropertyPath path = null;
    protected IPropertyChildValue root = null;
    /**
     * Reference to the parent. Set by onCreate().
     */
    private IPropertyNode parent = null;
    private String name = null;

    @Override
    public IPropertyNode getParent() {
        return parent;
    }

    @Override
    public Map<String, IPropertyNode> getChildren() {
        return children;
    }

    @Override
    public <ChildT extends IPropertyNode> ChildT add(String name, ChildT child) {
        this.children.put(name, child);
        child.onCreate(name, this);
        return child;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public PropertyPath getPath() {
        return this.path;
    }

    @Override
    public IPropertyChildValue getRoot() {
        return this.root;
    }

    @Override
    public int getLevel() {
        return parent.getLevel() + 1;
    }

    @Override
    public void onCreate(final String name, final IPropertyNode parent) {
        this.name = name;
        this.parent = parent;
        this.path = parent.getPath().plus(name);
        this.root = parent.getRoot();
    }
}
