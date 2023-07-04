package com.cloudurable.jai.model.text.embedding;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.DeserializerUtils;

import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;
import java.util.List;

public class EmbeddingResponseDeserializer {

    /**
     * Deserializes a JSON string representation into a EditResponse object.
     *
     * @param jsonBody The JSON string representation of the EditResponse.
     * @return The deserialized EditResponse object.
     */
    public static EmbeddingResponse deserialize(final String jsonBody) {
        final JsonParser parser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = parser.parse(jsonBody).asObject();
        final String object = objectNode.getString("object");
        final Usage usage = DeserializerUtils.deserializeUsage(objectNode.getObjectNode("usage"));

        final ArrayNode dataArray = objectNode.getArrayNode("data");

        final List<Embedding> data = dataArray.mapObjectNode(choiceNode -> Embedding.builder()
                .index(choiceNode.getInt("index"))
                .embedding(choiceNode.asObject().getArrayNode("embedding").getFloatArray())
                .build()
        );

        return EmbeddingResponse.builder().object(object).data(data)
                .usage(usage)
                .build();
    }

}
