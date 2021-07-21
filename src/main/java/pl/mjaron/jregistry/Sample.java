package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.IRoot;
import pl.mjaron.jregistry.core.PropertyNode;

public class Sample {

    public static class Registry1 {
        static IRoot root = new MemoryRoot();

        static class cats {
            static PropertyNode node = root.add(cats.class.getSimpleName(), new PropertyNode());
            static IntProperty count = node.add("count", new IntProperty()).setValue(85);

            static class bodyPartsStatus {
                static PropertyNode node = cats.node.add(bodyPartsStatus.class.getSimpleName(), new PropertyNode());

                static class leg {
                    static PropertyNode node = bodyPartsStatus.node.add(leg.class.getSimpleName(), new PropertyNode());

                    static class front {
                        static PropertyNode node = leg.node.add(front.class.getSimpleName(), new PropertyNode());
                        static PropertyNode left = node.add("left", new StringProperty()).setValue("OK");
                        static PropertyNode right = node.add("right", new StringProperty()).setValue("BAD");
                    }

                    static class rear {
                        static PropertyNode node = leg.node.add(front.class.getSimpleName(), new PropertyNode());
                        static PropertyNode left = node.add("left", new StringProperty()).setValue("BAD");
                        static PropertyNode right = node.add("right", new StringProperty()).setValue("BAD");
                    }
                }
            }
        }
    }

    public static class Registry2 {
        public MemoryRoot root = new MemoryRoot();
        public final Node cats = root.node("cats");
        public final IntProperty count = cats.integer("count").setValue(85);
        public final Node parts = cats.node("parts");
        public final Node legs = parts.node("legs");
        public final Node front = legs.node("front");
        public final StringProperty left = front.string("left").setValue("OK");
        public final StringProperty right = front.string("right").setValue("BAD");

        public final Node rear = legs.node("rear");
        final StringProperty REAR_LEFT = rear.string("left").setValue("BAD");
        final StringProperty REAR_RIGHT = rear.string("right").setValue("BAD");
    }

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
    }
}
