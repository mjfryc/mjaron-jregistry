package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.TypedProperty;
import pl.mjaron.jregistry.core.ISerializer;

public class IntProperty extends TypedProperty<Integer, IntProperty> {
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
