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
            jProperty.put("isNode", property.isNode());
            if (property.getIO() != null) {
                jProperty.put("value", property.getIO().getTextValue());
                jProperty.put("enums", property.getIO().getEnumTexts());
                jProperty.put("enumOnly", property.getIO().isEnumOnly());
                jProperty.put("default", property.getIO().getDefaultText());
                jProperty.put("hasValue", property.getIO().hasValue());
            }
            jArr.add(jProperty);
        }

        public String getJsonString() {
            return jArr.toJSONString();
        }
    }
}
