package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.TypedLegibleProperty;
import pl.mjaron.jregistry.core.IProperty;
import pl.mjaron.jregistry.core.ISerializer;

public class StrProperty extends TypedLegibleProperty<String, StrProperty> {
    public StrProperty(IProperty parent, String name) {
        super(parent, name, new ISerializer<String>() {
            @Override
            public String toStr(String what) {
                return what;
            }

            @Override
            public String fromStr(String whatStr) {
                return whatStr;
            }
        });
    }
}
