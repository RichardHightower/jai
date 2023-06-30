package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.DeserializerUtils;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * This class provides deserialization functionality for CompletionResponse objects.
 * The CompletionResponseDeserializer class is responsible for deserializing JSON string representations of CompletionResponse objects into Java objects.
 * It takes a JSON body as input and parses it using the JsonParser from the jparse library.
 * The deserialization process involves extracting the values from the JSON object and constructing a CompletionResponse object with the deserialized values.
 */
public class CompletionResponseDeserializer {

    /**
     * Deserializes a JSON string representation into a CompletionResponse object.
     *
     * @param jsonBody The JSON string representation of the CompletionResponse.
     * @return The deserialized CompletionResponse object.
     */
    public static CompletionResponse deserialize(final String jsonBody) {
        final JsonParser parser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = parser.parse(jsonBody).asObject();
        final String id = objectNode.getString("id");
        final String object = objectNode.getString("object");
        final int createdTime = objectNode.getInt("created");
        final Usage usage = DeserializerUtils.deserializeUsage(objectNode.getObjectNode("usage"));

        final ArrayNode choicesArray = objectNode.getArrayNode("choices");

        final List<CompletionChoice> choices = choicesArray.mapObjectNode(choiceNode -> CompletionChoice.builder()
                .finishReason(DeserializerUtils.deserializeFinishReason(choiceNode.getString("finish_reason")))
                .index(choiceNode.getInt("index"))
                .text(choiceNode.getString("text"))
                .logprobs(getLogprobs(choiceNode))
                .build()
        );

        return CompletionResponse.builder().id(id).object(object)
                .created(Instant.ofEpochSecond(createdTime))
                .usage(usage)
                .choices(choices)
                .build();
    }

    /**
     * Extracts the logprobs values from the choiceNode.
     * If logprobs are present, returns a list of integers parsed from the JSON array.
     * If logprobs are not present, returns an empty list.
     *
     * @param choiceNode The ObjectNode representing a choice in the JSON.
     * @return The list of logprobs values or an empty list.
     */
    private static List<Integer> getLogprobs(ObjectNode choiceNode) {
        if (choiceNode.getNode("logprobs") != null) {
            return choiceNode.getArrayNode("logprobs").map(node -> node.asScalar().intValue());
        } else {
            return Collections.emptyList();
        }
    }
}
