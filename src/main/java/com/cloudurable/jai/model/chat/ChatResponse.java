package com.cloudurable.jai.model.chat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chat response containing the ID, object, creation time, chat choices, and usage statistics.
 */
public class ChatResponse {
    private final String id;
    private final String object;
    private final Instant created;
    private final List<ChatChoice> choices;
    private final Usage usage;

    /**
     * Constructs a ChatResponse object.
     *
     * @param id      The ID of the chat response.
     * @param object  The object of the chat response.
     * @param created The creation time of the chat response.
     * @param choices The list of chat choices.
     * @param usage   The usage statistics for the chat response.
     */
    public ChatResponse(String id, String object, Instant created, List<ChatChoice> choices, Usage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.choices = choices;
        this.usage = usage;
    }

    /**
     * Builder builder
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the ID of the chat response.
     *
     * @return The ID of the chat response.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the object of the chat response.
     *
     * @return The object of the chat response.
     */
    public String getObject() {
        return object;
    }

    /**
     * Returns the creation time of the chat response.
     *
     * @return The creation time of the chat response.
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Returns the list of chat choices.
     *
     * @return The list of chat choices.
     */
    public List<ChatChoice> getChoices() {
        return choices;
    }

    /**
     * Returns the usage statistics for the chat response.
     *
     * @return The usage statistics for the chat response.
     */
    public Usage getUsage() {
        return usage;
    }

    /**
     * Generates a string representation of the ChatResponse object.
     *
     * @return The string representation of the ChatResponse object.
     */
    @Override
    public String toString() {
        return "ChatResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", choices=" + choices +
                ", usage=" + usage +
                '}';
    }

    /**
     * Checks if the ChatResponse object is equal to another object.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatResponse that = (ChatResponse) o;
        return id.equals(that.id) && object.equals(that.object) && created.equals(that.created) && choices.equals(that.choices) && usage.equals(that.usage);
    }

    /**
     * Computes the hash code of the ChatResponse object.
     *
     * @return The hash code of the ChatResponse object.
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + object.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + choices.hashCode();
        result = 31 * result + usage.hashCode();
        return result;
    }

    /**
     * Builder pattern for constructing ChatResponse objects.
     */
    public static class Builder {
        private String id;
        private String object;
        private Instant created;
        private List<ChatChoice> choices;
        private Usage usage;

        private Builder() {
        }

        /**
         * Sets the ID of the chat response.
         *
         * @param id The ID of the chat response.
         * @return The Builder instance.
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object of the chat response.
         *
         * @param object The object of the chat response.
         * @return The Builder instance.
         */
        public Builder setObject(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the creation time of the chat response.
         *
         * @param created The creation time of the chat response.
         * @return The Builder instance.
         */
        public Builder setCreated(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Add Choice
         *
         * @param choice choice
         * @return this
         */
        public Builder addChoice(ChatChoice choice) {
            this.getChoices().add(choice);
            return this;
        }

        /**
         * Get Choices
         *
         * @return choices
         */
        private List<ChatChoice> getChoices() {
            if (choices == null) {
                choices = new ArrayList<>();
            }
            return choices;
        }

        /**
         * Sets the list of chat choices.
         *
         * @param choices The list of chat choices.
         * @return The Builder instance.
         */
        public Builder setChoices(List<ChatChoice> choices) {
            this.choices = choices;
            return this;
        }

        /**
         * Sets the usage statistics for the chat response.
         *
         * @param usage The usage statistics for the chat response.
         * @return The Builder instance.
         */
        public Builder setUsage(Usage usage) {
            this.usage = usage;
            return this;
        }

        /**
         * Builds a ChatResponse object.
         *
         * @return The constructed ChatResponse object.
         */
        public ChatResponse build() {
            return new ChatResponse(id, object, created, choices, usage);
        }
    }
}
