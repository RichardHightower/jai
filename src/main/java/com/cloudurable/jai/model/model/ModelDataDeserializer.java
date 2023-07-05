package com.cloudurable.jai.model.model;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

/**
 * The ModelDataDeserializer class provides a utility method for deserializing JSON into a ModelData object.
 */
public class ModelDataDeserializer {
    private ModelDataDeserializer() {
    }

    /**
     * Deserializes the given JSON string into a ModelData object.
     *
     * @param json the JSON string to deserialize
     * @return the deserialized ModelData object
     */
    public static ModelData deserialize(final String json) {
        final ModelData.Builder builder = ModelData.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode node = jsonParser.parse(json).getObjectNode();

        builder.object(node.getString("object"));
        builder.id(node.getString("id"))
                .ownedBy(node.getString("owned_by"))
                .object(node.getString("object"))
                .permission(node.getArrayNode("permission"));

        return builder.build();
    }
}
