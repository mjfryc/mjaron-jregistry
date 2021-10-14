package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements IProperty {

    private final IProperty parent;
    private final String name;
    private final IProperty root;
    private final PropertyPath path;
    private final IStorage storage;
    private final ICriticalSection section;
    private ArrayList<IProperty> children = null;

    public Node(IProperty parent, String name) {
        this.parent = parent;
        this.name = name;
        this.root = parent.getRoot();
        this.path = parent.getPath().plus(name);
        this.storage = root.getStorage();
        this.section = root.getCriticalSection();
        parent.onChildCreated(this);
    }

    public Node(IStorage storage, ICriticalSection section) {
        this.parent = null;
        this.name = "root";
        this.path = new PropertyPath("");
        this.storage = storage;
        this.section = section;
        this.root = this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyPath getPath() {
        return path;
    }

    @Override
    public IProperty getParent() {
        return parent;
    }

    @Override
    public IProperty getRoot() {
        return root;
    }

    @Override
    public IStorage getStorage() {
        return storage;
    }

    @Override
    public ICriticalSection getCriticalSection() {
        return section;
    }

    @Override
    public boolean isNode() {
        return true;
    }

    @Override
    public String toString() {
        return this.path.toString() + ".*";
    }

    @Override
    public void onChildCreated(IProperty child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    @Override
    public List<IProperty> getChildren() {
        return Objects.requireNonNullElseGet(children, List::of);
    }

    @Override
    public <T extends IPropertyVisitor> T accept(T visitor) {
        return IPropertyVisitor.accept(this, visitor);
    }

    @Override
    public IO getIO() {
        return null; // This property doesn't use IO because it doesn't have a value.
    }
}
