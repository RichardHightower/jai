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
     * @return the deserialized FileData public static  object
     */
    public static FileData deserialize(final String json) {

        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode node = jsonParser.parse(json).getObjectNode();

        return getFileData(node);
    }

    /**
     * Create file data from objectNode
     * @param objectNode objectNode
     * @return FileData
     */
    public static FileData getFileData(ObjectNode objectNode) {
        final FileData.Builder builder = FileData.builder();
        builder.object(objectNode.getString("object"));
        builder.id(objectNode.getString("id"))
                .createdAt(Instant.ofEpochSecond(objectNode.getInt("created_at")))
                .bytes(objectNode.getInt("bytes"))
                .purpose(objectNode.getString("purpose"))
                .fileName(objectNode.getString("filename"));
        return builder.build();
    }
}
