package pl.mjaron.jregistry.core;

import java.util.ArrayList;
import java.util.List;

public class IO {

    private final IStorage s;
    private final IProperty p;
    ICriticalSection cs;

    private String defaultValueText = null;
    private boolean enumOnly = false;
    private List<String> enums = null;

    public IO(final IStorage storage, IProperty property, ICriticalSection section) {
        this.s = storage;
        this.p = property;
        this.cs = section;
    }

    /**
     * Tells whether given property has any value set in persistent storage.
     *
     * @return True if given property is stored in persistent storage.
     */
    boolean hasValue() {
        return cs.withLock(() -> s.hasValue(p.getPath()));
    }

    /**
     * Removes value of this property. It is not longer stored in persistent storage.
     * Post condition: hasValue() returns false.
     */
    void cleanTextValue() {
        cs.withLock(() -> {
            s.cleanValue(p.getPath());
            return null;
        });
    }

    /**
     * All properties are stored as text.
     * Provides raw value data stored as text.
     *
     * @return Text value of property.
     */
    String getTextValue() {
        return cs.withLock(() -> s.getValue(p.getPath()));
    }

    /**
     * All properties are stored as text.
     * Allows to set raw value data stored as text.
     *
     * @param textValue Value stored as text.
     */
    void setTextValue(final String textValue) {
        if (this.enumOnly && !this.enums.contains(textValue)) {
            throw new RuntimeException("Bad enum value: [" + textValue + "], available values: " + this.enums);
        }
        cs.withLock(() -> {
            s.setValue(p.getPath(), textValue);
            return null;
        });
    }

    /**
     * @return Default value in text format.
     */
    String getDefaultText() {
        return defaultValueText;
    }

    /**
     * @param what Sets default text value if property is missing.
     */
    void setDefaultText(final String what) {
        defaultValueText = what;
    }

    /**
     * @return True if enums are accepted only.
     */
    boolean isEnumOnly() {
        return enumOnly;
    }

    /**
     * @param enumOnly If true, only enums will be accepted.
     *                 If false, other values will be also tolerated.
     * @return This object.
     */
    void setEnumOnly(final boolean enumOnly) {
        this.enumOnly = enumOnly;
    }

    /**
     * Inserts enum value as text.
     *
     * @param enumTextValue
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

