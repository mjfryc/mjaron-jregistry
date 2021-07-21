package pl.mjaron.jregistry.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnyProperty<T, S extends AnyProperty<T, S>> implements IProperty {

    private IProperty parent;
    private String name;
    private ISerializer<T> serializer;
    private IProperty root;
    private PropertyPath path;
    private IO io;
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
    public AnyProperty(IProperty parent, String name, ISerializer<T> serializer) {
        this.parent = parent;
        this.name = name;
        this.serializer = serializer;
        this.root = parent.getRoot();
        this.path = parent.getPath().plus(name);
        this.io = new IO(this.getStorage(), this, this.getCriticalSection());
        parent.onChildCreated(this);
    }

    @Override
    public IProperty getParent() {
        return parent;
    }

    @Override
    public IProperty getRoot() {
        return root;
    }

    @Override
    public IStorage getStorage() {
        return root.getStorage();
    }

    @Override
    public ICriticalSection getCriticalSection() {
        return root.getCriticalSection();
    }

    @Override
    public boolean isNode() {
        return false;
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
    public <T extends IPropertyVisitor> T accept(T visitor) {
        return IPropertyVisitor.accept(this, visitor);
    }

    @Override
    public IO getIO() {
        return io;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyPath getPath() {
        return path;
    }

    public T getDefaultValue() {
        if (io.getDefaultText() == null) {
            return null;
        }
        return this.serializer.fromStr(io.getDefaultText());
    }

    /**
     * Provides value related with this property.
     *
     * @return Value of this property or throws exception.
     */
    public T getValue() {
        if (!io.hasValue()) {
            return this.getDefaultValue();
        }
        return this.serializer.fromStr(io.getTextValue());
    }

    /**
     * Allows to change property's value if it is possible.
     *
     * @param what Object which will be set as a property value.
     */
    public S setValue(T what) {
        io.setTextValue(this.serializer.toStr(what));
        return self();
    }

    /**
     * This value will be returned if value is missing.
     *
     * @param what Alternative value if there is no value stored.
     * @return Stored value or what if there is no stored value.
     */
    public S setDefault(final T what) {
        io.setDefaultText(serializer.toStr(what));
        return self();
    }

    /**
     * @return True if enums are accepted only.
     */
    public boolean isEnumOnly() {
        return io.isEnumOnly();
    }

    /**
     * @param isEnumValue If true, only enums will be accepted.
     *                    If false, other values will be also tolerated.
     * @return This object.
     */
    public S setEnumOnly(final boolean isEnumValue) {
        io.setEnumOnly(isEnumValue);
        return self();
    }

    /**
     * Adds sample valid value to sample values set (enumeration).
     *
     * @param what One enum value.
     * @return This object.
     */
    public S addEnum(final T what) {
        io.addEnumText(serializer.toStr(what));
        return self();
    }

    /**
     * @return List of all added enums.
     */
    public List<T> getEnums() {
        final List<T> result = new ArrayList<>(io.getEnumTexts().size());
        for (final String entry : io.getEnumTexts()) {
            result.add(this.serializer.fromStr(entry));
        }
        return result;
    }

    @Override
    public String toString() {
        return this.getPath().toString() + ":" + this.getValue();
    }
}
