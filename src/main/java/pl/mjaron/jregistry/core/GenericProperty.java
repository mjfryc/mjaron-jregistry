package pl.mjaron.jregistry.core;

/**
 * Basic implementation of IProperty.
 *
 * @param <T> Property value type.
 */
public class GenericProperty<T, S extends IPropertyValue> extends PropertyNode implements IPropertyValue<T, S> {

    private final ISerializer<T> serializer;
    private T defaultValue = null;

    public GenericProperty(final ISerializer<T> serializer) {
        this.serializer = serializer;
    }

    @SuppressWarnings("unchecked")
    public S getSelf() {
        return (S) this;
    }

    @Override
    public boolean hasValue() {
        return root.hasValue(this.path);
    }

    @Override
    public S cleanValue() {
        root.cleanValue(this.path);
        return getSelf();
    }

    @Override
    public T getValue() {
        if (!root.hasValue(this.path)) {
            return this.defaultValue;
        }
        return root.getValue(this.path, this.serializer);
    }

    @Override
    public S setValue(T what) {
        root.setValue(this.path, this.serializer, what);
        return getSelf();
    }

    @Override
    public S setDefault(final T what) {
        this.defaultValue = what;
        return getSelf();
    }

    @Override
    public String toString() {
        return this.getPath().toString() + ":" + this.getValue();
    }

}
