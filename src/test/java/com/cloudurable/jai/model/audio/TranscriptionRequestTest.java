package com.cloudurable.jai.model.audio;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranscriptionRequestTest {

    @Test
    void testGetLanguage() {
        // Create sample data for the TranscriptionRequest
        byte[] file = new byte[10];
        String fileName = "audio.m4a";
        String model = "whisper-1";
        String prompt = "Sample prompt";
        AudioResponseFormat responseFormat = AudioResponseFormat.JSON;
        float temperature = 0.5f;
        String language = "en-US";

        // Create an instance of TranscriptionRequest using the sample data
        TranscriptionRequest transcriptionRequest = TranscriptionRequest.builder()
                .file(file)
                .fileName(fileName)
                .model(model)
                .prompt(prompt)
                .responseFormat(responseFormat)
                .temperature(temperature)
                .language(language)
                .build();

        TranscriptionRequest transcriptionRequest2 = TranscriptionRequest.builder()
                .file(new File("test.m4a"))
                .model(model)
                .prompt(prompt)
                .responseFormat(responseFormat)
                .temperature(temperature)
                .language(language)
                .build();

        //.file(new File("test.m4a"))
        //

        // Test the getLanguage() method
        assertEquals(language, transcriptionRequest.getLanguage());
    }

    // Add more unit tests for other methods if needed
}
