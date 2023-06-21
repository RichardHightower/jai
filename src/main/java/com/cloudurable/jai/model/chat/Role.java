package com.cloudurable.jai.model.chat;

/**
 * Represents the roles in a chat system.
 */
public enum Role {

    /**
     * The role representing the system.
     */
    SYSTEM,

    /**
     * The role representing a user.
     */
    USER,

    /**
     * The role representing an assistant.
     */
    ASSISTANT,

    /**
     * The role representing a function.
     */
    FUNCTION,

    /**
     * The role representing other or something we don't understand yet
     */
    OTHER
}
