package com.cloudurable.jai.model.file;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;

/**
 * The FileDataDeserializer class provides a utility method for deserializing JSON into a FileData object.
 */
public class FileDataDeserializer {
    private FileDataDeserializer() {
    }

    /**
     * Deserializes the given JSON string into a FileData object.
     *
     * @param json the JSON string to deserialize
     * @return the deserialized FileDatapublic static  object
     */
    public static FileData deserialize(final String json) {
        final FileData.Builder builder = FileData.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode node = jsonParser.parse(json).getObjectNode();

        builder.object(node.getString("object"));
        builder.id(node.getString("id"))
                .createAt(Instant.ofEpochSecond(node.getInt("created_at")))
                .bytes(node.getInt("bytes"))
                .purpose(node.getString("purpose"));

        return builder.build();
    }
}
