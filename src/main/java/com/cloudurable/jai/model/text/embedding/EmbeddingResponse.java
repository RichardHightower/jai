package com.cloudurable.jai.model.text.embedding;

import com.cloudurable.jai.model.Response;
import com.cloudurable.jai.model.Usage;

import java.util.List;
import java.util.Objects;

/**
 * Represents the response for an embedding operation.
 */
public class EmbeddingResponse implements Response {

    private final String object;
    private final String model;
    private final Usage usage;
    private final List<Embedding> data;

    /**
     * Constructs a new EmbeddingResponse object.
     *
     * @param object The object associated with the response.
     * @param model  The model used for the embedding.
     * @param usage  The usage details of the response.
     * @param data   The list of Embedding objects containing the embeddings.
     */
    public EmbeddingResponse(String object, String model, Usage usage, List<Embedding> data) {
        this.object = object;
        this.model = model;
        this.usage = usage;
        this.data = data;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the object associated with the response.
     *
     * @return The object associated with the response.
     */
    public String getObject() {
        return object;
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
     * Returns the usage details of the response.
     *
     * @return The usage details of the response.
     */
    public Usage getUsage() {
        return usage;
    }

    /**
     * Returns the list of Embedding objects containing the embeddings.
     *
     * @return The list of Embedding objects containing the embeddings.
     */
    public List<Embedding> getData() {
        return data;
    }

    /**
     * Checks if this EmbeddingResponse is equal to another object.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmbeddingResponse)) return false;
        EmbeddingResponse that = (EmbeddingResponse) o;
        return Objects.equals(object, that.object) && Objects.equals(model, that.model) &&
                Objects.equals(usage, that.usage) && Objects.equals(data, that.data);
    }

    /**
     * Returns the hash code value for this EmbeddingResponse.
     *
     * @return The hash code value for this EmbeddingResponse.
     */
    @Override
    public int hashCode() {
        return Objects.hash(object, model, usage, data);
    }

    /**
     * Builder class for constructing EmbeddingResponse objects.
     */
    public static class Builder {

        private String object;
        private String model;
        private Usage usage;
        private List<Embedding> data;

        /**
         * Constructs a new Builder object.
         */
        private Builder() {
        }

        /**
         * Sets the object associated with the response.
         *
         * @param object The object associated with the response.
         * @return The Builder object.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the model used for the embedding.
         *
         * @param model The model used for the embedding.
         * @return The Builder object.
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the usage details of the response.
         *
         * @param usage The usage details of the response.
         * @return The Builder object.
         */
        public Builder usage(Usage usage) {
            this.usage = usage;
            return this;
        }

        /**
         * Sets the list of Embedding objects containing the embeddings.
         *
         * @param data The list of Embedding objects containing the embeddings.
         * @return The Builder object.
         */
        public Builder data(List<Embedding> data) {
            this.data = data;
            return this;
        }

        /**
         * Builds and returns a new EmbeddingResponse object.
         *
         * @return A new EmbeddingResponse object.
         */
        public EmbeddingResponse build() {
            return new EmbeddingResponse(object, model, usage, data);
        }
    }
}
