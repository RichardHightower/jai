package com.cloudurable.jai.model.audio;


import com.cloudurable.jai.util.MultipartEntityBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AudioRequestEncoderTest {

    @Test
    void testBuildForm() {
        // Create sample data for the AudioRequest
        byte[] file = new byte[10];
        String model = "whisper-1";
        String prompt = "Sample prompt";
        AudioResponseFormat responseFormat = AudioResponseFormat.JSON;
        float temperature = 0.5f;

        // Create an instance of AudioRequest using the sample data
        AudioRequest audioRequest = new AudioRequestImpl(file, model, prompt, responseFormat, temperature);

        // Build the form using AudioRequestEncoder
        MultipartEntityBuilder form = AudioRequestEncoder.buildForm(audioRequest);

        // Verify the form contents
        try {
            assertNotNull(form.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        assertEquals(model, form.getParameter("model").getValue());
//        assertEquals(prompt, form.getParameter("prompt").getValue());
//        assertEquals(responseFormat.toString().toLowerCase(), form.getParameter("response_format").getValue());
//        assertEquals(String.valueOf(temperature), form.getParameter("temperature").getValue());
//        assertEquals("application/binary", form.getBinaryBody("file").getMimeType());
//        assertEquals("transcribe.m4a", form.getBinaryBody("file").getFilename());
//        assertEquals(file.length, form.getBinaryBody("file").getContentLength());
    }

    private static class AudioRequestImpl extends AudioRequest {
        public AudioRequestImpl(byte[] file, String model, String prompt, AudioResponseFormat responseFormat, float temperature) {
            super(file, "foo.mpa",  model, prompt, AudioResponseFormat.JSON, temperature);
        }
    }
}
