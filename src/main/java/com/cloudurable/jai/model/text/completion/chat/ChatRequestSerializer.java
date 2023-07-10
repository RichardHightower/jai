package com.cloudurable.jai.model.text.completion.chat;


import com.cloudurable.jai.model.text.SerializerUtils;
import com.cloudurable.jai.model.text.completion.chat.function.*;
import com.cloudurable.jai.util.JsonSerializer;

import java.util.List;

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

        SerializerUtils.outputModel(chatRequest, jsonBodyBuilder);

        final List<Message> messages = chatRequest.getMessages();

        // start JSON list body for messages
        jsonBodyBuilder.startNestedArrayAttribute("messages");
        for (Message message : messages) {
            jsonBodyBuilder.startNestedObjectElement();
            jsonBodyBuilder.addAttribute("role", message.getRole().toString().toLowerCase());
            jsonBodyBuilder.addAttribute("content", message.getContent());

            if (message.getName()!=null) {
                jsonBodyBuilder.addAttribute("name", message.getName());
            }
            jsonBodyBuilder.endObject();
        }
        jsonBodyBuilder.endArray();


        SerializerUtils.outputTextParams(chatRequest, jsonBodyBuilder);

        if (chatRequest.getFunctionalCall()  == ChatRequest.AUTO) {
            jsonBodyBuilder.addAttribute("function_call", "auto");
        } else if (chatRequest.getFunctionalCall()  == ChatRequest.NONE) {
            jsonBodyBuilder.addAttribute("function_call", "none");
        } else if (chatRequest.getFunctionalCall()!=null) {
            final FunctionalCall functionalCall = chatRequest.getFunctionalCall();
            jsonBodyBuilder.startNestedObjectAttribute("function_call");
            jsonBodyBuilder.addAttribute("name", functionalCall.getName());
            if (functionalCall.getArguments()!=null) {
                jsonBodyBuilder.addAttribute("arguments", functionalCall.getArguments().toString());
            }
            jsonBodyBuilder.endObject();
        }


        final List<FunctionDef> functions = chatRequest.getFunctions();
        if (functions != null && !functions.isEmpty()) {
            jsonBodyBuilder.startNestedArrayAttribute("functions");
            for (FunctionDef function : functions) {
                jsonBodyBuilder.startNestedObjectElement();
                jsonBodyBuilder.addAttribute("name", function.getName());
                ObjectParameter parameters = function.getParameters();
                writeObjectParameter(jsonBodyBuilder, parameters);
                jsonBodyBuilder.endObject();
                jsonBodyBuilder.endObject();
            }
            jsonBodyBuilder.endArray();
        }
        SerializerUtils.outputCompletionParams(chatRequest, jsonBodyBuilder);
        // end JSON request body for an open ai API chat request
        jsonBodyBuilder.endObject();

        String json = jsonBodyBuilder.toString();

        System.out.println(json);
        return json;
    }

    public static void writeObjectParameter(JsonSerializer jsonBodyBuilder, ObjectParameter op) {
        jsonBodyBuilder.startNestedObjectAttribute("parameters");
        jsonBodyBuilder.addAttribute("type", op.getType().toString().toLowerCase());

        jsonBodyBuilder.startNestedObjectAttribute("properties");

        for (Parameter parameter : op.getProperties()) {
            jsonBodyBuilder.startNestedObjectAttribute(parameter.getName());
            jsonBodyBuilder.addAttribute("type", parameter.getType().toString().toLowerCase());
            if (parameter instanceof EnumParameter) {
                jsonBodyBuilder.startNestedArrayAttribute("enum");
                for (String enumValue : ((EnumParameter) parameter).getEnumValues()) {
                    jsonBodyBuilder.addElement(enumValue);
                }
                jsonBodyBuilder.endArray();
            }
            jsonBodyBuilder.endObject();
        }

        jsonBodyBuilder.endObject();

        jsonBodyBuilder.startNestedArrayAttribute("required");
        for (String req : op.getRequired()) {
            jsonBodyBuilder.addElement(req);
        }
        jsonBodyBuilder.endArray();

    }


}
