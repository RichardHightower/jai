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

        if (request.getInput().size() == 1) {
            jsonBodyBuilder.addAttribute("input", request.getInput().get(0));
        } else if (request.getInput().size() > 1){
           jsonBodyBuilder.startNestedArrayAttribute("input");
           for (String in : request.getInput()) {
               jsonBodyBuilder.addElement(in);
           }
           jsonBodyBuilder.endArray();
        }
        jsonBodyBuilder.addAttribute("model", request.getModel());

        // end JSON request body for an open ai API chat request
        jsonBodyBuilder.endObject();

        return jsonBodyBuilder.toString();
    }
}
