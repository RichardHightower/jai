package com.cloudurable.jai.model.file;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

/**
 * The FileDeleteResponseDeserializer class provides a utility method for deserializing JSON into a FileDeleteResponse object.
 */
public class FileDeleteResponseDeserializer {
    private FileDeleteResponseDeserializer() {
    }

    /**
     * Deserializes the given JSON string into a FileDeleteResponse object.
     *
     * @param json the JSON string to deserialize
     * @return the deserialized FileDeleteResponse static  object
     */
    public static FileDeleteResponse deserialize(final String json) {
        final FileDeleteResponse.Builder builder = FileDeleteResponse.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode node = jsonParser.parse(json).getObjectNode();

        builder.id(node.getString("id"))
                .deleted(node.getBoolean("deleted"))
                .object(node.getString("object"));

        return builder.build();
    }
}
