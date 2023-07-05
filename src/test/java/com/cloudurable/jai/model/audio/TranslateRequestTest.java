package com.cloudurable.jai.model.audio;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslateRequestTest {

    @Test
    void testConstructorAndGetters() {
        // Create sample data for the TranslateRequest
        byte[] file = new byte[10];
        String fileName = "audio.m4a";
        String model = "whisper-1";
        String prompt = "Sample prompt";
        AudioResponseFormat responseFormat = AudioResponseFormat.JSON;
        float temperature = 0.5f;

        // Create an instance of TranslateRequest using the sample data
        TranslateRequest translateRequest = TranslateRequest.builder()
                .file(file)
                .fileName(fileName)
                .model(model)
                .prompt(prompt)
                .responseFormat(responseFormat)
                .temperature(temperature)
                .build();

        TranslateRequest translateRequest2 = TranslateRequest.builder()
                .file(new File("test.m4a"))
                .model(model)
                .prompt(prompt)
                .responseFormat(responseFormat)
                .temperature(temperature)
                .build();

        // Test the getters
        assertEquals(file, translateRequest.getFile());
        assertEquals(fileName, translateRequest.getFileName());
        assertEquals(model, translateRequest.getModel());
        assertEquals(prompt, translateRequest.getPrompt());
        assertEquals(responseFormat, translateRequest.getResponseFormat());
        assertEquals(temperature, translateRequest.getTemperature());

        assertEquals("test.m4a", translateRequest2.getFileName());
    }

    // Add more unit tests for other methods if needed
}
