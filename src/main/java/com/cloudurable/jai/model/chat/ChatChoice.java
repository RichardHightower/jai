package com.cloudurable.jai.model.chat;


import com.cloudurable.jai.model.FinishReason;

/**
 * Represents a chat choice with an index, message, and finish reason.
 */
public class ChatChoice {
    private final int index;
    private final Message message;
    private final FinishReason finishReason;

    /**
     * Constructs a ChatChoice object.
     *
     * @param index        The index of the chat choice.
     * @param message      The message associated with the chat choice.
     * @param finishReason The finish reason for the chat choice.
     */
    public ChatChoice(int index, Message message, FinishReason finishReason) {
        this.index = index;
        this.message = message;
        this.finishReason = finishReason;
    }

    /**
     * Builder
     *
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the index of the chat choice.
     *
     * @return The index of the chat choice.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the message associated with the chat choice.
     *
     * @return The message associated with the chat choice.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Returns the finish reason for the chat choice.
     *
     * @return The finish reason for the chat choice.
     */
    public FinishReason getFinishReason() {
        return finishReason;
    }

    /**
     * Generates a string representation of the ChatChoice object.
     *
     * @return The string representation of the ChatChoice object.
     */
    @Override
    public String toString() {
        return "ChatChoice{" +
                "index=" + index +
                ", message=" + message +
                ", finishReason=" + finishReason +
                '}';
    }

    /**
     * Checks if the ChatChoice object is equal to another object.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatChoice that = (ChatChoice) o;
        return index == that.index && message.equals(that.message) && finishReason == that.finishReason;
    }

    /**
     * Computes the hash code of the ChatChoice object.
     *
     * @return The hash code of the ChatChoice object.
     */
    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + message.hashCode();
        result = 31 * result + finishReason.hashCode();
        return result;
    }

    /**
     * Builder pattern for constructing ChatChoice objects.
     */
    public static class Builder {
        private int index;
        private Message message;
        private FinishReason finishReason;

        private Builder() {
        }

        /**
         * Sets the index for the chat choice.
         *
         * @param index The index of the chat choice.
         * @return The Builder instance.
         */
        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        /**
         * Sets the message for the chat choice.
         *
         * @param message The message associated with the chat choice.
         * @return The Builder instance.
         */
        public Builder setMessage(Message message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the finish reason for the chat choice.
         *
         * @param finishReason The finish reason for the chat choice.
         * @return The Builder instance.
         */
        public Builder setFinishReason(FinishReason finishReason) {
            this.finishReason = finishReason;
            return this;
        }

        /**
         * Builds a ChatChoice object.
         *
         * @return The constructed ChatChoice object.
         */
        public ChatChoice build() {
            return new ChatChoice(index, message, finishReason);
        }
    }
}

