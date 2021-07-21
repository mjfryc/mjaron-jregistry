package pl.mjaron.jregistry.core;

import java.util.Map;

/**
 * Basic node which may return child nodes if property is a node or give text value is property is a leaf.
 */
public interface IPropertyNode extends IPropertyTextValue {
    /**
     * Properties are stored in tree hierarchy.
     * Each property is a node.
     * Only root property doesn't has parent.
     *
     * @return Node which contains this property. It is a child of parent property.
     */
    IPropertyNode getParent();

    /**
     * Properties are stored in tree hierarchy. Each property is a node.
     * This method provides mapping between child name and child object.
     *
     * @return All direct children of this property.
     */
    Map<String, IPropertyNode> getChildren();

    /**
     * Registers existence of child property, so child can be found with getChildren()["child_name"] call.
     *
     * @param name     Name of the child property.
     * @param child    Child object.
     * @param <ChildT> Type of child property.
     * @return Child which has been added.
     */
    <ChildT extends IPropertyNode> ChildT add(final String name, final ChildT child);

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
     * @return Reference to root node.
     */
    IStorage getRoot();

    /**
     * Information about nesting level of this property.
     *
     * @return Size of nesting. Root property level is 0, root's direct child is 1 and so on.
     */
    int getLevel();

    /**
     * Determines whether this node is leaf or node.
     * @return True if it is a node. False if it is a leaf.
     */
    boolean isNode();

    /**
     * Must be called by parent property when adding child.
     * Should set parent reference.
     *
     * @param name   Node name of this property.
     * @param parent This property owner reference.
     */
    void onCreate(final String name, final IPropertyNode parent);
}
