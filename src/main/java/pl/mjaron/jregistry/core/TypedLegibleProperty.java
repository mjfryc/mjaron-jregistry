package pl.mjaron.jregistry.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypedLegibleProperty<T, S extends TypedLegibleProperty<T, S>> extends PropertyBase {


    private final ISerializer<T> serializer;
    private final LegibleIO legibleIo;
    private ArrayList<IProperty> children = null;

    @SuppressWarnings("unchecked")
    private S self() {
        return (S) this;
    }

    /**
     * Common property implementation.
     *
     * @param parent     Parent property or node.
     * @param name       Name of single node.
     * @param serializer Converter from / to string.
     */
    public TypedLegibleProperty(IProperty parent, String name, ISerializer<T> serializer) {
        super(parent, name);
        this.serializer = serializer;
        this.legibleIo = new LegibleIO(this.getStorage(), this, this.getCriticalSection());
        parent.onChildCreated(this);
    }

    @Override
    public ILegibleStorage getStorage() {
        return getRoot().getStorage();
    }

    @Override
    public ICriticalSection getCriticalSection() {
        return getRoot().getCriticalSection();
    }

    @Override
    public Type getType() {
        return Type.LEGIBLE;
    }

    @Override
    public void onChildCreated(IProperty child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    @Override
    public List<IProperty> getChildren() {
        return Objects.requireNonNullElseGet(children, List::of);
    }

    @Override
    public <V extends IPropertyVisitor> V accept(V visitor) {
        return IPropertyVisitor.accept(this, visitor);
    }

    @Override
    public LegibleIO getLegibleIO() {
        return legibleIo;
    }

    @Override
    public BlobIO getBlobIO() {
        return null;
    }

    public T getDefaultValue() {
        if (legibleIo.getDefaultText() == null) {
            return null;
        }
        return this.serializer.fromStr(legibleIo.getDefaultText());
    }

    /**
     * Provides value related with this property.
     *
     * @return Value of this property or throws exception.
     */
    public T getValue() {
        if (!legibleIo.hasValue()) {
            return this.getDefaultValue();
        }
        return this.serializer.fromStr(legibleIo.getTextValue());
    }

    /**
     * Allows changing property's value if it is possible.
     *
     * @param what Object which will be set as a property value.
     */
    public S setValue(T what) {
        legibleIo.setTextValue(this.serializer.toStr(what));
        return self();
    }

    /**
     * This value will be returned if value is missing.
     *
     * @param what Alternative value if there is no value stored.
     * @return Stored value or what if there is no stored value.
     */
    public S setDefault(final T what) {
        legibleIo.setDefaultText(serializer.toStr(what));
        return self();
    }

    /**
     * @return True if enums are accepted only.
     */
    @SuppressWarnings("unused")
    public boolean isEnumOnly() {
        return legibleIo.isEnumOnly();
    }

    /**
     * @param isEnumValue If true, only enums will be accepted.
     *                    If false, other values will be also tolerated.
     * @return This object.
     */
    public S setEnumOnly(final boolean isEnumValue) {
        legibleIo.setEnumOnly(isEnumValue);
        return self();
    }

    /**
     * Adds sample valid value to sample values set (enumeration).
     *
     * @param what One enum value.
     * @return This object.
     */
    public S addEnum(final T what) {
        legibleIo.addEnumText(serializer.toStr(what));
        return self();
    }

    /**
     * @return List of all added enums.
     */
    @SuppressWarnings("unused")
    public List<T> getEnums() {
        final List<T> result = new ArrayList<>(legibleIo.getEnumTexts().size());
        for (final String entry : legibleIo.getEnumTexts()) {
            result.add(this.serializer.fromStr(entry));
        }
        return result;
    }

    @Override
    public String toString() {
        return this.getPath().toString() + ":" + this.getValue();
    }
}
