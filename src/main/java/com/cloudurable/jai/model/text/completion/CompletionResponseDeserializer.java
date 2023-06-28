package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.DeserializerUtils;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;
import java.util.List;

public class CompletionResponseDeserializer {

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
                //TODO .logprobs(choiceNode.getArrayNode("logprobs").map(node -> node.asScalar().intValue()))
                .build()
        );

        return  CompletionResponse.builder().id(id).object(object)
                .created(Instant.ofEpochSecond(createdTime))
                .usage(usage)
                .choices(choices)
                .build();
    }
}
