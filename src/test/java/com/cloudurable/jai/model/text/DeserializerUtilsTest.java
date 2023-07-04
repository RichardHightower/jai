package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.Usage;
import io.nats.jparse.Json;
import io.nats.jparse.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeserializerUtilsTest {

    @Test
    void testDeserializeUsage() {

        ObjectNode usageNode = Json.toObjectNode("{ " +
                "\"completion_tokens\" :10," +
                "\"prompt_tokens\" : 5," +
                "\"total_tokens\": 15 }");

        Usage usage = DeserializerUtils.deserializeUsage(usageNode);

        assertEquals(10, usage.getCompletionTokens());
        assertEquals(5, usage.getPromptTokens());
        assertEquals(15, usage.getTotalTokens());
    }

    @Test
    void testDeserializeFinishReason() {
        FinishReason stopReason = DeserializerUtils.deserializeFinishReason("stop");
        assertEquals(FinishReason.STOP, stopReason);

        FinishReason contentFilterReason = DeserializerUtils.deserializeFinishReason("content_filter");
        assertEquals(FinishReason.CONTENT_FILTER, contentFilterReason);

        FinishReason lengthReason = DeserializerUtils.deserializeFinishReason("length");
        assertEquals(FinishReason.LENGTH, lengthReason);

        FinishReason nullReason = DeserializerUtils.deserializeFinishReason("null");
        assertEquals(FinishReason.NULL, nullReason);

        FinishReason otherReason = DeserializerUtils.deserializeFinishReason("other");
        assertEquals(FinishReason.OTHER, otherReason);
    }
}
