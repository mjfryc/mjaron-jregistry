package pl.mjaron.jregistry.core;

public abstract class PropertyBase implements IProperty {

    private final IProperty parent;
    private final String name;
    private long mVersion = 0;
    private final IProperty root;
    private final PropertyPath path;

    public PropertyBase(final IProperty parent, final String name) {
        this(parent, name, parent.getRoot(), parent.getPath().plus(name));
    }

    public PropertyBase(final IProperty parent, final String name, final IProperty root, final PropertyPath path) {
        this.parent = parent;
        this.name = name;
        this.root = root;
        this.path = path;
    }

    public PropertyBase(final String name, final PropertyPath path) {
        this.parent = null;
        this.name = name;
        this.root = this;
        this.path = path;
    }

    @Override
    public IProperty getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getVersion() {
        return mVersion;
    }

    @Override
    public IProperty getRoot() {
        return root;
    }


    @Override
    public PropertyPath getPath() {
        return path;
    }

    @Override
    public long increaseVersion() {
        ++mVersion;
        final IProperty parent = this.getParent();
        if (parent != null) {
            parent.increaseVersion();
        }
        return mVersion;
    }

}
