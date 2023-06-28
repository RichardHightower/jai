package com.cloudurable.jai.model.chat;

import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatRequestSerializer;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;
import com.cloudurable.jai.model.text.completion.chat.function.Function;
import com.cloudurable.jai.model.text.completion.chat.function.Parameter;
import com.cloudurable.jai.model.text.completion.chat.function.ParameterType;
import io.nats.jparse.Json;
import io.nats.jparse.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatRequestSerializerTest {

    @Test
    void serialize() {
        // Create a sample ChatRequest object using the builder
        ChatRequest chatRequest = ChatRequest.builder()
                .setModel("gpt-3.5-turbo")
                .addMessage(Message.builder().setRole(Role.SYSTEM).setContent("You are a helpful assistant.").build())
                .addMessage(Message.builder().setRole(Role.USER).setContent("Hello!").build())
                .setTemperature(0.8f)
                .setFrequencyPenalty(0.6f)
                .setPresencePenalty(0.65f)
//                .addLogitBias(1234, 2.0f)
//                .addLogitBias(4567, -1.0f)
                .addFunction(Function.builder().setName("func1").setDescription("Function 1").addParameter(
                        Parameter.builder().setName("param1").setType(ParameterType.NUMBER).build()
                ).build())
                .addFunction(Function.builder().setName("func2").setDescription("Function 2").addParameter(
                        Parameter.builder().setName("param2").setType(ParameterType.NUMBER).build()
                ).build())
                .setMaxTokens(20)
                .setTopP(0.7f)
                .setUser("user123")
                .setStop(Arrays.asList("stopword1", "stopword2"))
                .setCompletionCount(5)
                .build();

        // Serialize the ChatRequest object
        String serializedJson = ChatRequestSerializer.serialize(chatRequest);

        ObjectNode objectNode = Json.toObjectNode(serializedJson);

        assertEquals("system", objectNode.atPath("messages[0].role").asScalar().stringValue());
        assertEquals("Hello!", objectNode.atPath("messages[1].content").asScalar().stringValue());
        assertEquals("number", objectNode.atPath("functions[0].parameters[0].type").asScalar().stringValue());
        assertEquals("func2", objectNode.atPath("functions[1].name").asScalar().stringValue());
        assertEquals("gpt-3.5-turbo", objectNode.getString("model"));

        assertEquals("0.8", objectNode.getNode("temperature").asScalar().stringValue());
        assertEquals("0.7", objectNode.getNode("top_p").asScalar().stringValue());

        assertEquals("user123", objectNode.getString("user"));
        assertEquals(20, objectNode.getInt("max_tokens"));
        assertEquals(5, objectNode.getInt("n"));
        assertEquals("stopword1", objectNode.getArrayNode("stop").getString(0));
        assertEquals("stopword2", objectNode.getArrayNode("stop").getString(1));

        assertEquals("0.6", objectNode.getNode("frequency_penalty").asScalar().stringValue());
        assertEquals("0.65", objectNode.getNode("presence_penalty").asScalar().stringValue());


    }
}
