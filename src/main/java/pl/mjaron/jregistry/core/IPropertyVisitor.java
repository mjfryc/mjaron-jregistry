package pl.mjaron.jregistry.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface IPropertyVisitor {

    void visit(final IProperty property);

    static <T extends IPropertyVisitor> T accept(IProperty property, T visitor) {
        visitor.visit(property);
        for (IProperty child : property.getChildren()) {
            accept(child, visitor);
        }
        return visitor;
    }

    class PrintingVisitor implements IPropertyVisitor {

        @Override
        public void visit(IProperty property) {
            System.out.println(property);
        }
    }

    class JsonVisitor implements IPropertyVisitor {

        JSONArray jArr = new JSONArray();

        @Override
        public void visit(IProperty property) {
            JSONObject jProperty = new JSONObject();
            jProperty.put("name", property.getName());
            jProperty.put("path", property.getPath().toString());
            jProperty.put("isNode", property.getType() == IProperty.Type.NODE);
            if (property.getLegibleIO() != null) {
                jProperty.put("value", property.getLegibleIO().getTextValue());
                jProperty.put("enums", property.getLegibleIO().getEnumTexts());
                jProperty.put("enumOnly", property.getLegibleIO().isEnumOnly());
                jProperty.put("default", property.getLegibleIO().getDefaultText());
                jProperty.put("hasValue", property.getLegibleIO().hasValue());
            }
            jArr.add(jProperty);
        }

        public String getJsonString() {
            return jArr.toJSONString();
        }
    }
}
