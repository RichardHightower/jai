package com.cloudurable.jai.model.moderation;

import com.cloudurable.jai.model.Response;
import io.nats.jparse.node.ArrayNode;

import java.util.Objects;


/**
 * CreateModerationResponse object.
 */
public class CreateModerationResponse implements Response {

    /**
     * id
     * string or array
     * Required
     * The input text to classify
     */
    private final String id;

    /**
     * model
     * string
     * Optional
     * Defaults to curie
     * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci", or a
     * fine-tuned model created after 2022-04-21. To learn more about these models, see the Models documentation.
     */
    private final String model;

    private final ArrayNode results;

    public CreateModerationResponse(String id, String model, ArrayNode results) {
        this.id = id;
        this.model = model;
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public ArrayNode getResults() {
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateModerationResponse)) return false;
        CreateModerationResponse that = (CreateModerationResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model);
    }

    @Override
    public String toString() {
        return "CreateModerationResponse{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    /**
     * Returns a new instance of the Builder for constructing CreateFineTuneRequest objects.
     *
     * @return a new instance of the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for constructing CreateFineTuneRequest instances.
     * <p>
     * /**
     * Builder for constructing UploadFileRequest instances.
     */
    public static class Builder {
        private Builder(){}


        /**
         * id
         * string or array
         * Required
         * The input text to classify
         */

        private String id;


        /**
         * Results of moderation.
         */
        private ArrayNode results;


        /**
         * model
         * string
         * Optional
         * Defaults to curie
         * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci", or a
         * fine-tuned model created after 2022-04-21. To learn more about these models, see the Models documentation.
         */
        private String model;

        /**
         * Sets the base model
         *
         * @param model The name of the base model.
         * @return the Builder instance
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the results from classify.
         *
         * @param results The results from classify
         * @return the Builder instance
         */
        public Builder results(ArrayNode results) {
            this.results = results;
            return this;
        }

        /**
         * Sets the id from classify.
         *
         * @param id The id to classify
         * @return the Builder instance
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Builds a new instance of CreateModerationResponse using the configured values.
         *
         * @return a new instance of CreateModerationResponse
         */
        public CreateModerationResponse build() {
            return new CreateModerationResponse(id, model, results);
        }
    }
}
