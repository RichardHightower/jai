package com.cloudurable.jai.model.chat;

import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatRequestSerializer;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;
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
                .model("gpt-3.5-turbo")
                .addMessage(Message.builder().role(Role.SYSTEM).content("You are a helpful assistant.").build())
                .addMessage(Message.builder().role(Role.USER).content("Hello!").build())
                .temperature(0.8f)
                .frequencyPenalty(0.6f)
                .presencePenalty(0.65f)
//                .addFunction(Function.builder().name("func1").setDescription("Function 1").p(
//                        Parameter.builder().name("param1").type(ParameterType.NUMBER).build()
//                ).build())
//                .addFunction(Function.builder().name("func2").setDescription("Function 2").addParameter(
//                        Parameter.builder().name("param2").type(ParameterType.NUMBER).build()
//                ).build())
                .maxTokens(20)
                .topP(0.7f)
                .user("user123")
                .stop(Arrays.asList("stopword1", "stopword2"))
                .completionCount(5)
                .build();

        // Serialize the ChatRequest object
        String serializedJson = ChatRequestSerializer.serialize(chatRequest);

        ObjectNode objectNode = Json.toObjectNode(serializedJson);

        assertEquals("system", objectNode.atPath("messages[0].role").asScalar().stringValue());
        assertEquals("Hello!", objectNode.atPath("messages[1].content").asScalar().stringValue());
        //assertEquals("number", objectNode.atPath("functions[0].parameters[0].type").asScalar().stringValue());
// TODO        assertEquals("func2", objectNode.atPath("functions[1].name").asScalar().stringValue());
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
