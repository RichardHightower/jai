package com.cloudurable.jai.model.text.completion.chat.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArgumentTest {

    @Test
    void testArgumentBuilder() {
        // Create an Argument using the builder
        Argument<String> argument = Argument.<String>builder()
                .setParameterType(ParameterType.STRING)
                .setValue("Hello")
                .build();

        // Verify the values of the Argument
        assertEquals(ParameterType.STRING, argument.getParameterType());
        assertEquals("Hello", argument.getValue());
    }

    @Test
    void testArgumentEquality() {
        // Create two Arguments with the same values
        Argument<Integer> argument1 = Argument.<Integer>builder()
                .setParameterType(ParameterType.NUMBER)
                .setValue(123)
                .build();
        Argument<Integer> argument2 = Argument.<Integer>builder()
                .setParameterType(ParameterType.NUMBER)
                .setValue(123)
                .build();

        // Verify that the two Arguments are equal
        assertEquals(argument1, argument2);
    }
}
