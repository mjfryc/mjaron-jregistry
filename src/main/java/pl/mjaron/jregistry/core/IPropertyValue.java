package pl.mjaron.jregistry.core;

public interface IPropertyValue<T, S extends IPropertyValue> {
    /**
     * Tells whether given property has any value set in persistent storage.
     *
     * @return True if given property is stored in persistent storage.
     */
    boolean hasValue();

    /**
     * Removes value of this property. It is not longer stored in persistent storage.
     * Post condition: hasValue() returns false.
     */
    void cleanValue();

    /**
     * Provides value related with this property.
     *
     * @return Value of this property or throws exception.
     */
    T getValue();

    /**
     * Allows to change property's value if it is possible.
     *
     * @param what Object which will be set as a property value.
     */
    S setValue(T what);
}
