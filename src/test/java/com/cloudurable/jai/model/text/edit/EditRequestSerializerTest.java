package com.cloudurable.jai.model.text.edit;

import io.nats.jparse.Json;
import io.nats.jparse.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditRequestSerializerTest {

    @Test
    void testSerialize() {
        // Create sample data for the EditRequest
        String model = "sampleModel";
        float temperature = 0.8f;
        float topP = 0.5f;
        int completionCount = 3;
        String input = "Sample input";
        String instruction = "Sample instruction";
        EditRequest editRequest = new EditRequest(model, temperature, topP, completionCount, input, instruction);

        // Serialize the EditRequest
        String serializedJson = EditRequestSerializer.serialize(editRequest);

        ObjectNode objectNode = Json.toObjectNode(serializedJson);

        // Assert that the serialized JSON matches the expected JSON string
        assertEquals(0.5f, objectNode.getFloat("top_p"), 0.01);
        assertEquals(0.8f, objectNode.getFloat("temperature"), 0.01);
        assertEquals("Sample input", objectNode.getString("input"));
        assertEquals("Sample instruction", objectNode.getString("instruction"));
        assertEquals(3, objectNode.getInt("n"));
    }

    // Add more unit tests for other scenarios if needed
}
