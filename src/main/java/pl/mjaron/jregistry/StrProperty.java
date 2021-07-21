package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.TypedProperty;
import pl.mjaron.jregistry.core.ISerializer;

public class StrProperty extends TypedProperty<String, StrProperty> {
    public StrProperty() {
        super(new ISerializer<String>() {
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
