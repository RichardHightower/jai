package com.cloudurable.jai.model.audio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioRequestTest {

    @Test
    void testGetters() {
        // Create sample data for the AudioRequest
        byte[] file = new byte[10];
        String fileName = "audio.mp3";
        String model = "whisper-1";
        String prompt = "Sample prompt";
        AudioResponseFormat responseFormat = AudioResponseFormat.JSON;
        float temperature = 0.5f;

        // Create an instance of AudioRequest using the sample data
        AudioRequest audioRequest = new AudioRequestImpl(file, fileName, model, prompt, responseFormat, temperature);

        // Test the getters
        assertEquals(file, audioRequest.getFile());
        assertEquals(fileName, audioRequest.getFileName());
        assertEquals(model, audioRequest.getModel());
        assertEquals(prompt, audioRequest.getPrompt());
        assertEquals(responseFormat, audioRequest.getResponseFormat());
        assertEquals(temperature, audioRequest.getTemperature());
    }

    private static class AudioRequestImpl extends AudioRequest {
        public AudioRequestImpl(byte[] file, String fileName, String model, String prompt, AudioResponseFormat responseFormat, float temperature) {
            super(file, fileName, model, prompt, responseFormat, temperature);
        }
    }
}
