package pl.mjaron.jregistry;

/**
 * Conversion between property data and format used by registry.
 */
public interface IPropertyAdapter<T> {

    String toStr(final T what);

    T fromStr(final String whatStr);
}