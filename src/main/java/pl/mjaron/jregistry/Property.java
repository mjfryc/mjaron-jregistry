package pl.mjaron.jregistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties are stored in registry.
 * Property is responsible for notifying registry about property value change.
 * Property doesn't contain a value but fetches it updated value from registry.
 *
 * @param <T> Type of data represented by this property.
 */
public abstract class Property<T> {

    public Property(final ISerializer<T> serializer) {
        this.serializer = serializer;
    }

    /**
     * Must be called internally by IRegistryOperations. User shouldn't call it directly.
     * @param registry
     * @param path
     */
    public void implementationInitialize(final IRegistryOperations registry, final PropertyPath path) {
        this.registry = registry;
        this.path = path;
    }

    <T> T add(T property, String node) {
        this.registry.add(property, this.path.plus(node));
        return property;
    }

    /**
     * @return This property location in registry.
     */
    public PropertyPath getPath() {
        return this.path;
    }

    /**
     * @return True if this property exists in registry.
     */
    public boolean exists() {
        return registry.has(this.path);
    }

    /**
     * @return Value of this property.
     */
    public T get() {
        final String sValue = this.registry.get(this.path);
        return serializer.fromStr(sValue);
    }

    /**
     * @param defaultValue It will be returned if registry doesn't contain this property.
     * @return Value of this property or defaultValue if registry doesn't contain value.
     */
    public T getOr(final T defaultValue) {
        if (this.exists()) {
            return this.get();
        } else {
            return defaultValue;
        }
    }

    /**
     * @return null if value doesn't exist.
     */
    public T getOrNull() {
        return this.getOr(null);
    }

    public void set(T what) {
        final String sValue = serializer.toStr(what);
        this.registry.set(this.path, sValue);
    }

    public void remove() {
        this.registry.remove(this.path);
    }

    public Property<T> setReadOnly(final boolean value) {
        this.readOnly = value;
        return this;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public Property<T> setDefaultValue(final T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public String getDefaultValueString() {
        return serializer.toStr(this.defaultValue);
    }

    public Property<T> setEnumOnly(final boolean value) {
        this.enumOnly = value;
        return this;
    }

    public boolean isEnumOnly() {
        return this.enumOnly;
    }

    public Property<T> addEnum(final T value) {
        this.enumValues.add(value);
        return this;
    }

    public List<T> getEnumValues() {
        return this.enumValues;
    }

    public List<String> getEnumValuesStrings() {
        ArrayList<String> enums = new ArrayList<>();
        for (final T entry : this.enumValues) {
            enums.add(serializer.toStr(entry));
        }
        return enums;
    }

    private final ISerializer<T> serializer;
    private IRegistryOperations registry;
    private PropertyPath path;
    private boolean readOnly = false;
    private T defaultValue = null;
    private boolean enumOnly = false;
    private final ArrayList<T> enumValues = new ArrayList<>();
}
