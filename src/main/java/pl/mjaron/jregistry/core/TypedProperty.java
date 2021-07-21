package pl.mjaron.jregistry.core;

/**
 * Basic implementation of IProperty.
 *
 * @param <T> Property value type.
 */
public class TypedProperty<T, S extends TypedProperty> extends PropertyNode<TypedProperty> implements IPropertyTypedValue<T, S> {

    private final ISerializer<T> serializer;
    private String defaultValueText = null;

    public TypedProperty(final ISerializer<T> serializer) {
        this.serializer = serializer;
    }

    @SuppressWarnings("unchecked")
    public S getSelf() {
        return (S) this;
    }

    public T getDefaultValue() {
        return this.serializer.fromStr(this.defaultValueText);
    }

    @Override
    public T getValue() {
        if (!this.hasValue()) {
            return this.getDefaultValue();
        }
        return this.serializer.fromStr(this.getTextValue());
    }

    @Override
    public S setValue(T what) {
        this.setTextValue(this.serializer.toStr(what));
        return getSelf();
    }

    @Override
    public S setDefault(final T what) {
        this.defaultValueText = serializer.toStr(what);
        return getSelf();
    }

    @Override
    public String toString() {
        return this.getPath().toString() + ":" + this.getValue();
    }

    @Override
    public boolean isNode() {
        return false;
    }

    @Override
    public void cleanTextValue() {
        this.root.cleanValue(this.path);
    }
}
