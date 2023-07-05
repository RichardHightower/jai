package com.cloudurable.jai.model.audio;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents an audio response.
 */
public class AudioResponse {
    private final String body;
    private final AudioResponseFormat responseFormat;
    private ObjectNode objectNode = null;

    /**
     * Constructs a new AudioResponse object.
     *
     * @param body           The body of the audio response.
     * @param responseFormat The response format.
     */
    public AudioResponse(String body, AudioResponseFormat responseFormat) {
        this.body = body;
        this.responseFormat = responseFormat;
    }

    /**
     * Get builder for AudioResponse
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * AudioResponseFormat
     *
     * @return AudioResponseFormat
     */
    public AudioResponseFormat getResponseFormat() {
        return responseFormat;
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
     * Returns the text content of the audio response based on the response format.
     * For JSON and VERBOSE_JSON formats, it retrieves the "text" field from the parsed JSON.
     * For other formats, it returns the original response body.
     *
     * @return The text content of the audio response.
     */
    public String getText() {
        switch (this.responseFormat) {
            case JSON:
            case VERBOSE_JSON:
                return this.getObjectNode().isPresent() ? this.objectNode.getString("text") : "";
            default:
                return body;
        }
    }

    /**
     * Returns the parsed ObjectNode of the audio response, if available.
     * For JSON and VERBOSE_JSON formats, it parses the response body using a JSON parser and retrieves the ObjectNode.
     * For other formats, it returns an empty Optional.
     *
     * @return The parsed ObjectNode of the audio response, or an empty Optional.
     */
    public Optional<ObjectNode> getObjectNode() {
        if (objectNode == null) {
            switch (this.responseFormat) {
                case JSON:
                case VERBOSE_JSON:
                    JsonParser jsonParser = JsonParserBuilder.builder().build();
                    this.objectNode = jsonParser.parse(this.body).getObjectNode();
                    break;
                default:
                    return Optional.empty();
            }
        }
        return Optional.ofNullable(objectNode);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioResponse)) return false;
        AudioResponse that = (AudioResponse) o;
        return Objects.equals(body, that.body) && Objects.equals(objectNode, that.objectNode) && responseFormat == that.responseFormat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, objectNode, responseFormat);
    }

    /**
     * Builder class for constructing AudioResponse objects.
     */
    public static class Builder {
        private String body;
        private AudioResponseFormat responseFormat;

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
         * Sets the response format of the audio response.
         *
         * @param responseFormat The response format.
         * @return The Builder instance.
         */
        public Builder responseFormat(AudioResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        /**
         * Builds and returns a new AudioResponse object.
         *
         * @return A new AudioResponse object.
         */
        public AudioResponse build() {
            return new AudioResponse(body, responseFormat);
        }
    }
}
