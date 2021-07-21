package pl.mjaron.jregistry;

class IntProperty extends Property<Integer> {

    public IntProperty() {
        super(new ISerializer<Integer>() {

            @Override
            public String toStr(Integer what) {
                return what.toString();
            }

            @Override
            public Integer fromStr(String whatStr) {
                return Integer.valueOf(whatStr);
            }
        });
    }
}

public class Registry {

    private static IRegistryOperations r = null;

    static IntProperty CATS_AGE = r.add(new IntProperty(), "cats.age");
    static IntProperty CATS_COUNT = CATS_AGE.add(new IntProperty(), "average");

    static class Komar<T> {

        //static Class<T> tClass;
        static int getValue() {

            //System.out.println(T::getClass().getEnclosingClass());
            return 4;
        }
    }
    static Root root = new Root() {
        int ala = 4;


    };
//    public Prop seconds = root.leaf<Integer>("seconds_count");
//    public Node foo = root.node("foo");
//    public Prop bar = foo.leaf<Integer>("bar");

    static class Root {

        static class Foo {
            static class Bar {
                static class Seconds extends Komar<Seconds> {
                    static int getValue() {
                        return 5;
                    }
                };
            }

        }

    }

    void ryba() {
        Root.Foo.Bar.Seconds.getValue();
    }


//    static IRegistryOperations operations = null;
//    public static Property<Integer> myp = new Property<Integer>(operations, new PropertyPath("")) {
//        @Override
//        public String toStr(Integer what) {
//            return null;
//        }
//
//        @Override
//        public Integer fromStr(String whatStr) {
//            return null;
//        }
//    };

    public static void main(String[] args) {
        Root.Foo.Bar.Seconds.getValue();
    }

}
