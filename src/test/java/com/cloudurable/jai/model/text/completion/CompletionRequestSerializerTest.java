package com.cloudurable.jai.model.text.completion;

import io.nats.jparse.Json;
import io.nats.jparse.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CompletionRequestSerializerTest {

    @Test
    void testSerialize() {
        CompletionRequest request = CompletionRequest.builder()
                .model("model")
                .prompt("Hello")
                .suffix("World")
                .bestOf(3)
                .temperature(0.8f)
                .topP(0.9f)
                .completionCount(1)
                .stream(false)
                .stop(Collections.singletonList("||STOP"))
                .maxTokens(100)
                .presencePenalty(0.33f)
                .frequencyPenalty(0.66f)
                .logitBias(Collections.emptyMap())
                .user("john_doe")
                .logprobs(1)
                .echo(true)
                .build();


        String serializedJson = CompletionRequestSerializer.serialize(request);

        ObjectNode objectNode = Json.toObjectNode(serializedJson);

        assertEquals("model", objectNode.getString("model"));
        assertEquals("Hello", objectNode.getString("prompt"));
        assertEquals("World", objectNode.getString("suffix"));
        assertEquals(0.8, objectNode.getDouble("temperature"), 0.01);
        assertEquals(1, objectNode.getInt("n"));
        assertEquals(0.9, objectNode.getDouble("top_p"), 0.01);
        assertEquals(100, objectNode.getInt("max_tokens"));
        assertEquals(0.33, objectNode.getDouble("presence_penalty"), 0.01);
        assertEquals(0.66, objectNode.getDouble("frequency_penalty"), 0.01);
        assertEquals("john_doe", objectNode.getString("user"));
        assertEquals(1, objectNode.getInt("logprobs"));
        assertTrue(objectNode.getBoolean("echo"));
        assertFalse(objectNode.getBoolean("stream"));
        assertEquals(3, objectNode.getInt("best_of"));

    }
}
