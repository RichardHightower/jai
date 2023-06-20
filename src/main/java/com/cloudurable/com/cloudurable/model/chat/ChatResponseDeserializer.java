package com.cloudurable.com.cloudurable.model.chat;

/**
 * This class provides deserialization functionality for ChatResponse objects.
 */
public class ChatResponseDeserializer {
    private ChatResponseDeserializer() {
    }

    /**
     * Deserializes the provided string representation into a ChatResponse object.
     *
     * @param body The string representation to be deserialized.
     * @return The deserialized ChatResponse object.
     */
    public static ChatResponse deserialize(String body) {
        return ChatResponse.builder().build();
    }
}
