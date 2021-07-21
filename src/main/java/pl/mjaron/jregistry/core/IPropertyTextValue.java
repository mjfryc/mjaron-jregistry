package pl.mjaron.jregistry.core;

public interface IPropertyTextValue {

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
    void cleanTextValue();

    /**
     * All properties are stored as text.
     * Provides raw value data stored as text.
     * @return Text value of property.
     */
    String getTextValue();

    /**
     * All properties are stored as text.
     * Allows to set raw value data stored as text.
     * @param textValue Value stored as text.
     */
    void setTextValue(final String textValue);

}

