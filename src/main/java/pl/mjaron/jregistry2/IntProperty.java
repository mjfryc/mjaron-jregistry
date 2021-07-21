package pl.mjaron.jregistry2;

import pl.mjaron.jregistry2.core.GenericProperty;
import pl.mjaron.jregistry2.core.ISerializer;

public class IntProperty extends GenericProperty<Integer> {
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
