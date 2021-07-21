package pl.mjaron.jregistry2.core;

import java.util.Map;

public interface IPropertyNode {
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
    <ChildT extends IPropertyNode> ChildT add(String name, ChildT child);

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
     * @return Reference tp root node.
     */
    IPropertyChildValue getRoot();

    /**
     * Information about nesting level of this property.
     *
     * @return Size of nesting. Root property level is 0, root's direct child is 1 and so on.
     */
    int getLevel();

    /**
     * Must be called by parent property when adding child.
     * Should set parent reference.
     *
     * @param name   Node name of this property.
     * @param parent This property owner reference.
     */
    void onCreate(String name, IPropertyNode parent);
}
