package com.cloudurable.jai.model.text.completion.chat;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.DeserializerUtils;
import com.cloudurable.jai.model.text.completion.chat.function.FunctionalCall;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.Node;
import io.nats.jparse.node.NullNode;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;
import java.util.List;

/**
 * This class provides deserialization functionality for Chat Response for Open AI API.
 * <p>
 * The ChatResponseDeserializer class provides deserialization functionality for the Chat Response received from the OpenAI API.
 * It takes a string representation of the JSON response and converts it into a ChatResponse object, which contains
 * the deserialized information.
 * <p>
 * The deserialize() method is the main entry point of the class. It takes the JSON response as input and parses
 * it using a JsonParser. The method extracts the relevant fields from the JSON and constructs a ChatResponse object
 * by utilizing other helper methods.
 * <p>
 * The deserializeUsage() method deserializes the usage information from the JSON response and constructs a Usage object,
 * which represents the token usage details of the chat conversation.
 * <p>
 * The deserializeChoice() method deserializes an individual chat choice from the JSON response. It extracts the choice
 * index, finish reason, and message information, and constructs a ChatChoice object.
 * <p>
 * The deserializeMessage() method deserializes a message from the JSON response. It extracts the message content,
 * name (if available), and role, and constructs a Message object.
 * <p>
 * The deserializeRole() method deserializes the role of a message from the JSON response. It maps the role string
 * to the corresponding Role enum value.
 * <p>
 * The deserializeFinishReason() method deserializes the finish reason of a chat choice from the JSON response.
 * It maps the finish reason string to the corresponding FinishReason enum value.
 * <p>
 * Overall, the ChatResponseDeserializer class provides a convenient way to deserialize the OpenAI API's chat response
 * JSON into a structured Java object, making it easier to work with and access the information contained in the response.
 */
public class ChatResponseDeserializer {

    private ChatResponseDeserializer() {

    }

    /**
     * Deserializes the provided string representation into a ChatResponse object.
     * <p>
     * See <a href="https://platform.openai.com/docs/api-reference/chat/create">Details of Chat Response from OpenAPI.</a>
     * </p>
     *
     * @param jsonBody The string representation to be deserialized.
     * @return The deserialized ChatResponse object.
     */
    public static ChatResponse deserialize(final String jsonBody) {

        //ystem.out.println(jsonBody);

        final JsonParser parser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = parser.parse(jsonBody).asObject();
        final String id = objectNode.getString("id");
        final String object = objectNode.getString("object");
        final int createdTime = objectNode.getInt("created");
        final Usage usage = DeserializerUtils.deserializeUsage(objectNode.getObjectNode("usage"));
        final ArrayNode choicesNode = objectNode.getArrayNode("choices");
        final List<ChatChoice> chatChoices = choicesNode.mapObjectNode(ChatResponseDeserializer::deserializeChoice);


        return ChatResponse.builder().choices(chatChoices).id(id).usage(usage)
                .object(object).created(Instant.ofEpochSecond(createdTime)).build();
    }


    private static ChatChoice deserializeChoice(final ObjectNode choiceNode) {
        ChatChoice.Builder builder = ChatChoice.builder();
        builder.index(choiceNode.getInt("index"))
                .finishReason(DeserializerUtils.deserializeFinishReason(choiceNode.getString("finish_reason")))
                .message(deserializeMessage(choiceNode.getObjectNode("message")));

        return builder.build();
    }

    private static Message deserializeMessage(ObjectNode message) {
        Message.Builder builder = Message.builder();
        if (message.get("content") != null) {
                builder.content(message.getString("content"));
        }
        if (message.get("name") != null) {
            builder.name(message.getString("name"));
        }
        builder.role(deserializeRole(message.getString("role")));

        if (message.get("function_call") != null) {
            ObjectNode functionCallNode = message.getObjectNode("function_call");
            FunctionalCall.Builder funcBuilder = FunctionalCall.builder().name(functionCallNode.getString("name"));

            if (functionCallNode.getString("arguments") != null) {
                funcBuilder.arguments(JsonParserBuilder.builder().build().parse(functionCallNode.getString("arguments")).getObjectNode());
            }
            builder.functionCall(funcBuilder.build());
        }
        return builder.build();
    }

    private static Role deserializeRole(final String role) {
        switch (role) {
            case "system":
                return Role.SYSTEM;
            case "user":
                return Role.USER;
            case "assistant":
                return Role.ASSISTANT;
            case "function":
                return Role.FUNCTION;
            default:
                return Role.OTHER;
        }
    }


}
