package pl.mjaron.jregistry;

/**
 * Definition of essential registry operations.
 * Single project should use single registry instance.
 */
public interface IRegistryOperations extends IPropertyOperations {

    String get(final PropertyPath path);

    String set(final PropertyPath path, final String what);

    boolean has(final PropertyPath path);

    boolean remove(final PropertyPath path);

}