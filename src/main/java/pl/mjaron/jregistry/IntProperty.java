package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.GenericProperty;
import pl.mjaron.jregistry.core.ISerializer;

public class IntProperty extends GenericProperty<Integer, IntProperty> {
    public IntProperty() {
        super(new ISerializer<>() {
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
