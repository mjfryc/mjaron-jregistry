package pl.mjaron.jregistry.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for storing all properties in text format.
 */
public class LegibleIO {

    private final ILegibleStorage s;
    private final PropertyPath path;
    ICriticalSection cs;

    private String defaultValueText = null;
    private boolean enumOnly = false;
    private List<String> enums = null;

    public LegibleIO(final ILegibleStorage storage, IProperty property, ICriticalSection section) {
        this.s = storage;
        this.path = property.getPath();
        this.cs = section;
    }

    /**
     * Tells whether given property has any value set in persistent storage.
     *
     * @return True if given property is stored in persistent storage.
     */
    public boolean hasValue() {
        return cs.withLock(() -> s.hasValue(path));
    }

    /**
     * Removes value of this property. It is not longer stored in persistent storage.
     * Post condition: hasValue() returns false.
     */
    public void cleanTextValue() {
        cs.withLock(() -> {
            s.cleanValue(path);
            return null;
        });
    }

    /**
     * All properties are stored as text.
     * Provides raw value data stored as text.
     *
     * @return Text value of property.
     */
    public String getTextValue() {
        return cs.withLock(() -> s.getValue(path));
    }

    /**
     * All properties are stored as text.
     * Allows to set raw value data stored as text.
     *
     * @param textValue Value stored as text.
     */
    public void setTextValue(final String textValue) {
        if (this.enumOnly && !this.enums.contains(textValue)) {
            throw new RuntimeException("Bad enum value: [" + textValue + "], available values: " + this.enums);
        }
        cs.withLock(() -> {
            s.setValue(path, textValue);
            return null;
        });
    }

    /**
     * @return Default value in text format.
     */
    public String getDefaultText() {
        return defaultValueText;
    }

    /**
     * @param what Sets default text value if property is missing.
     */
    public void setDefaultText(final String what) {
        defaultValueText = what;
    }

    /**
     * @return True if enums are accepted only.
     */
    public boolean isEnumOnly() {
        return enumOnly;
    }

    /**
     * @param enumOnly If true, only enums will be accepted.
     *                 If false, other values will be also tolerated.
     */
    public void setEnumOnly(final boolean enumOnly) {
        this.enumOnly = enumOnly;
    }

    /**
     * Inserts enum value as text.
     *
     * @param enumTextValue Text representation of enumeration.
     */
    public void addEnumText(final String enumTextValue) {
        if (enums == null) {
            enums = new ArrayList<>();
        }
        enums.add(enumTextValue);
    }

    /**
     * @return List of all added enums as text.
     */
    public List<String> getEnumTexts() {
        if (enums == null) {
            return List.of();
        }
        return enums;
    }

}

