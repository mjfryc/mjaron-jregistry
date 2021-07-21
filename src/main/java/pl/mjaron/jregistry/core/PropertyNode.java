package pl.mjaron.jregistry.core;

import java.util.Map;
import java.util.TreeMap;

public abstract class PropertyNode<S extends PropertyNode> implements IPropertyNode, IPropertyTextValue {

    private final Map<String, IPropertyNode> children = new TreeMap<>();
    protected PropertyPath path = null;
    protected IStorage root = null;
    /**
     * Reference to the parent. Set by onCreate().
     */
    private IPropertyNode parent = null;
    private String name = null;

    @SuppressWarnings("unchecked")
    private S getSelf() {
        return (S) this;
    }

    @Override
    public IPropertyNode getParent() {
        return parent;
    }

    @Override
    public Map<String, IPropertyNode> getChildren() {
        return children;
    }

    @Override
    public <ChildT extends IPropertyNode> ChildT add(final String name, final ChildT child) {
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
    public IStorage getRoot() {
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

    @Override
    public boolean hasValue() {
        return root.hasValue(this.path);
    }

    @Override
    public void cleanTextValue() {
        root.cleanValue(this.path);
    }

    @Override
    public String getTextValue() {
        return root.getValue(this.path);
    }

    @Override
    public void setTextValue(String textValue) {
        root.setValue(this.path, textValue);
    }
}
