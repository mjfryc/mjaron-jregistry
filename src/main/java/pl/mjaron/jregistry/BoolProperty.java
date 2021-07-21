package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.AnyProperty;
import pl.mjaron.jregistry.core.IProperty;
import pl.mjaron.jregistry.core.ISerializer;

public class BoolProperty extends AnyProperty<Boolean, BoolProperty> {
    public BoolProperty(IProperty parent, String name) {
        super(parent, name, new ISerializer<Boolean>() {
            @Override
            public String toStr(Boolean what) {
                return what.toString();
            }

            @Override
            public Boolean fromStr(String whatStr) {
                return Boolean.valueOf(whatStr);
            }
        });
    }
}

