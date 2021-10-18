package pl.mjaron.jregistry.core;

public interface ILegibleStorage {
    /**
     * Tells whether given child has any value set in persistent storage.
     *
     * @return True if given child is stored in persistent storage.
     */
    boolean hasValue(final PropertyPath path);

    /**
     * Removes value of child property. It is no longer stored in persistent storage.
     * Post condition: hasValue(path) returns false.
     *
     * @param path Route to the child property.
     */
    void cleanValue(final PropertyPath path);

    /**
     * Provides value related with given child property.
     *
     * @param path Route to the child property.
     * @return Value of child property or throws exception.
     */
    String getValue(final PropertyPath path);

    /**
     * Allows changing property's value if it is possible.
     *
     * @param what Object which will be set as a property value.
     * @param path Route to the child property.
     */
    void setValue(final PropertyPath path, final String what);
}
