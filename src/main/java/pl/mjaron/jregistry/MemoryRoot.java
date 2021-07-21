package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Stores all data in RAM only.
 */
public class MemoryRoot extends Node implements IRoot {

    private Map<String, String> values = new TreeMap<>();

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

    @Override
    public void accept(final IRegistryVisitor visitor) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            visitor.visit(entry.getKey(), entry.getValue());
        }
    }

}
