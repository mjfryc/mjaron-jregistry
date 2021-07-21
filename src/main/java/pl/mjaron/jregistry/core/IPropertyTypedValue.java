package pl.mjaron.jregistry.core;

public interface IPropertyTypedValue<T, S extends IPropertyTypedValue> extends IPropertyTextValue {

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
