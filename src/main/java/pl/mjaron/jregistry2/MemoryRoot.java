package pl.mjaron.jregistry2;

import pl.mjaron.jregistry2.core.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Stores all data in RAM only.
 */
public class MemoryRoot extends PropertyNode implements IRoot {

    public MemoryRoot() {
        this.path = new PropertyPath("");
    }

    @Override
    public PropertyPath getPath() {
        return this.path;
    }

    @Override
    public IPropertyChildValue getRoot() {
        return this;
    }

    @Override
    public boolean hasValue(PropertyPath path) {
        return values.containsKey(path.toString());
    }

    @Override
    public void cleanValue(PropertyPath path) {
        values.remove(path.toString());
    }

    @Override
    public <U> U getValue(PropertyPath path, ISerializer<U> serializer) {
        return serializer.fromStr(values.get(path.toString()));
    }

    @Override
    public <U> void setValue(PropertyPath path, ISerializer<U> serializer, U what) {
        values.put(path.toString(), serializer.toStr(what));
    }

    private Map<String, String> values = new TreeMap<>();

}
