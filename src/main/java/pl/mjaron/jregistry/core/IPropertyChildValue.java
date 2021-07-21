package pl.mjaron.jregistry.core;

public interface IPropertyChildValue {
    /**
     * Tells whether given child has any value set in persistent storage.
     *
     * @return True if given child is stored in persistent storage.
     */
    boolean hasValue(PropertyPath path);

    /**
     * Removes value of child property. It is not longer stored in persistent storage.
     * Post condition: hasValue(path) returns false.
     *
     * @param path Route to the child property.
     */
    void cleanValue(PropertyPath path);

    /**
     * Provides value related with given child property.
     *
     * @param path Route to the child property.
     * @return Value of child property or throws exception.
     */
    <U> U getValue(PropertyPath path, ISerializer<U> serializer);

    /**
     * Allows to change property's value if it is possible.
     *
     * @param what Object which will be set as a property value.
     * @param path Route to the child property.
     */
    <U> void setValue(PropertyPath path, ISerializer<U> serializer, U what);

    void accept(final IRegistryVisitor visitor);
}
