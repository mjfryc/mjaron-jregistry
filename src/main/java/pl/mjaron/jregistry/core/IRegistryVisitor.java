package pl.mjaron.jregistry.core;

public interface IRegistryVisitor {

    void visit(final String key, final String value);

}
