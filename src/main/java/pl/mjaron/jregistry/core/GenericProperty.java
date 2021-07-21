package pl.mjaron.jregistry.core;

/**
 * Basic implementation of IProperty.
 *
 * @param <T> Property value type.
 */
public class GenericProperty<T, S extends IPropertyValue> extends PropertyNode implements IPropertyValue<T, S> {

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
    public void cleanValue() {
        root.cleanValue(this.path);
    }

    @Override
    public T getValue() {
        return root.getValue(this.path, this.serializer);
    }


    @Override
    public S setValue(T what) {
        root.setValue(this.path, this.serializer, what);
        return getSelf();
    }

    @Override
    public String toString() {
        return this.getPath().toString() + ":" + this.getValue();
    }

    private final ISerializer<T> serializer;

}
