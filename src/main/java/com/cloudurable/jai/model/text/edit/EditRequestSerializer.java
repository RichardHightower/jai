package com.cloudurable.jai.model.text.edit;


import com.cloudurable.jai.model.text.SerializerUtils;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.util.JsonSerializer;

/**
 * This class provides serialization functionality for EditRequest objects.
 * The EditRequestSerializer class is responsible for serializing EditReuest objects into a JSON string representation.
 * It provides the functionality to convert the ChatRequest object into a format compatible with the OpenAI API.
 * <p>
 * The class contains a single public static method, serialize, which takes a EditRequest object as input and returns
 * the serialized JSON string representation of the object. The method follows the structure and format specified by the OpenAI API documentation.
 * <p>
 * The serialization process involves constructing a JSON body by appending key-value pairs based on the properties of the EditRequest object.
 * The JsonSerializer is used to efficiently build the JSON string.
 * <p>
 * Overall, the EditRequestSerializer class enables the conversion of EdieRequest objects into JSON strings that can
 * be sent to the OpenAI API for chat completion requests.
 */
public class EditRequestSerializer {

    private EditRequestSerializer() {
    }

    public static String serialize(EditRequest request) {

        final JsonSerializer jsonBodyBuilder = new JsonSerializer();
        // start JSON request body for an open ai API chat request
        jsonBodyBuilder.startObject();

        SerializerUtils.outputModel(request, jsonBodyBuilder);

        jsonBodyBuilder.addAttribute("input", request.getInput());
        jsonBodyBuilder.addAttribute("instruction", request.getInstruction());

        SerializerUtils.outputTextParams(request, jsonBodyBuilder);

        // end JSON request body for an open ai API chat request
        jsonBodyBuilder.endObject();

        return jsonBodyBuilder.toString();
    }


}
