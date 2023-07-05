package com.cloudurable.jai.model.audio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Represents a translate request for audio processing.
 */
public class TranslateRequest extends AudioRequest {

    /**
     * Constructs a new TranslateRequest object.
     *
     * @param file           The byte array representing the audio file.
     * @param fileName       The file name of the audio file.
     * @param model          The model to use for translation.
     * @param prompt         The prompt for the translation.
     * @param responseFormat The format of the response.
     * @param temperature    The temperature for the translation.
     */
    public TranslateRequest(byte[] file, String fileName, String model, String prompt, AudioResponseFormat responseFormat, float temperature) {
        super(file, fileName, model, prompt, responseFormat, temperature);
    }

    /**
     * Builder
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing TranslateRequest objects.
     */
    public static class Builder {

        private byte[] file;
        private String fileName = "foo.m4a";
        private String model = "whisper-1";
        private String prompt;
        private AudioResponseFormat responseFormat;
        private float temperature;
        private Builder() {
        }

        /**
         * Sets the audio file for the translation.
         *
         * @param file The byte array representing the audio file.
         * @return The Builder instance.
         */
        public Builder file(byte[] file) {
            this.file = file;
            return this;
        }

        /**
         * fileName
         *
         * @param fileName fileName
         * @return this
         */
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
         * Sets the model to use for the translation.
         *
         * @param model The model to use for the translation.
         * @return The Builder instance.
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the prompt for the translation.
         *
         * @param prompt The prompt for the translation.
         * @return The Builder instance.
         */
        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        /**
         * Sets the response format for the translation.
         *
         * @param responseFormat The format of the response.
         * @return The Builder instance.
         */
        public Builder responseFormat(AudioResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        /**
         * Sets the temperature for the translation.
         *
         * @param temperature The temperature for the translation.
         * @return The Builder instance.
         */
        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Builds and returns a new TranslateRequest object.
         *
         * @return A new TranslateRequest object.
         */
        public TranslateRequest build() {
            return new TranslateRequest(file, fileName, model, prompt, responseFormat, temperature);
        }
    }
}
