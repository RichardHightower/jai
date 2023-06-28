package com.cloudurable.jai.model.text.completion.chat;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.Usage;
import io.nats.jparse.node.ArrayNode;
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

        final JsonParser parser = JsonParserBuilder.builder().setStrict(true).build();
        final ObjectNode objectNode = parser.parse(jsonBody).asObject();
        final String id = objectNode.getString("id");
        final String object = objectNode.getString("object");
        final int createdTime = objectNode.getInt("created");
        final Usage usage = deserializeUsage(objectNode.getObjectNode("usage"));
        final ArrayNode choicesNode = objectNode.getArrayNode("choices");
        final List<ChatChoice> chatChoices = choicesNode.mapObjectNode(ChatResponseDeserializer::deserializeChoice);


        return ChatResponse.builder().setChoices(chatChoices).setId(id).setUsage(usage)
                .setObject(object).setCreated(Instant.ofEpochSecond(createdTime)).build();
    }

    private static Usage deserializeUsage(final ObjectNode usageNode) {
        Usage.Builder builder = Usage.builder();
        builder.setCompletionTokens(usageNode.getInt("completion_tokens"));
        builder.setPromptTokens(usageNode.getInt("prompt_tokens"));
        builder.setTotalTokens(usageNode.getInt("total_tokens"));
        return builder.build();
    }

    private static ChatChoice deserializeChoice(final ObjectNode choiceNode) {
        ChatChoice.Builder builder = ChatChoice.builder();
        builder.setIndex(choiceNode.getInt("index"))
                .setFinishReason(deserializeFinishReason(choiceNode.getString("finish_reason")))
                .setMessage(deserializeMessage(choiceNode.getObjectNode("message")));

        return builder.build();
    }

    private static Message deserializeMessage(ObjectNode message) {
        Message.Builder builder = Message.builder();
        builder.setContent(message.getString("content"));
        if (message.get("name") != null) {
            builder.setName(message.getString("name"));
        }
        builder.setRole(deserializeRole(message.getString("role")));
        //TODO builder.setFunctionCall(deserializeFunctionCall(message.getObjectNode("function_call")));
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

    private static FinishReason deserializeFinishReason(final String finishReason) {
        switch (finishReason) {
            case "stop":
                return FinishReason.STOP;
            case "content_filter":
                return FinishReason.CONTENT_FILTER;
            case "length":
                return FinishReason.LENGTH;
            case "null":
                return FinishReason.NULL;
            default:
                return FinishReason.OTHER;
        }
    }
}
