package com.cloudurable.jai.model.audio;

/**
 * Represents an audio response.
 */
public class AudioResponse {
    private final String body;

    /**
     * Constructs a new AudioResponse object.
     *
     * @param body The body of the audio response.
     */
    public AudioResponse(String body) {
        this.body = body;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the body of the audio response.
     *
     * @return The body of the audio response.
     */
    public String getBody() {
        return body;
    }

    /**
     * Builder class for constructing AudioResponse objects.
     */
    public static class Builder {
        private String body;

        /**
         * Constructs a new Builder object.
         */
        public Builder() {
        }

        /**
         * Sets the body of the audio response.
         *
         * @param body The body of the audio response.
         * @return The Builder instance.
         */
        public Builder body(String body) {
            this.body = body;
            return this;
        }

        /**
         * Builds and returns a new AudioResponse object.
         *
         * @return A new AudioResponse object.
         */
        public AudioResponse build() {
            return new AudioResponse(body);
        }
    }
}

