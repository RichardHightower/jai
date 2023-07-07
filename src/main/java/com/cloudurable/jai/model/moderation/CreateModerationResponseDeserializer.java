package com.cloudurable.jai.model.moderation;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

public class CreateModerationResponseDeserializer {

    public static CreateModerationResponse deserialize(String jsonBody) {

        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = jsonParser.parse(jsonBody).getObjectNode();
        final CreateModerationResponse.Builder builder = CreateModerationResponse.builder();

        builder.id(objectNode.getString("id"));

        if(objectNode.containsKey("model")) {
            builder.model(objectNode.getString("model"));
        }

        if(objectNode.containsKey("results")) {
            builder.results(objectNode.getArrayNode("results"));
        }

        return builder.build();
    }
}
