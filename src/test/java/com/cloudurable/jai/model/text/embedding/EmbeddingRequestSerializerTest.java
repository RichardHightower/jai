package com.cloudurable.jai.model.text.embedding;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmbeddingRequestSerializerTest {

    @Test
    void testSerialize() {
        String model = "gpt2";
        String input = "Hello, world!";

        EmbeddingRequest request = new EmbeddingRequest(model, input);

        String expectedJson = "{\"input\":\"Hello, world!\",\"model\":\"gpt2\"}";
        String actualJson = EmbeddingRequestSerializer.serialize(request);

        assertEquals(expectedJson, actualJson);
    }
}
