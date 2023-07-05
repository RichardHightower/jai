package com.cloudurable.jai.model.audio;

import com.cloudurable.jai.util.MultipartEntityBuilder;

/**
 * Encoder class for building a multipart form for an audio transcription request.
 */
public class AudioRequestEncoder {

    /**
     * Builds a multipart form for an audio transcription request.
     *
     * @param audioRequest The audio transcription request.
     * @return The constructed MultipartEntityBuilder object.
     */
    public static MultipartEntityBuilder buildForm(AudioRequest audioRequest) {
        MultipartEntityBuilder form = MultipartEntityBuilder.create()
                .addTextBody("model", audioRequest.getModel());

        // Add prompt if available
        if (audioRequest.getPrompt() != null) {
            form.addTextBody("prompt", audioRequest.getPrompt());
        }

        // Add response format if available
        if (audioRequest.getResponseFormat() != null) {
            form.addTextBody("response_format", audioRequest.getResponseFormat().toString().toLowerCase());
        }

        // Add temperature if not zero
        if (audioRequest.getTemperature() != 0.0) {
            form.addTextBody("temperature", String.valueOf(audioRequest.getTemperature()));
        }

        // Add the audio file as binary body
        form.addBinaryBody("file", audioRequest.getFile(), "application/binary", "transcribe.m4a");

        return form;
    }
}
