package com.cloudurable.jai.model.model;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;


public class ModelDataDeserializer {
    private ModelDataDeserializer() {}

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
