package com.cloudurable.jai.model.finetune;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.util.List;

/**
 * The ListFineTuneResponseDeserializer class provides methods to deserialize a JSON string
 * into a ListFineTuneResponse object.
 */
public class ListFineTuneResponseDeserializer {
    private ListFineTuneResponseDeserializer() {
    }

    /**
     * Deserializes a JSON string into a ListFineTuneResponse object.
     *
     * @param json The JSON string to deserialize.
     * @return The deserialized ListFineTuneResponse object.
     */
    public static ListFineTuneResponse deserialize(final String json) {
        final ListFineTuneResponse.Builder builder = ListFineTuneResponse.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = jsonParser.parse(json).getObjectNode();
        builder.object(objectNode.getString("object"));
        final List<FineTuneData> data = objectNode.getArrayNode("data")
                .mapObjectNode(FineTuneDataDeserializer::getFineTuneData);
        builder.data(data);
        return builder.build();
    }
}
