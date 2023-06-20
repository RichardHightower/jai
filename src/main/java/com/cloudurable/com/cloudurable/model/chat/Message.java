package com.cloudurable.com.cloudurable.model.chat;

import com.cloudurable.com.cloudurable.model.chat.function.FunctionalCall;

import java.util.Objects;

/**
 * Represents a message in a chat system.
 */
public class Message {
    private final Role role;
    private final String content;
    private final String name;
    private final FunctionalCall functionCall;

    /**
     * Constructs a new Message object.
     *
     * @param role         the role of the sender
     * @param content      the content of the message
     * @param name         the name of the sender
     * @param functionCall the functional call associated with the message
     */
    public Message(Role role, String content, String name, FunctionalCall functionCall) {
        this.role = role;
        this.content = content;
        this.name = name;
        this.functionCall = functionCall;
    }

    /**
     * Gets the role of the sender.
     *
     * @return the role of the sender
     */
    public Role getRole() {
        return role;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the name of the sender.
     *
     * @return the name of the sender
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the functional call associated with the message.
     *
     * @return the functional call associated with the message
     */
    public FunctionalCall getFunctionCall() {
        return functionCall;
    }

    /**
     * Returns a string representation of the Message.
     *
     * @return a string representation of the Message
     */
    @Override
    public String toString() {
        return "Message{" +
                "role=" + role +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", functionCall=" + functionCall +
                '}';
    }

    /**
     * Checks if this Message is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return role == message.role &&
                Objects.equals(content, message.content) &&
                Objects.equals(name, message.name) &&
                Objects.equals(functionCall, message.functionCall);
    }

    /**
     * Computes the hash code for this Message.
     *
     * @return the hash code value for this Message
     */
    @Override
    public int hashCode() {
        return Objects.hash(role, content, name, functionCall);
    }

    /**
     * Returns a new Builder instance to construct a Message object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing Message objects.
     */
    public static class Builder {
        private Role role;
        private String content;
        private String name;
        private FunctionalCall functionCall;

        /**
         * Sets the role of the sender.
         *
         * @param role the role of the sender
         * @return the Builder instance
         */
        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        /**
         * Sets the content of the message.
         *
         * @param content the content of the message
         * @return the Builder instance
         */
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Sets the name of the sender.
         *
         * @param name the name of the sender
         * @return the Builder instance
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the functional call associated with the message.
         *
         * @param functionCall the functional call associated with the message
         * @return the Builder instance
         */
        public Builder setFunctionCall(FunctionalCall functionCall) {
            this.functionCall = functionCall;
            return this;
        }

        /**
         * Constructs a new Message object with the provided properties.
         *
         * @return a new Message object
         */
        public Message build() {
            return new Message(role, content, name, functionCall);
        }
    }
}
