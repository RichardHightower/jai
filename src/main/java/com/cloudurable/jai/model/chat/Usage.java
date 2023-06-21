package com.cloudurable.jai.model.chat;

/**
 * Represents the usage statistics for a chat conversation.
 */
public class Usage {
    private final int promptTokens;
    private final int completionTokens;
    private final int totalTokens;

    /**
     * Constructs a Usage object.
     *
     * @param promptTokens     The number of tokens in the prompt.
     * @param completionTokens The number of tokens in the completion.
     * @param totalTokens      The total number of tokens.
     */
    public Usage(int promptTokens, int completionTokens, int totalTokens) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }

    /**
     * Builder
     *
     * @return builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the number of tokens in the prompt.
     *
     * @return The number of tokens in the prompt.
     */
    public int getPromptTokens() {
        return promptTokens;
    }

    /**
     * Returns the number of tokens in the completion.
     *
     * @return The number of tokens in the completion.
     */
    public int getCompletionTokens() {
        return completionTokens;
    }

    /**
     * Returns the total number of tokens.
     *
     * @return The total number of tokens.
     */
    public int getTotalTokens() {
        return totalTokens;
    }

    /**
     * Generates a string representation of the Usage object.
     *
     * @return The string representation of the Usage object.
     */
    @Override
    public String toString() {
        return "Usage{" +
                "promptTokens=" + promptTokens +
                ", completionTokens=" + completionTokens +
                ", totalTokens=" + totalTokens +
                '}';
    }

    /**
     * Checks if the Usage object is equal to another object.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usage usage = (Usage) o;
        return promptTokens == usage.promptTokens && completionTokens == usage.completionTokens && totalTokens == usage.totalTokens;
    }

    /**
     * Computes the hash code of the Usage object.
     *
     * @return The hash code of the Usage object.
     */
    @Override
    public int hashCode() {
        int result = promptTokens;
        result = 31 * result + completionTokens;
        result = 31 * result + totalTokens;
        return result;
    }

    /**
     * Builder pattern for constructing Usage objects.
     */
    public static class Builder {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;

        private Builder() {
        }

        /**
         * Sets the number of tokens in the prompt.
         *
         * @param promptTokens The number of tokens in the prompt.
         * @return The Builder instance.
         */
        public Builder setPromptTokens(int promptTokens) {
            this.promptTokens = promptTokens;
            return this;
        }

        /**
         * Sets the number of tokens in the completion.
         *
         * @param completionTokens The number of tokens in the completion.
         * @return The Builder instance.
         */
        public Builder setCompletionTokens(int completionTokens) {
            this.completionTokens = completionTokens;
            return this;
        }

        /**
         * Sets the total number of tokens.
         *
         * @param totalTokens The total number of tokens.
         * @return The Builder instance.
         */
        public Builder setTotalTokens(int totalTokens) {
            this.totalTokens = totalTokens;
            return this;
        }

        /**
         * Builds a Usage object.
         *
         * @return The constructed Usage object.
         */
        public Usage build() {
            return new Usage(promptTokens, completionTokens, totalTokens);
        }
    }
}
