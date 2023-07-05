package com.cloudurable.jai.model.text.embedding;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmbeddingRequestTest {

    @Test
    void testBuilder() {
        String model = "gpt2";
        String input = "Hello, world!";

        EmbeddingRequest.Builder builder = EmbeddingRequest.builder();
        builder.model(model);
        builder.input(input);

        EmbeddingRequest result = builder.build();

        assertEquals(model, result.getModel());
        assertEquals(input, result.getInput());
    }

    @Test
    void testEqualsAndHashCode() {
        String model = "gpt2";
        String input = "Hello, world!";

        EmbeddingRequest request1 = new EmbeddingRequest(model, input);
        EmbeddingRequest request2 = new EmbeddingRequest(model, input);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testToString() {
        String model = "gpt2";
        String input = "Hello, world!";

        EmbeddingRequest request = new EmbeddingRequest(model, input);
        String expectedString = "EmbeddingRequest{model='gpt2', input='Hello, world!'}";

        assertEquals(expectedString, request.toString());
    }
}
