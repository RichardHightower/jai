package com.cloudurable.jai.model.audio;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.util.Optional;

/**
 * Represents an audio response.
 */
public class AudioResponse {
    private final String body;

    private ObjectNode objectNode = null;


    private final AudioResponseFormat responseFormat;
    /**
     * Constructs a new AudioResponse object.
     *
     * @param body           The body of the audio response.
     * @param responseFormat  The response format.
     */
    public AudioResponse(String body, AudioResponseFormat responseFormat) {
        this.body = body;
        this.responseFormat = responseFormat;
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

    public String getText() {
        switch (this.responseFormat) {
            case JSON:
            case VERBOSE_JSON:
               return this.getObjectNode().isPresent() ? this.objectNode.getString("text") : "";
            default:
                return body;
        }
    }


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
         * responseFormat
         * @param responseFormat responseFormat
         * @return responseFormat
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

