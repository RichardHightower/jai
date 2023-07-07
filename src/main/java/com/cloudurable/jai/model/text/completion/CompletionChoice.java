package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.text.Choice;

import java.util.List;

/**
 * Represents a choice for text completion.
 */
public class CompletionChoice extends Choice {
    /**
     * Text of the choice
     */
    private final String text;

    /**
     * The log probabilities associated with the choice.
     */
    private final List<Integer> logprobs;

    /**
     * Constructs a CompletionChoice object.
     *
     * @param index        The index of the choice.
     * @param finishReason The reason indicating completion status.
     * @param text         The text of the choice.
     * @param logprobs     The log probabilities associated with the choice.
     */
    public CompletionChoice(int index, FinishReason finishReason, String text, List<Integer> logprobs) {
        super(index, finishReason);
        this.text = text;
        this.logprobs = logprobs;
    }

    /**
     * builder
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the text of the choice.
     *
     * @return The text of the choice.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the log probabilities associated with the choice.
     *
     * @return The log probabilities associated with the choice.
     */
    public List<Integer> getLogprobs() {
        return logprobs;
    }

    @Override
    public String toString() {
        return "CompletionChoice{" +
                "text='" + text + '\'' +
                ", index=" + index +
                ", logprobs=" + logprobs +
                ", finishReason=" + finishReason +
                '}';
    }

    /**
     * Builder class for constructing CompletionChoice objects.
     */
    public static class Builder {
        private int index;

        ;
        private FinishReason finishReason;
        private String text;
        private List<Integer> logprobs;

        private Builder() {
        }

        /**
         * Sets the index of the choice.
         *
         * @param index The index of the choice.
         * @return The builder instance.
         */
        public Builder index(int index) {
            this.index = index;
            return this;
        }

        /**
         * Sets the finish reason indicating completion status.
         *
         * @param finishReason The finish reason indicating completion status.
         * @return The builder instance.
         */
        public Builder finishReason(FinishReason finishReason) {
            this.finishReason = finishReason;
            return this;
        }

        /**
         * Sets the text of the choice.
         *
         * @param text The text of the choice.
         * @return The builder instance.
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the log probabilities associated with the choice.
         *
         * @param logprobs The log probabilities associated with the choice.
         * @return The builder instance.
         */
        public Builder logprobs(List<Integer> logprobs) {
            this.logprobs = logprobs;
            return this;
        }

        /**
         * Builds a CompletionChoice object with the provided values.
         *
         * @return The constructed CompletionChoice object.
         */
        public CompletionChoice build() {
            return new CompletionChoice(index, finishReason, text, logprobs);
        }
    }
}
