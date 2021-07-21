package pl.mjaron.jregistry;

/**
 * Converts properties from / to string.
 */
public interface ISerializer<T> {

    String toStr(final T what);

    T fromStr(final String whatStr);

}
