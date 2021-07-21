package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.AnyProperty;
import pl.mjaron.jregistry.core.IProperty;
import pl.mjaron.jregistry.core.ISerializer;

public class IntProperty extends AnyProperty<Integer, IntProperty> {
    public IntProperty(IProperty parent, String name) {
        super(parent, name, new ISerializer<Integer>() {
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
