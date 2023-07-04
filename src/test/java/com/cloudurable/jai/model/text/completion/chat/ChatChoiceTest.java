package com.cloudurable.jai.model.text.completion.chat;

import com.cloudurable.jai.model.FinishReason;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatChoiceTest {

    @Test
    void testChatChoiceBuilder() {
        // Create a Message for the ChatChoice
        Message message = Message.builder()
                .role(Role.SYSTEM)
                .content("This is a system message.")
                .build();

        // Create a ChatChoice using the builder
        ChatChoice chatChoice = ChatChoice.builder()
                .index(1)
                .message(message)
                .finishReason(FinishReason.STOP)
                .build();

        // Verify the values of the ChatChoice
        assertEquals(1, chatChoice.getIndex());
        assertEquals(message, chatChoice.getMessage());
        assertEquals(FinishReason.STOP, chatChoice.getFinishReason());
    }


    @Test
    void testHashCodeToString() {
        // Create a Message for the ChatChoice
        Message message = Message.builder()
                .role(Role.SYSTEM)
                .content("This is a system message.")
                .build();

        // Create a ChatChoice using the builder
        ChatChoice chatChoice = ChatChoice.builder()
                .index(1)
                .message(message)
                .finishReason(FinishReason.STOP)
                .build();

        ChatChoice chatChoice2 = ChatChoice.builder()
                .index(1)
                .message(message)
                .finishReason(FinishReason.STOP)
                .build();

        // Verify the values of the ChatChoice
        assertEquals(chatChoice2.hashCode(), chatChoice.hashCode());
        assertEquals(chatChoice2.toString(), chatChoice.toString());
        assertEquals(chatChoice2, chatChoice);

    }

    @Test
    void testChatChoiceEquality() {
        // Create two ChatChoices with the same values
        Message message = Message.builder()
                .role(Role.SYSTEM)
                .content("This is a system message.")
                .build();

        ChatChoice chatChoice1 = ChatChoice.builder()
                .index(1)
                .message(message)
                .finishReason(FinishReason.STOP)
                .build();
        ChatChoice chatChoice2 = ChatChoice.builder()
                .index(1)
                .message(message)
                .finishReason(FinishReason.STOP)
                .build();

        // Verify that the two ChatChoices are equal
        assertEquals(chatChoice1, chatChoice2);
    }
}
