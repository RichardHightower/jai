package com.cloudurable.jai.model.text.embedding;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
        assertEquals(List.of(input), result.getInput());
    }

    @Test
    void testEqualsAndHashCode() {
        String model = "gpt2";
        String input = "Hello, world!";

        EmbeddingRequest request1 =  EmbeddingRequest.builder().model(model).addInput(input).build();
        EmbeddingRequest request2 =  EmbeddingRequest.builder().model(model).addInput(input).build();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertEquals(request1.toString(), request2.toString());
    }

}
