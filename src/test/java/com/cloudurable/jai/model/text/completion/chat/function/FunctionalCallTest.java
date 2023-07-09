package com.cloudurable.jai.model.text.completion.chat.function;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionalCallTest {

    @Test
    void testFunctionalCallBuilder() {
        // Create a list of arguments


        // Create a FunctionalCall using the builder
        FunctionalCall functionalCall = FunctionalCall.builder()
                .name("func")
                .build();

        // Verify the values of the FunctionalCall
        assertEquals("func", functionalCall.getName());
    }

    @Test
    void testFunctionalCallEquality() {
        // Create two FunctionalCalls with the same values


        FunctionalCall functionalCall1 = FunctionalCall.builder()
                .name("func")
                .build();
        FunctionalCall functionalCall2 = FunctionalCall.builder()
                .name("func")
                .build();

        // Verify that the two FunctionalCalls are equal
        assertEquals(functionalCall1, functionalCall2);
        assertEquals(functionalCall1.toString(),
                functionalCall2.toString());
        assertEquals(functionalCall1.hashCode(),
                functionalCall2.hashCode());
    }
}
