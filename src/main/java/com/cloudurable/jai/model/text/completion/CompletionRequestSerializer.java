package com.cloudurable.jai.model.text.completion;


import com.cloudurable.jai.model.text.SerializerUtils;
import com.cloudurable.jai.util.JsonSerializer;

/**
 * This class provides serialization functionality for ChatRequest objects.
 * The ChatRequestSerializer class is responsible for serializing ChatRequest objects into a JSON string representation. It provides the functionality to convert the ChatRequest object into a format compatible with the OpenAI API.
 * <p>
 * The class contains a single public static method, serialize, which takes a ChatRequest object as input and returns the serialized JSON string representation of the object. The method follows the structure and format specified by the OpenAI API documentation.
 * <p>
 * The serialization process involves constructing a JSON body by appending key-value pairs based on the properties of the ChatRequest object. The StringBuilder is used to efficiently build the JSON string.
 * <p>
 * The method iterates over the list of messages, functions, and logit biases, appending their respective key-value pairs to the JSON body. It handles different data types and uses appropriate formatting for each property.
 * <p>
 * Overall, the ChatRequestSerializer class enables the conversion of ChatRequest objects into JSON strings that can be sent to the OpenAI API for chat completion requests.
 */
public class CompletionRequestSerializer {

    private CompletionRequestSerializer() {
    }

    /**
     * Serialize request into JSON.
     *
     * @param request Completion Request
     * @return JSON
     */
    public static String serialize(CompletionRequest request) {

        final JsonSerializer jsonBodyBuilder = new JsonSerializer();
        // start JSON request body for an open ai API chat request
        jsonBodyBuilder.startObject();

        SerializerUtils.outputModel(request, jsonBodyBuilder);

        jsonBodyBuilder.addAttribute("prompt", request.getPrompt());
        jsonBodyBuilder.addAttribute("suffix", request.getSuffix());
        jsonBodyBuilder.addAttribute("echo", request.isEcho());
        jsonBodyBuilder.addAttribute("logprobs", request.getLogprobs());
        jsonBodyBuilder.addAttribute("stream", request.isStream());
        jsonBodyBuilder.addAttribute("best_of", request.getBestOf());

        SerializerUtils.outputTextParams(request, jsonBodyBuilder);
        SerializerUtils.outputCompletionParams(request, jsonBodyBuilder);

        // end JSON request body for an open ai API chat request
        jsonBodyBuilder.endObject();

        return jsonBodyBuilder.toString();
    }


}
