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
    S cleanValue();

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
    S setValue(final T what);

    /**
     * This value will be returned if value is missing.
     *
     * @param what Alternative value if there is no value stored.
     * @return Stored value or what if there is no stored value.
     */
    S setDefault(final T what);
}
