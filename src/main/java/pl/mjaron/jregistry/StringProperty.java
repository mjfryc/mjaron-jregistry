package pl.mjaron.jregistry;

import pl.mjaron.jregistry.core.GenericProperty;
import pl.mjaron.jregistry.core.ISerializer;

public class StringProperty extends GenericProperty<String, StringProperty> {
    public StringProperty() {
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
