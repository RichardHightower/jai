package com.cloudurable.jai.model.text.embedding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmbeddingResponseDeserializerTest {

    @Test
    void testDeserialize() {
        String jsonBody = "{\"object\": \"embedding\", \"usage\": {\"prompt_tokens\": 10, \"total_tokens\": 10}, " +
                "\"data\": [{\"index\": 0, \"embedding\": [1.0, 2.0]}, {\"index\": 1, \"embedding\": [3.0, 4.0]}]}";

        EmbeddingResponse response = EmbeddingResponseDeserializer.deserialize(jsonBody);

        assertNotNull(response);
        assertEquals("embedding", response.getObject());
        assertEquals(2, response.getData().size());
        assertEquals(10, response.getUsage().getPromptTokens());
        assertEquals(10, response.getUsage().getTotalTokens());

        Embedding embedding1 = response.getData().get(0);
        assertEquals(0, embedding1.getIndex());
        assertEquals(2, embedding1.getEmbedding().length);
        assertEquals(1.0f, embedding1.getEmbedding()[0]);
        assertEquals(2.0f, embedding1.getEmbedding()[1]);

        Embedding embedding2 = response.getData().get(1);
        assertEquals(1, embedding2.getIndex());
        assertEquals(2, embedding2.getEmbedding().length);
        assertEquals(3.0f, embedding2.getEmbedding()[0]);
        assertEquals(4.0f, embedding2.getEmbedding()[1]);
    }
}
