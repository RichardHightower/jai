package com.cloudurable.jai.model.finetune;

import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;
import java.util.List;

/**
 * The ListFineTuneEventResponseDeserializer class provides methods to deserialize a JSON string
 * into a ListFineTuneEvent object.
 */
public class ListFineTuneEventResponseDeserializer {
    private ListFineTuneEventResponseDeserializer() {
    }

    /**
     * Deserializes a JSON string into a ListFineTuneEventResponse object.
     *
     * @param json The JSON string to deserialize.
     * @return The deserialized ListFineTuneEventResponse object.
     */
    public static ListFineTuneEventResponse deserialize(final String json) {
        final ListFineTuneEventResponse.Builder builder = ListFineTuneEventResponse.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = jsonParser.parse(json).getObjectNode();

        builder.object(objectNode.getString("object"));

        builder.data(getEventData(objectNode.getArrayNode("data")));

        return builder.build();
    }

    public static List<FineTuneEvent> getEventData(ArrayNode arrayNode) {
        return arrayNode.mapObjectNode(on -> FineTuneEvent.builder()
                .object(on.getString("object"))
                .level(on.getString("level"))
                .message(on.getString("message"))
                .createdAt(Instant.ofEpochSecond(on.getInt("created_at")))
                .build());
    }
}
