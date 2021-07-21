package pl.mjaron.jregistry.core;

public interface IPropertyVisitor {

    void visit(final IProperty property);

    static void accept(IProperty property, IPropertyVisitor visitor) {
        visitor.visit(property);
        for (IProperty child : property.getChildren()) {
            accept(child, visitor);
        }
    }

    class PrintingVisitor implements IPropertyVisitor {

        @Override
        public void visit(IProperty property) {
            System.out.println(property);
        }
    }
}
