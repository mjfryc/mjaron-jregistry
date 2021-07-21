package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.IPropertyNode;
import pl.mjaron.jregistry.core.IRoot;
import pl.mjaron.jregistry.core.MemoryStorage;
import pl.mjaron.jregistry.core.PropertyNode;

public class Sample {

    static Registry2 Registry2 = new Registry2();

    public static void main(String[] args) {
        System.out.println("Registry1");
        System.out.println(Registry1.cats.count);
        System.out.println(Registry1.cats.bodyPartsStatus.leg.front.right);
        System.out.println("Only referenced static values are stored.");
        Registry1.root.accept((key, value) -> System.out.println(key + ":" + value));

        System.out.println("");
        System.out.println("Registry2");
        Registry2.root.accept((key, value) -> System.out.println(key + ":" + value));

        System.out.println();
        System.out.println("Printing default value.");
        System.out.println(Registry2.DEFAULT_COUNT);

        System.out.println();
        Registry2.DEFAULT_COUNT.setValue(5);
        System.out.println("Now default value not used because stored value exists.");
        System.out.println(Registry2.DEFAULT_COUNT);
    }

    public static class Registry1 {
        static IRoot root = new Root(new MemoryStorage());

        static class cats {
            static Node node = root.add(cats.class.getSimpleName(), new Node());
            static IntProperty count = node.add("count", new IntProperty()).setValue(85);

            static class bodyPartsStatus {
                static IPropertyNode node = cats.node.add(bodyPartsStatus.class.getSimpleName(), new Node());

                static class leg {
                    static IPropertyNode node = bodyPartsStatus.node.add(leg.class.getSimpleName(), new Node());

                    static class front {
                        static IPropertyNode node = leg.node.add(front.class.getSimpleName(), new Node());
                        static IPropertyNode left = node.add("left", new StrProperty()).setValue("OK");
                        static IPropertyNode right = node.add("right", new StrProperty()).setValue("BAD");
                    }

                    static class rear {
                        static IPropertyNode node = leg.node.add(front.class.getSimpleName(), new Node());
                        static IPropertyNode left = node.add("left", new StrProperty()).setValue("BAD");
                        static IPropertyNode right = node.add("right", new StrProperty()).setValue("BAD");
                    }
                }
            }
        }
    }

    public static class Registry2 {
        public Root root = new Root(new MemoryStorage());
        public final Node cats = root.node("cats");
        public final IntProperty count = cats.integer("count").setValue(85);
        public final Node parts = cats.node("parts");
        public final Node legs = parts.node("legs");
        public final Node front = legs.node("front");
        public final StrProperty left = front.string("left").setValue("OK");
        public final StrProperty right = front.string("right").setValue("BAD");
        public final Node rear = legs.node("rear");
        public final StrProperty REAR_LEFT = rear.string("left").setValue("BAD");
        public final StrProperty REAR_RIGHT = rear.string("right").setValue("BAD");
        public final IntProperty DEFAULT_COUNT = legs.integer("default-count").setDefault(4);
    }
}
