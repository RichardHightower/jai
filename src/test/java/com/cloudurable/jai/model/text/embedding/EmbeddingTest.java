package com.cloudurable.jai.model.text.embedding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmbeddingTest {

    @Test
    void testBuilder() {
        int index = 1;
        float[] embedding = {0.1f, 0.2f, 0.3f};

        Embedding.Builder builder = Embedding.builder();
        builder.index(index);
        builder.embedding(embedding);

        Embedding result = builder.build();

        assertEquals(index, result.getIndex());
        assertArrayEquals(embedding, result.getEmbedding());
    }

    @Test
    void testEqualsAndHashCode() {
        int index = 1;
        float[] embedding = {0.1f, 0.2f, 0.3f};

        Embedding embedding1 = new Embedding(index, embedding);
        Embedding embedding2 = new Embedding(index, embedding);

        assertEquals(embedding1, embedding2);
        assertEquals(embedding1.hashCode(), embedding2.hashCode());
    }

    @Test
    void testToString() {
        int index = 1;
        float[] embedding = {0.1f, 0.2f, 0.3f};

        Embedding embedding1 = new Embedding(index, embedding);
        String expectedString = "Embedding{object='embedding', index=1, embedding=[0.1, 0.2, 0.3]}";

        assertEquals(expectedString, embedding1.toString());
    }
}
