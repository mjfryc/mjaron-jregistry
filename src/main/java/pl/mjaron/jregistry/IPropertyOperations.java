package pl.mjaron.jregistry;

public interface IPropertyOperations {

    <T> T add(T property, String path);

    <T> T add(T property, PropertyPath path);
}
