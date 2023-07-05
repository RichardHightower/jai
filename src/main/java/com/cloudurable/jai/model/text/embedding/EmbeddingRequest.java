package com.cloudurable.jai.model.text.embedding;

import com.cloudurable.jai.model.Request;

import java.util.Objects;

/**
 * Represents a request for an embedding operation.
 */
public class EmbeddingRequest implements Request {

    private final String model;
    private final String input;

    /**
     * Constructs a new EmbeddingRequest object.
     *
     * @param model The model to use for the embedding.
     * @param input The input text for the embedding.
     */
    public EmbeddingRequest(String model, String input) {
        this.model = model;
        this.input = input;
    }

    /**
     * Returns a new Builder instance to build an EmbeddingRequest.
     *
     * @return A new Builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the model used for the embedding.
     *
     * @return The model used for the embedding.
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the input text for the embedding.
     *
     * @return The input text for the embedding.
     */
    public String getInput() {
        return input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmbeddingRequest)) return false;
        EmbeddingRequest that = (EmbeddingRequest) o;
        return Objects.equals(model, that.model) && Objects.equals(input, that.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, input);
    }

    @Override
    public String toString() {
        return "EmbeddingRequest{" +
                "model='" + model + '\'' +
                ", input='" + input + '\'' +
                '}';
    }

    /**
     * Builder class for constructing EmbeddingRequest objects.
     */
    public static class Builder {

        private String model;
        private String input;

        /**
         * Constructs a new Builder object.
         */
        private Builder() {
        }

        /**
         * Sets the model to use for the embedding.
         *
         * @param model The model to use for the embedding.
         * @return The Builder instance.
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the input text for the embedding.
         *
         * @param input The input text for the embedding.
         * @return The Builder instance.
         */
        public Builder input(String input) {
            this.input = input;
            return this;
        }

        /**
         * Builds and returns a new EmbeddingRequest object.
         *
         * @return A new EmbeddingRequest object.
         */
        public EmbeddingRequest build() {
            return new EmbeddingRequest(model, input);
        }
    }
}
