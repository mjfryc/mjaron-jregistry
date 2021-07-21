package pl.mjaron.jregistry2.core;

/**
 * Basic implementation of IProperty.
 *
 * @param <T> Property value type.
 */
public class GenericProperty<T, S extends IPropertyValue> extends PropertyNode implements IPropertyValue<T, S>, IPropertyChildValue {

    public GenericProperty(final ISerializer<T> serializer) {
        this.serializer = serializer;
    }

    @Override
    public boolean hasValue() {
        return root.hasValue(this.path);
    }

    @Override
    public boolean hasValue(PropertyPath path) {
        return root.hasValue(this.path.plus(path));
    }

    @Override
    public void cleanValue() {
        root.cleanValue(this.path);
    }

    @Override
    public void cleanValue(PropertyPath path) {
        root.cleanValue(this.path.plus(path));
    }

    @Override
    public T getValue() {
        return root.getValue(this.path, this.serializer);
    }

    @Override
    public S setValue(T what) {
        root.setValue(this.path, this.serializer, what);
        return (S)this;
    }

    @Override
    public <U> U getValue(PropertyPath path, ISerializer<U> serializer) {
        return root.getValue(this.path.plus(path), serializer);
    }

//    @Override
//    public <S extends IPropertyValue<T> > setValue(T what) {
//        root.setValue(this.path, this.serializer, what);
//        return this;
//    }

    @Override
    public <U> void setValue(PropertyPath path, ISerializer<U> serializer, U what) {
        root.setValue(this.path.plus(path), serializer, what);
    }

    @Override
    public String toString() {
        return this.getPath().toString() + ":" + this.getValue();
    }

    private final ISerializer<T> serializer;

}
