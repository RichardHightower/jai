package com.cloudurable.jai.model.text.edit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EditRequestTest {

    @Test
    void testConstructorAndGetters() {
        // Create sample data for the EditRequest
        String model = "sampleModel";
        float temperature = 0.8f;
        float topP = 0.5f;
        int completionCount = 3;
        String input = "Sample input";
        String instruction = "Sample instruction";

        // Create an instance of EditRequest using the sample data
        EditRequest editRequest = EditRequest.builder().model(model)
                .temperature(temperature).topP(topP).completionCount(3)
                .input(input).instruction(instruction)
                .build();
        // Test the getters
        assertEquals(model, editRequest.getModel());
        assertEquals(temperature, editRequest.getTemperature());
        assertEquals(topP, editRequest.getTopP());
        assertEquals(completionCount, editRequest.getCompletionCount());
        assertEquals(input, editRequest.getInput());
        assertEquals(instruction, editRequest.getInstruction());
    }

    @Test
    void testEqualsAndHashCode() {
        // Create two EditRequest objects with the same property values
        String model = "sampleModel";
        float temperature = 0.8f;
        float topP = 0.5f;
        int completionCount = 3;
        String input = "Sample input";
        String instruction = "Sample instruction";

        EditRequest editRequest1 = EditRequest.builder().model(model)
                .temperature(temperature).topP(topP).completionCount(3)
                .input(input).instruction(instruction)
                .build();

        EditRequest editRequest2 = EditRequest.builder().model(model)
                .temperature(temperature).topP(topP).completionCount(3)
                .input(input).instruction(instruction)
                .build();

        // Create another EditRequest object with different property values
        String differentModel = "differentModel";
        float differentTemperature = 0.5f;
        float differentTopP = 0.2f;
        int differentCompletionCount = 1;
        String differentInput = "Different input";
        String differentInstruction = "Different instruction";
        EditRequest differentEditRequest = new EditRequest(differentModel, differentTemperature, differentTopP, differentCompletionCount, differentInput, differentInstruction);

        // Test equals()
        assertEquals(editRequest1, editRequest2);
        assertNotEquals(editRequest1, differentEditRequest);

        // Test hashCode()
        assertEquals(editRequest1.hashCode(), editRequest2.hashCode());
        assertEquals(editRequest1.toString(), editRequest2.toString());
        assertNotEquals(editRequest1.hashCode(), differentEditRequest.hashCode());
    }

    // Add more unit tests for other methods if needed
}
