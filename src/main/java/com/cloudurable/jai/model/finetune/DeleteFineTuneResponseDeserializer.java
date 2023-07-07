package com.cloudurable.jai.model.finetune;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

/**
 * The DeleteFineTuneResponseDeserializer class provides a utility method for deserializing JSON into a DeleteFineTuneResponse object.
 */
public class DeleteFineTuneResponseDeserializer {
    private DeleteFineTuneResponseDeserializer() {
    }

    /**
     * Deserializes the given JSON string into a DeleteFineTuneResponse object.
     *
     * @param json the JSON string to deserialize
     * @return the deserialized DeleteFineTuneResponse static  object
     */
    public static DeleteFineTuneResponse deserialize(final String json) {
        final DeleteFineTuneResponse.Builder builder = DeleteFineTuneResponse.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode node = jsonParser.parse(json).getObjectNode();

        builder.id(node.getString("id"))
                .deleted(node.getBoolean("deleted"))
                .object(node.getString("object"));

        return builder.build();
    }
}
