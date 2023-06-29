package com.cloudurable.jai.model.text.completion.chat.function;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionalCallTest {

    @Test
    void testFunctionalCallBuilder() {
        // Create a list of arguments
        List<Argument<?>> arguments = Arrays.asList(
                Argument.builder().setParameterType(ParameterType.STRING).setValue("arg1").build(),
                Argument.builder().setParameterType(ParameterType.NUMBER).setValue(123).build()
        );

        // Create a FunctionalCall using the builder
        FunctionalCall functionalCall = FunctionalCall.builder()
                .setName("func")
                .setArguments(arguments)
                .build();

        // Verify the values of the FunctionalCall
        assertEquals("func", functionalCall.getName());
        assertEquals(arguments, functionalCall.getArguments());
    }

    @Test
    void testFunctionalCallEquality() {
        // Create two FunctionalCalls with the same values
        List<Argument<?>> arguments1 = Arrays.asList(
                Argument.builder().setParameterType(ParameterType.STRING).setValue("arg1").build(),
                Argument.builder().setParameterType(ParameterType.NUMBER).setValue(123).build()
        );
        List<Argument<?>> arguments2 = Arrays.asList(
                Argument.builder().setParameterType(ParameterType.STRING).setValue("arg1").build(),
                Argument.builder().setParameterType(ParameterType.NUMBER).setValue(123).build()
        );

        FunctionalCall functionalCall1 = FunctionalCall.builder()
                .setName("func")
                .setArguments(arguments1)
                .build();
        FunctionalCall functionalCall2 = FunctionalCall.builder()
                .setName("func")
                .setArguments(arguments2)
                .build();

        // Verify that the two FunctionalCalls are equal
        assertEquals(functionalCall1, functionalCall2);
    }
}
