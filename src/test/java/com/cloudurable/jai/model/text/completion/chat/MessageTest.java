package com.cloudurable.jai.model.text.completion.chat;

import com.cloudurable.jai.model.text.completion.chat.function.FunctionalCall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @Test
    void testMessageBuilder() {
        // Create a FunctionalCall for the Message
        FunctionalCall functionalCall = FunctionalCall.builder()
                .setName("functionName")
                .build();

        // Create a Message using the builder
        Message message = Message.builder()
                .setRole(Role.SYSTEM)
                .setContent("This is a system message.")
                .setName("Sender")
                .setFunctionCall(functionalCall)
                .build();

        // Verify the values of the Message
        assertEquals(Role.SYSTEM, message.getRole());
        assertEquals("This is a system message.", message.getContent());
        assertEquals("Sender", message.getName());
        assertEquals(functionalCall, message.getFunctionCall());
    }

    @Test
    void testMessageEquality() {
        // Create two Messages with the same values
        FunctionalCall functionalCall = FunctionalCall.builder()
                .setName("functionName")
                .build();

        Message message1 = Message.builder()
                .setRole(Role.SYSTEM)
                .setContent("This is a system message.")
                .setName("Sender")
                .setFunctionCall(functionalCall)
                .build();
        Message message2 = Message.builder()
                .setRole(Role.SYSTEM)
                .setContent("This is a system message.")
                .setName("Sender")
                .setFunctionCall(functionalCall)
                .build();

        // Verify that the two Messages are equal
        assertEquals(message1, message2);
    }
}
