package com.cloudurable.jai.model.text.embedding;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an embedding.
 */
public class Embedding {

    private final String object = "embedding";
    private final int index;
    private final float[] embedding;

    /**
     * Constructs a new Embedding object.
     *
     * @param index     The index of the embedding.
     * @param embedding The array of float values representing the embedding.
     */
    public Embedding(int index, float[] embedding) {
        this.index = index;
        this.embedding = embedding;
    }

    /**
     * Returns a new Builder instance to build an Embedding.
     *
     * @return A new Builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the index of the embedding.
     *
     * @return The index of the embedding.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the array of float values representing the embedding.
     *
     * @return The array of float values representing the embedding.
     */
    public float[] getEmbedding() {
        return embedding;
    }

    /**
     * Checks if this Embedding is equal to another object.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Embedding)) return false;
        Embedding embedding1 = (Embedding) o;
        return index == embedding1.index && Objects.equals(object, embedding1.object) && Arrays.equals(embedding, embedding1.embedding);
    }

    /**
     * Returns the hash code value for this Embedding.
     *
     * @return The hash code value for this Embedding.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(object, index);
        result = 31 * result + Arrays.hashCode(embedding);
        return result;
    }

    /**
     * Returns a string representation of the Embedding.
     *
     * @return A string representation of the Embedding.
     */
    @Override
    public String toString() {
        return "Embedding{" +
                "object='" + object + '\'' +
                ", index=" + index +
                ", embedding=" + Arrays.toString(embedding) +
                '}';
    }

    /**
     * Builder class for constructing Embedding objects.
     */
    public static class Builder {
        private int index;
        private float[] embedding;

        /**
         * Constructs a new Builder object.
         */
        private Builder() {
        }

        /**
         * Sets the index of the embedding.
         *
         * @param index The index of the embedding.
         * @return The Builder instance.
         */
        public Builder index(int index) {
            this.index = index;
            return this;
        }

        /**
         * Sets the array of float values representing the embedding.
         *
         * @param embedding The array of float values representing the embedding.
         * @return The Builder instance.
         */
        public Builder embedding(float[] embedding) {
            this.embedding = embedding;
            return this;
        }

        /**
         * Builds and returns a new Embedding object.
         *
         * @return A new Embedding object.
         */
        public Embedding build() {
            return new Embedding(index, embedding);
        }
    }
}
