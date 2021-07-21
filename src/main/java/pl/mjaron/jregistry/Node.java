package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.IPropertyVisitor;
import pl.mjaron.jregistry.core.IStorage;
import pl.mjaron.jregistry.core.PropertyPath;
import pl.mjaron.jregistry.core.IProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements IProperty {

    private IProperty parent;
    private String name;
    private PropertyPath path;
    private IStorage storage;
    private ArrayList<IProperty> children = null;

    public Node(IProperty parent, String name) {
        this.parent = parent;
        this.name = name;
        this.path = parent.getPath().plus(name);
        this.storage = parent.getStorage();
        parent.onChildCreated(this);
    }

    public Node(IStorage storage) {
        this.parent = null;
        this.name = "root";
        this.path = new PropertyPath("");
        this.storage = storage;
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
    public IStorage getStorage() {
        return storage;
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
    public void accept(IPropertyVisitor visitor) {
        IPropertyVisitor.accept(this, visitor);
    }
}
