package com.cloudurable.jai.model.file;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;
import java.util.List;

/**
 * The FileListResponseDeserializer class provides methods to deserialize a JSON string
 * into a FileListResponse object.
 */
public class FileListResponseDeserializer {
    private FileListResponseDeserializer() {
    }

    /**
     * Deserializes a JSON string into a FileListResponse object.
     *
     * @param json The JSON string to deserialize.
     * @return The deserialized FileListResponse object.
     */
    public static FileListResponse deserialize(final String json) {
        final FileListResponse.Builder builder = FileListResponse.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = jsonParser.parse(json).getObjectNode();

        builder.object(objectNode.getString("object"));

        final List<FileData> data = objectNode.getArrayNode("data")
                .mapObjectNode(node -> FileData.builder().id(node.getString("id"))
                        .createAt(Instant.ofEpochSecond(node.getInt("created_at")))
                        .bytes(node.getInt("bytes"))
                        .object(node.getString("object"))
                        .purpose(node.getString("purpose")).build());

        builder.data(data);

        return builder.build();
    }
}
