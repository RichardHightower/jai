package com.cloudurable.jai.model.text.completion.chat.function;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTest {

    @Test
    void testFunctionBuilder() {
        // Create a list of parameters
        List<Parameter> parameters = Arrays.asList(
                Parameter.builder().setName("param1").build(),
                Parameter.builder().setName("param2").build()
        );

        // Create a Function using the builder
        Function function = Function.builder()
                .setName("myFunction")
                .setDescription("My function description")
                .setParameters(parameters)
                .build();

        // Verify the values of the Function
        assertEquals("myFunction", function.getName());
        assertEquals("My function description", function.getDescription());
        assertEquals(parameters, function.getParameters());
    }

    @Test
    void testFunctionEquality() {
        // Create two Functions with the same values
        List<Parameter> parameters1 = Arrays.asList(
                Parameter.builder().setName("param1").build(),
                Parameter.builder().setName("param2").build()
        );
        List<Parameter> parameters2 = Arrays.asList(
                Parameter.builder().setName("param1").build(),
                Parameter.builder().setName("param2").build()
        );

        Function function1 = Function.builder()
                .setName("myFunction")
                .setDescription("My function description")
                .setParameters(parameters1)
                .build();
        Function function2 = Function.builder()
                .setName("myFunction")
                .setDescription("My function description")
                .setParameters(parameters2)
                .build();

        // Verify that the two Functions are equal
        assertEquals(function1, function2);
    }
}
