package com.cloudurable.jai.model.text.embedding;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmbeddingRequestSerializerTest {

    @Test
    void testSerialize() {
        String model = "gpt2";
        String input = "Hello, world!";

        EmbeddingRequest request =  EmbeddingRequest.builder().model(model).addInput(input).build();

        String expectedJson = "{\"input\":\"Hello, world!\",\"model\":\"gpt2\"}";
        String actualJson = EmbeddingRequestSerializer.serialize(request);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    void testSerialize2() {
        String model = "gpt2";
        String input = "Hello, world!";

        EmbeddingRequest request =  EmbeddingRequest.builder().model(model).addInput(input).addInput("Mom").build();

        String expectedJson = "{\"input\":[\"Hello, world!\",\"Mom\"],\"model\":\"gpt2\"}";
        String actualJson = EmbeddingRequestSerializer.serialize(request);

        assertEquals(expectedJson, actualJson);
    }
}
