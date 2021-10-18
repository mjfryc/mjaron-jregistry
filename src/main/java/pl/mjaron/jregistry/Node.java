package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node extends PropertyBase {
    private final ILegibleStorage storage;
    private final ICriticalSection section;
    private ArrayList<IProperty> children = null;

    public Node(IProperty parent, String name) {
        super(parent, name);
        this.storage = getRoot().getStorage();
        this.section = getRoot().getCriticalSection();
        parent.onChildCreated(this);
    }

    public Node(ILegibleStorage storage, ICriticalSection section) {
        super("root", PropertyPath.emptyPath());
        this.storage = storage;
        this.section = section;
    }

    @Override
    public ILegibleStorage getStorage() {
        return storage;
    }

    @Override
    public ICriticalSection getCriticalSection() {
        return section;
    }

    @Override
    public Type getType() {
        return Type.NODE;
    }

    @Override
    public String toString() {
        return this.getPath().toString() + ".*";
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
    public LegibleIO getLegibleIO() {
        return null; // This property doesn't use IO because it doesn't have a value.
    }

    @Override
    public BlobIO getBlobIO() {
        return null;
    }
}
