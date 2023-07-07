package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.TextResponse;

import java.time.Instant;
import java.util.List;

/**
 * Represents a response from the text completion API.
 */
public class CompletionResponse extends TextResponse {

    private final List<CompletionChoice> choices;

    /**
     * Constructs a CompletionResponse object.
     *
     * @param id      The ID of the response.
     * @param object  The object type of the response.
     * @param created The timestamp indicating when the response was created.
     * @param usage   The usage information associated with the response.
     * @param choices The list of completion choices in the response.
     */
    public CompletionResponse(String id, String object, Instant created, Usage usage, List<CompletionChoice> choices) {
        super(id, object, created, usage);
        this.choices = choices;
    }

    /**
     * Returns a new builder instance to construct a CompletionResponse object.
     *
     * @return A new builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the list of completion choices in the response.
     *
     * @return The list of completion choices.
     */
    public List<CompletionChoice> getChoices() {
        return choices;
    }


    /**
     * Builder class for constructing CompletionResponse objects.
     */
    public static class Builder {

        private String id;
        private String object;
        private Instant created;
        private Usage usage;
        private List<CompletionChoice> choices;

        private Builder() {
        }

        /**
         * Sets the ID of the response.
         *
         * @param id The ID of the response.
         * @return The builder instance.
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object type of the response.
         *
         * @param object The object type of the response.
         * @return The builder instance.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the timestamp indicating when the response was created.
         *
         * @param created The timestamp of the response creation.
         * @return The builder instance.
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets the usage information associated with the response.
         *
         * @param usage The usage information of the response.
         * @return The builder instance.
         */
        public Builder usage(Usage usage) {
            this.usage = usage;
            return this;
        }

        /**
         * Sets the list of completion choices in the response.
         *
         * @param choices The list of completion choices.
         * @return The builder instance.
         */
        public Builder choices(List<CompletionChoice> choices) {
            this.choices = choices;
            return this;
        }

        /**
         * Builds a CompletionResponse object with the provided values.
         *
         * @return The constructed CompletionResponse object.
         */
        public CompletionResponse build() {
            return new CompletionResponse(id, object, created, usage, choices);
        }
    }
}
