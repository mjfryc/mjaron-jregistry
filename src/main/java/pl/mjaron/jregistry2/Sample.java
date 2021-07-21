package pl.mjaron.jregistry2;

import pl.mjaron.jregistry2.core.IRoot;
import pl.mjaron.jregistry2.core.PropertyNode;

public class Sample {

    private static class Registry {
        static IRoot root = new MemoryRoot();

        static class cats {
            static PropertyNode cats = root.add("cats", new PropertyNode());
            static IntProperty count = cats.add("count", new IntProperty()).setValue(85);
            static class byName {
                static PropertyNode node = cats.add(byName.class.getName(), new PropertyNode());
                static StringProperty tom = node.add("tom", new StringProperty());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(Registry.cats.count);
        System.out.println(Registry.cats.byName.tom);
    }
}
