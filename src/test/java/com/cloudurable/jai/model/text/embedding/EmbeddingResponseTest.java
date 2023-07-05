package com.cloudurable.jai.model.text.embedding;


import com.cloudurable.jai.model.Usage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmbeddingResponseTest {

    @Test
    void testEquals() {
        String object = "embedding";
        String model = "gpt2";
        Usage usage = Usage.builder().totalTokens(10).completionTokens(5).promptTokens(5).build();
        List<Embedding> data = Arrays.asList(
                Embedding.builder().index(0).embedding(new float[]{1.0f, 2.0f}).build(),
                Embedding.builder().index(1).embedding(new float[]{3.0f, 4.0f}).build()
        );

        EmbeddingResponse response1 = EmbeddingResponse.builder().object(object)
                .model(model).usage(usage).data(data).build();
        EmbeddingResponse response2 = new EmbeddingResponse(object, model, usage, data);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertEquals(response1.toString(), response2.toString());
        assertEquals(response1.getModel(), response2.getModel());
        assertEquals(response1.getData(), response2.getData());
        assertEquals(response1.getUsage(), response2.getUsage());
    }
}
