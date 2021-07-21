package pl.mjaron.jregistry.core;

/**
 * Conventional interface of root node which also stores child node values.
 */
public interface IRoot extends IPropertyNode, IStorage {

    /**
     * Allows to visit all children recursively.
     * @param visitor Visitor object.
     */
    void accept(final IRegistryVisitor visitor);

}
