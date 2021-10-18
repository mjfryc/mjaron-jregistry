package pl.mjaron.jregistry.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonTreePropertyVisitor implements IPropertyVisitor {

    private JsonObject jRoot = new JsonObject();

    @Override
    public void visit(IProperty property) {
        final JsonObject jProperty = propertyToJsonObject(property);
        final String[] pathParts = property.getPath().getPathParts();
        JsonObject iNode = jRoot;
        for (int i = 0; i < pathParts.length; ++i) {
            final String pathEntry = pathParts[i];
            if (i != pathParts.length - 1) {
                if (!iNode.has(pathEntry)) {
                    iNode = new JsonObject();
                    iNode.add(pathEntry, iNode);
                }
            }
            else {
                iNode.add(pathEntry, jProperty);
            }
        }
    }

    private JsonObject propertyToJsonObject(IProperty property) {
        JsonObject jProperty = new JsonObject();
        jProperty.addProperty("name", property.getName());
        jProperty.addProperty("path", property.getPath().toString());
        jProperty.addProperty("isNode", property.getType() == IProperty.Type.NODE);
        if (property.getLegibleIO() != null) {
            jProperty.addProperty("value", property.getLegibleIO().getTextValue());
            JsonArray enums = new JsonArray();
            for (var enumEntry : property.getLegibleIO().getEnumTexts()) {
                enums.add(enumEntry);
            }
            jProperty.add("enums", enums);
            jProperty.addProperty("enumOnly", property.getLegibleIO().isEnumOnly());
            jProperty.addProperty("default", property.getLegibleIO().getDefaultText());
            jProperty.addProperty("hasValue", property.getLegibleIO().hasValue());
        }
        return jProperty;
    }

    public String getJsonString() {
        return null;
    }
}
