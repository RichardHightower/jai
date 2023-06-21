package com.cloudurable.jai.model.chat;


import com.cloudurable.jai.model.chat.function.Function;
import com.cloudurable.jai.model.chat.function.Parameter;
import com.cloudurable.jai.util.JsonSerializer;

import java.util.List;
import java.util.Map;

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
public class ChatRequestSerializer {

    private ChatRequestSerializer() {
    }

    /**
     * Serializes the provided ChatRequest object into a string representation.
     * <p>
     * See <a href="https://platform.openai.com/docs/api-reference/chat/create">Details of Chat Request from OpenAPI.</a>
     * </p>
     * <p>
     * Forms JSON request body like this:
     * <code>
     * {
     * "model": "gpt-3.5-turbo",
     * "messages": [{"role": "system", "content": "You are a helpful assistant."},
     * {"role": "user", "content": "Hello!"}],
     * "logit_bits" : {...},
     * "frequency_penalty" : 1.0,
     * "max_tokens" : 20,
     * "presence_penalty" : 1.0,
     * }
     * </code>
     *
     * @param chatRequest The ChatRequest object to be serialized.
     * @return The serialized JSON string representation of the ChatRequest object.
     */
    public static String serialize(ChatRequest chatRequest) {

        final JsonSerializer jsonBodyBuilder = new JsonSerializer();
        // start JSON request body for an open ai API chat request
        jsonBodyBuilder.startObject();

        // Note property values are always in snake case for the JSON output.
        jsonBodyBuilder.addAttribute("model", chatRequest.getModel());

        final List<Message> messages = chatRequest.getMessages();

        // start JSON list body for messages
        jsonBodyBuilder.startNestedListAttribute("messages");
        for (Message message : messages) {
            jsonBodyBuilder.startNestedObjectElement();
            jsonBodyBuilder.addAttribute("role", message.getRole().toString().toLowerCase());
            jsonBodyBuilder.addAttribute("content", message.getContent());
            jsonBodyBuilder.endObject();
        }
        jsonBodyBuilder.endList();

        final float temperature = chatRequest.getTemperature();
        if (temperature != 0) {
            jsonBodyBuilder.addAttribute("temperature", temperature);
        }

        final float frequencyPenalty = chatRequest.getFrequencyPenalty();
        if (frequencyPenalty != 0) {
            jsonBodyBuilder.addAttribute("frequency_penalty", frequencyPenalty);
        }

        float presencePenalty = chatRequest.getPresencePenalty();
        if (presencePenalty != 0) {
            jsonBodyBuilder.addAttribute("presence_penalty", presencePenalty);
        }

        final Map<Integer, Float> logitBias = chatRequest.getLogitBias();
        if (logitBias != null && !logitBias.isEmpty()) {
            jsonBodyBuilder.startNestedListAttribute("logit_bias");
            for (Integer key : logitBias.keySet()) {
                jsonBodyBuilder.startNestedObjectElement();
                jsonBodyBuilder.addAttribute(key, logitBias.get(key));
                jsonBodyBuilder.endObject();
            }
            jsonBodyBuilder.endList();
        }

        final List<Function> functions = chatRequest.getFunctions();
        if (functions != null && !functions.isEmpty()) {
            jsonBodyBuilder.startNestedListAttribute("functions");
            for (Function function : functions) {
                jsonBodyBuilder.startNestedObjectElement();
                jsonBodyBuilder.addAttribute("name", function.getName());
                jsonBodyBuilder.startNestedListAttribute("parameters");
                List<Parameter> parameters = function.getParameters();
                for (Parameter parameter : parameters) {
                    jsonBodyBuilder.startNestedObjectElement();
                    jsonBodyBuilder.addAttribute("type", parameter.getType().toString().toLowerCase());
                    jsonBodyBuilder.endObject();
                }
                jsonBodyBuilder.endList();
                jsonBodyBuilder.endObject();
            }
            jsonBodyBuilder.endList();
        }

        final int maxTokens = chatRequest.getMaxTokens();
        if (maxTokens != 0) {
            jsonBodyBuilder.addAttribute("max_tokens", maxTokens);
        }

        final float topP = chatRequest.getTopP();
        if (topP != 0) {
            jsonBodyBuilder.addAttribute("top_p", topP);
        }

        // add user to JSON
        final String user = chatRequest.getUser();
        if (user != null && !user.isBlank()) {
            jsonBodyBuilder.addAttribute("user", user);
        }

        // add stop to JSON
        final List<String> stop = chatRequest.getStop();

        if (stop != null && !stop.isEmpty()) {
            jsonBodyBuilder.startNestedListAttribute("stop");
            for (String str : stop) {
                jsonBodyBuilder.addElement(str);
            }
            // remove trailing comma
            jsonBodyBuilder.endList();
        }

        // add completionCount to JSON
        final int completionCount = chatRequest.getCompletionCount();
        if (completionCount != 0) {
            jsonBodyBuilder.addAttribute("n", completionCount);
        }

        // end JSON request body for an open ai API chat request
        jsonBodyBuilder.endObject();

        return jsonBodyBuilder.toString();
    }
}
