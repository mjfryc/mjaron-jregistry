package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.IPropertyVisitor;
import pl.mjaron.jregistry.core.MemoryStorage;

import java.io.PrintStream;

class Registry {
    Node root = new Node(new MemoryStorage());
    public Node fish = new Node(root, "fish");
    public IntProperty FISH_AGE = new IntProperty(fish, "age").setValue(28);
    public StrProperty name = new StrProperty(fish, "what").setDefault("Fish");
    public StrProperty surname = new StrProperty(fish, "name").setValue("Jessica");
    public StrProperty myEnum = new StrProperty(fish, "enum").addEnum("a").addEnum("b").addEnum("c").setEnumOnly(true).setValue("b");
    public IntProperty myDigitEnum = new IntProperty(fish, "enum-d").setEnumOnly(true).addEnum(4).addEnum(6).addEnum(8);
}



public class Sample {

    static Registry R = new Registry();

    public static void main(String[] args) {
        final PrintStream o = System.out;
        R.myDigitEnum.setValue(4);
        o.println("All properties:");
        R.root.accept(new IPropertyVisitor.PrintingVisitor());

        o.println();
        o.println("Fish age is: " + R.FISH_AGE.getValue());
    }

}
