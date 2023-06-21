package com.cloudurable.jai.model.chat;


import com.cloudurable.jai.model.chat.function.Function;
import com.cloudurable.jai.model.chat.function.Parameter;

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

        final StringBuilder jsonBodyBuilder = new StringBuilder(80);
        // start JSON request body for an open ai API chat request
        jsonBodyBuilder.append('{');

        // Note property values are always in snake case for the JSON output.
        final String model = chatRequest.getModel();
        jsonBodyBuilder.append("\"model\": \"").append(model).append("\",");

        final List<Message> messages = chatRequest.getMessages();
        // start JSON list body for messages
        jsonBodyBuilder.append("\"messages\": [");
        for (Message message : messages) {
            jsonBodyBuilder.append("{");
            jsonBodyBuilder.append("\"role\": \"").append(message.getRole().toString().toLowerCase()).append("\",");
            jsonBodyBuilder.append("\"content\": \"").append(message.getContent()).append("\"");
            jsonBodyBuilder.append("},");
        }
        // remove trailing comma
        jsonBodyBuilder.deleteCharAt(jsonBodyBuilder.length() - 1);
        jsonBodyBuilder.append("]");

        final float temperature = chatRequest.getTemperature();
        if (temperature != 0) {
            jsonBodyBuilder.append("\"temperature\": ").append(temperature).append(",");
        }

        final float frequencyPenalty = chatRequest.getFrequencyPenalty();
        if (frequencyPenalty != 0) {
            jsonBodyBuilder.append("\"frequency_penalty\": ").append(frequencyPenalty).append(",");
        }

        float presencePenalty = chatRequest.getPresencePenalty();
        if (presencePenalty != 0) {
            jsonBodyBuilder.append("\"presence_penalty\": ").append(presencePenalty).append(",");
        }

        final Map<Integer, Float> logitBias = chatRequest.getLogitBias();
        if (logitBias != null && !logitBias.isEmpty()) {
            jsonBodyBuilder.append("\"logit_bias\": [");
            for (Integer key : logitBias.keySet()) {
                jsonBodyBuilder.append(key).append(':').append(logitBias.get(key)).append(',');
            }
            // remove trailing comma
            jsonBodyBuilder.deleteCharAt(jsonBodyBuilder.length() - 1);
            jsonBodyBuilder.append("],");
        }

        final List<Function> functions = chatRequest.getFunctions();
        if (functions != null && !functions.isEmpty()) {
            jsonBodyBuilder.append("\"functions\": [");
            for (Function function : functions) {
                jsonBodyBuilder.append("{");
                jsonBodyBuilder.append("\"name\": \"").append(function.getName()).append("\",");

                List<Parameter> parameters = function.getParameters();
                jsonBodyBuilder.append("\"parameters\": [");
                for (Parameter parameter : parameters) {
                    jsonBodyBuilder.append("{");
                    jsonBodyBuilder.append('"').append(parameter.getName()).append('"').append(":{");
                    jsonBodyBuilder.append("\"type\": \"").append(parameter.getType().toString().toLowerCase()).append('"').append('}');
                    jsonBodyBuilder.append("},");
                }
                jsonBodyBuilder.deleteCharAt(jsonBodyBuilder.length() - 1); // remove trailing comma
                jsonBodyBuilder.append("]");


                jsonBodyBuilder.append("},");
            }
            // remove trailing comma
            jsonBodyBuilder.deleteCharAt(jsonBodyBuilder.length() - 1);
            jsonBodyBuilder.append("],");
        }

        final int maxTokens = chatRequest.getMaxTokens();
        if (maxTokens != 0) {
            jsonBodyBuilder.append("\"max_tokens\": ").append(maxTokens).append(",");
        }

        final float topP = chatRequest.getTopP();
        if (topP != 0) {
            jsonBodyBuilder.append("\"top_p\": ").append(topP).append(",");
        }

        // add user to JSON
        final String user = chatRequest.getUser();
        if (user != null && !user.isBlank()) {
            jsonBodyBuilder.append("\"user\": \"").append(user).append("\",");
        }

        // add stop to JSON
        final List<String> stop = chatRequest.getStop();

        if (stop != null && !stop.isEmpty()) {
            jsonBodyBuilder.append("\"stop\": [");
            for (String str : stop) {
                jsonBodyBuilder.append("\"").append(str).append("\"" + ",");
            }
            // remove trailing comma
            jsonBodyBuilder.deleteCharAt(jsonBodyBuilder.length() - 1);
            jsonBodyBuilder.append("]");
        }

        // add completionCount to JSON
        final int completionCount = chatRequest.getCompletionCount();
        if (completionCount != 0) {
            jsonBodyBuilder.append("\"n\": ").append(completionCount);
        }

        // end JSON request body for an open ai API chat request
        jsonBodyBuilder.append('}');

        return jsonBodyBuilder.toString();
    }
}
