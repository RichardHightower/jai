package com.cloudurable.jai.model.text.edit;

import io.nats.jparse.Json;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EditResponseDeserializerTest {

    @Test
    void testDeserialize() {
        // Create a sample JSON response
        String jsonBody = Json.niceJson(
                "{" +
                "'object': 'object', " +
                "'created': 1628097025, " +
                "'usage': {'prompt_tokens':5, 'total_tokens':5}, " +
                "'choices': [" +
                "   {'index': 1, 'text': 'Choice 1'}, " +
                "   {'index': 2, 'text': 'Choice 2'} " +
                "  ] "+
                "}");

        // Deserialize the JSON response
        EditResponse response = EditResponseDeserializer.deserialize(jsonBody);

        // Verify the deserialized values
        assertEquals("object", response.getObject());
        assertNotNull(response.getCreated());
        assertNotNull(response.getUsage());
        List<EditChoice> choices = response.getChoices();
        assertNotNull(choices);
        assertEquals(2, choices.size());
        assertEquals(1, choices.get(0).getIndex());
        assertEquals("Choice 1", choices.get(0).getText());
        assertEquals(2, choices.get(1).getIndex());
        assertEquals("Choice 2", choices.get(1).getText());
    }
}
