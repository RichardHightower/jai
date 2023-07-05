package com.cloudurable.jai.model.audio;

import io.nats.jparse.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioResponseTest {

    @Test
    void testGetBody() {
        // Create a sample AudioResponse object
        String body = "Sample response body";
        AudioResponseFormat responseFormat = AudioResponseFormat.TEXT;
        AudioResponse audioResponse1 =  AudioResponse.builder().body(body)
                .responseFormat(responseFormat)
                .build();

        AudioResponse audioResponse2 =  AudioResponse.builder().body(body)
                .responseFormat(responseFormat)
                .build();

        // Verify the getBody() method
        assertEquals(body, audioResponse1.getBody());
        assertEquals(body, audioResponse1.getText());
        assertEquals(responseFormat, audioResponse1.getResponseFormat());

        assertEquals(audioResponse1, audioResponse2);
    }

    @Test
    void testGetText() {
        // Create a sample AudioResponse object with JSON format and an ObjectNode
        String body = "{\"text\":\"Sample text\"}";
        AudioResponseFormat responseFormat = AudioResponseFormat.JSON;
        AudioResponse audioResponse = new AudioResponse(body, responseFormat);

        // Verify the getText() method for JSON format
        assertEquals("Sample text", audioResponse.getText());

        // Create a sample AudioResponse object with a different format
        body = "Sample response body";
        responseFormat = AudioResponseFormat.TEXT;
        audioResponse = new AudioResponse(body, responseFormat);

        // Verify the getText() method for a different format
        assertEquals(body, audioResponse.getText());
    }

    @Test
    void testGetObjectNode() {
        // Create a sample AudioResponse object with JSON format and an ObjectNode
        String body = "{\"text\":\"Sample text\"}";
        AudioResponseFormat responseFormat = AudioResponseFormat.JSON;
        AudioResponse audioResponse = new AudioResponse(body, responseFormat);

        // Verify the getObjectNode() method for JSON format
        Optional<ObjectNode> objectNodeOptional = audioResponse.getObjectNode();
        assertTrue(objectNodeOptional.isPresent());
        assertEquals("Sample text", objectNodeOptional.get().getString("text"));

        // Create a sample AudioResponse object with a different format
        body = "Sample response body";
        responseFormat = AudioResponseFormat.TEXT;
        audioResponse = new AudioResponse(body, responseFormat);

        // Verify the getObjectNode() method for a different format
        objectNodeOptional = audioResponse.getObjectNode();
        assertFalse(objectNodeOptional.isPresent());
    }
}
