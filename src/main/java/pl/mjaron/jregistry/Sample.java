package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.IPropertyVisitor;
import pl.mjaron.jregistry.core.MemoryStorage;
import pl.mjaron.jregistry.core.ReentrantSection;

import java.io.PrintStream;

/**
 * This is sample registry implementation.
 */
class Registry {
    Node root = new Node(new MemoryStorage(), new ReentrantSection());
    public Node fish = new Node(root, "fish");
    public IntProperty FISH_AGE = new IntProperty(fish, "age").setValue(28);
    public StrProperty name = new StrProperty(fish, "what").setDefault("Fish");
    public StrProperty surname = new StrProperty(fish, "name").setValue("Jessica");
    public StrProperty myEnum = new StrProperty(fish, "enum")
            .addEnum("a")
            .addEnum("b")
            .addEnum("c")
            .setEnumOnly(true) // If setting value other than added enum - a RuntimeException will be thrown.
            .setValue("b");
    public IntProperty myDigitEnum = new IntProperty(fish, "enum-d").setEnumOnly(true).addEnum(4).addEnum(6).addEnum(8);

    public BoolProperty myBool = new BoolProperty(fish, "should-i-swim?").setDefault(true);
}

public class Sample {

    static Registry R = new Registry();

    public static void main(String[] args) throws InterruptedException {
        PrintStream o = System.out;
        R.myDigitEnum.setValue(4);
        o.println("All properties:");
        R.root.accept(new IPropertyVisitor.PrintingVisitor());

        R.root.getCriticalSection().withLock(() -> {
            System.out.println("I am secure here!");
            return null;
        });

        Thread thr = new Thread(() -> {
            String someOperationResult = R.FISH_AGE.getCriticalSection().withLock(() -> {
                // All operations inside lock will be called atomically.
                Thread.sleep(10000);
                return R.FISH_AGE.getName();
            });
            System.out.println("someOperationResult: " + someOperationResult);
        });
        thr.start();;

        Thread.sleep(100);
        R.FISH_AGE.getValue();

        o.println();
        o.println("Fish age is: " + R.FISH_AGE.getValue());

        o.println();
        o.println("Should I swim? " + R.myBool.getValue());
    }

}
