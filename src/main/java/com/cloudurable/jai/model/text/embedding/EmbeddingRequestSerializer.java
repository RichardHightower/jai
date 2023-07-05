package com.cloudurable.jai.model.text.embedding;


import com.cloudurable.jai.util.JsonSerializer;

/**
 * Serializes an EmbeddingRequest object to JSON.
 */
public class EmbeddingRequestSerializer {

    private EmbeddingRequestSerializer() {
    }

    /**
     * Serializes an EmbeddingRequest object to JSON.
     *
     * @param request The EmbeddingRequest object to serialize.
     * @return The JSON representation of the EmbeddingRequest.
     */
    public static String serialize(EmbeddingRequest request) {

        final JsonSerializer jsonBodyBuilder = new JsonSerializer();
        // start JSON request body for an open ai API chat request
        jsonBodyBuilder.startObject();

        jsonBodyBuilder.addAttribute("input", request.getInput());
        jsonBodyBuilder.addAttribute("model", request.getModel());

        // end JSON request body for an open ai API chat request
        jsonBodyBuilder.endObject();

        return jsonBodyBuilder.toString();
    }
}
