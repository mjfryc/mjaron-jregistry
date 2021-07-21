package pl.mjaron.jregistry.core;

import java.util.Map;
import java.util.TreeMap;

/**
 * Stores all data in RAM only.
 * Usually for test purposes.
 */
public class MemoryStorage implements IStorage {

    @Override
    public boolean hasValue(final PropertyPath path) {
        return values.containsKey(path.toString());
    }

    @Override
    public void cleanValue(final PropertyPath path) {
        values.remove(path.toString());
    }

    @Override
    public String getValue(final PropertyPath path) {
        return values.get(path.toString());
    }

    @Override
    public void setValue(final PropertyPath path, final String what) {
        values.put(path.toString(), what);
    }

    private Map<String, String> values = new TreeMap<>();
}
