package com.cloudurable.jai.model.moderation;

import com.cloudurable.jai.model.Request;

import java.util.Objects;


/**
 * CreateFineTuneRequest object.
 */
public class CreateModerationRequest implements Request {

    /**
     * input
     * string or array
     * Required
     * The input text to classify
     */
    private final String input;

    /**
     * model
     * string
     * Optional
     * Defaults to curie
     * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci", or a
     * fine-tuned model created after 2022-04-21. To learn more about these models, see the Models documentation.
     */
    private final String model;

    public CreateModerationRequest(String input, String model) {
        this.input = input;
        this.model = model;
    }

    public String getInput() {
        return input;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateModerationRequest)) return false;
        CreateModerationRequest that = (CreateModerationRequest) o;
        return Objects.equals(input, that.input) && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, model);
    }

    @Override
    public String toString() {
        return "CreateModerationRequest{" +
                "input='" + input + '\'' +
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
         * input
         * string or array
         * Required
         * The input text to classify
         */

        private String input;


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
         * Sets the input to classify.
         *
         * @param input The input to classify
         * @return the Builder instance
         */
        public Builder input(String input) {
            this.input = input;
            return this;
        }

        /**
         * Builds a new instance of CreateFineTuneRequest using the configured values.
         *
         * @return a new instance of CreateFineTuneRequest
         */
        public CreateModerationRequest build() {
            return new CreateModerationRequest(input, model);
        }
    }
}
