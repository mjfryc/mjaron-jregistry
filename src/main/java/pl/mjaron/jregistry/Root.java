package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.*;

import java.util.Map;

public class Root extends Node implements IRoot {

    public Root(final IStorage storage) {
        this.path = new PropertyPath("");
        this.storage = storage;
    }

    @Override
    public PropertyPath getPath() {
        return this.path;
    }

    @Override
    public IStorage getRoot() {
        return this;
    }

    private void doAccept(final IRegistryVisitor visitor, final IPropertyNode node) {
        for (final Map.Entry<String, IPropertyNode> child : node.getChildren().entrySet()) {
            if (child.getValue().isNode()) {
                doAccept(visitor, child.getValue());
            } else {
                visitor.visit(child.getValue().getPath().toString(), child.getValue().getTextValue());
            }
        }
    }

    @Override
    public void accept(final IRegistryVisitor visitor) {
        this.doAccept(visitor, this);
    }

    @Override
    public boolean hasValue(PropertyPath path) {
        return storage.hasValue(path);
    }

    @Override
    public void cleanValue(PropertyPath path) {
        storage.cleanValue(path);
    }

    @Override
    public String getValue(final PropertyPath path) {
        return storage.getValue(path);
    }

    @Override
    public void setValue(final PropertyPath path, final String what) {
        storage.setValue(path, what);
    }

    private final IStorage storage;
}
