package pl.mjaron.jregistry2;

import pl.mjaron.jregistry2.core.GenericProperty;
import pl.mjaron.jregistry2.core.ISerializer;

public class StringProperty extends GenericProperty<String> {
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
