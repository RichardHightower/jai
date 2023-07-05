package com.cloudurable.jai.model.audio;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Represents a transcription request for audio processing.
 */
public class TranscriptionRequest extends AudioRequest {

    /**
     *  The language for the transcription.
     */
    private final String language;


    /**
     * Constructs a new TranscriptionRequest object.
     *
     * @param file           The byte array representing the audio file.
     * @param fileName       The file name of the audio file.
     * @param model          The model to use for transcription.
     * @param prompt         The prompt for the transcription.
     * @param responseFormat The format of the response.
     * @param temperature    The temperature for the transcription.
     * @param language       The language for the transcription.
     */
    public TranscriptionRequest(byte[] file, String fileName, String model, String prompt, AudioResponseFormat responseFormat, float temperature, String language) {
        super(file, fileName, model, prompt, responseFormat, temperature);
        this.language = language;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the language for the transcription.
     *
     * @return The language for the transcription.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Builder class for constructing TranscriptionRequest objects.
     */
    public static class Builder {
        private byte[] file;

        private  String fileName = "foo.m4a";
        private String model;
        private String prompt;
        private AudioResponseFormat responseFormat;
        private float temperature;
        private String language;

        /**
         * Sets the audio file for the transcription.
         *
         * @param file The byte array representing the audio file.
         * @return The Builder instance.
         */
        public Builder file(byte[] file) {
            this.file = file;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder file(File file) {
            try {
                this.file = Files.readAllBytes(file.toPath());
                this.fileName = file.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        /**
         * Sets the model to use for the transcription.
         *
         * @param model The model to use for the transcription.
         * @return The Builder instance.
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the prompt for the transcription.
         *
         * @param prompt The prompt for the transcription.
         * @return The Builder instance.
         */
        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        /**
         * Sets the response format for the transcription.
         *
         * @param responseFormat The format of the response.
         * @return The Builder instance.
         */
        public Builder responseFormat(AudioResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        /**
         * Sets the temperature for the transcription.
         *
         * @param temperature The temperature for the transcription.
         * @return The Builder instance.
         */
        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets the language for the transcription.
         *
         * @param language The language for the transcription.
         * @return The Builder instance.
         */
        public Builder language(String language) {
            this.language = language;
            return this;
        }

        /**
         * Builds and returns a new TranscriptionRequest object.
         *
         * @return A new TranscriptionRequest object.
         */
        public TranscriptionRequest build() {
            return new TranscriptionRequest(file, fileName, model, prompt, responseFormat, temperature, language);
        }
    }
}
