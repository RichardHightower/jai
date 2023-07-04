package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.DeserializerUtils;
import com.cloudurable.jai.model.text.completion.CompletionChoice;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * This class provides deserialization functionality for EditResponse objects.
 * The EditResponseDeserializer class is responsible for deserializing JSON string representations of
 * EditResponse objects into Java objects.
 * It takes a JSON body as input and parses it using the JsonParser from the jparse library.
 * The deserialization process involves extracting the values from the JSON object and constructing a EditResponse object
 * with the deserialized values.
 */
public class EditResponseDeserializer {

    /**
     * Deserializes a JSON string representation into a EditResponse object.
     *
     * @param jsonBody The JSON string representation of the EditResponse.
     * @return The deserialized EditResponse object.
     */
    public static EditResponse deserialize(final String jsonBody) {
        final JsonParser parser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = parser.parse(jsonBody).asObject();
        final String object = objectNode.getString("object");
        final int createdTime = objectNode.getInt("created");
        final Usage usage = DeserializerUtils.deserializeUsage(objectNode.getObjectNode("usage"));

        final ArrayNode choicesArray = objectNode.getArrayNode("choices");

        final List<EditChoice> choices = choicesArray.mapObjectNode(choiceNode -> EditChoice.builder()
                .index(choiceNode.getInt("index"))
                .text(choiceNode.getString("text"))
                .build()
        );

        return EditResponse.builder().object(object)
                .created(Instant.ofEpochSecond(createdTime))
                .usage(usage)
                .choices(choices)
                .build();
    }

}
