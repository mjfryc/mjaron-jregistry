package pl.mjaron.jregistry.core;

import java.util.List;

/**
 * Represents any property (which has value) or node (without value).
 */
public interface IProperty {

    public enum Type {
        NODE,
        LEGIBLE,
        BLOB
    }

    /**
     * Properties are stored in tree hierarchy. Each property is a node.
     * This method provides this property node name.
     *
     * @return Name of this property without full path.
     */
    String getName();

    /**
     * @return Full path of given property.
     */
    PropertyPath getPath();

    /**
     * Properties are stored in tree hierarchy.
     * Each property is a node.
     * Only root property doesn't has parent.
     *
     * @return Node which contains this property. It is a child of parent property.
     */
    IProperty getParent();

    /**
     * @return Root node.
     */
    IProperty getRoot();

    /**
     * @return Storage which is responsible for saving / loading all data.
     */
    ILegibleStorage getStorage();

    /**
     * @return Implementation of lock used while saving / loading all data.
     */
    ICriticalSection getCriticalSection();

    /**
     * Determines whether this node is leaf or node.
     *
     * @return True if it is a node. False if it is a leaf.
     */
    Type getType();

    /**
     * Should be called automatically by property / node constructor.
     *
     * @param child Instance of child.
     */
    void onChildCreated(IProperty child);

    /**
     * @return All children.
     */
    List<IProperty> getChildren();

    /**
     * Iterates over all children recursively.
     *
     * @param visitor IPropertyVisitor::visit() will be called for each child recursively.
     */
    <T extends IPropertyVisitor> T accept(T visitor);

    /**
     * Runtime version identifier. Changed identifier means changed value.
     *
     * @return Version identifier unique for single runtime.
     */
    long getVersion();

    /**
     * Increases unique version of value and its parent version.
     *
     * @return New increased version of value.
     */
    long increaseVersion();

    /**
     * Used only for serialization purposes.
     *
     * @return IO instance.
     */
    LegibleIO getLegibleIO();

    BlobIO getBlobIO();

    static IProperty findChild(final IProperty p, final String name) {
        for (final IProperty childEntry : p.getChildren()) {
            if (childEntry.getName().equals(name)) {
                return childEntry;
            }
        }
        return null;
    }

    static IProperty getChildByPath(final IProperty p, final PropertyPath path) {
        IProperty pNode = p;
        for (final String node : path.getPathParts()) {
            pNode = findChild(pNode, node);
        }
        return pNode;
    }
}
