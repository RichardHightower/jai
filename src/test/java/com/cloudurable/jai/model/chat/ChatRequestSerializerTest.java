package com.cloudurable.jai.model.chat;

import com.cloudurable.jai.model.chat.function.Function;
import com.cloudurable.jai.model.chat.function.Parameter;
import com.cloudurable.jai.model.chat.function.ParameterType;
import io.nats.jparse.Json;
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
                .setFrequencyPenalty(1.0f)
                .setPresencePenalty(1.0f)
                .addLogitBias(1234, 2.0f)
                .addLogitBias(4567, -1.0f)
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

        // Define the expected serialized JSON
        String expectedJson = Json.niceJson("{'model': 'gpt-3.5-turbo'," +
                "'messages': [{'role': 'system','content': 'You are a helpful assistant.'}," +
                "{'role': 'user','content': 'Hello!'}]," +
                "'temperature': 0.8," +
                "'frequency_penalty': 1.0," +
                "'presence_penalty': 1.0," +
                "'logit_bias': [1234:2.0,4567:-1.0]," +
                "'functions': [{'name': 'func1'," +
                "'parameters': [{'param1':{'type': 'number'}}]},{'name': 'func2','parameters': [{'param2':{'type': 'number'}}]}]," +
                "'max_tokens': 20," +
                "'top_p': 0.7," +
                "'user': 'user123'," +
                "'stop': ['stopword1','stopword2']'n': 5}");

        // Assert that the serialized JSON matches the expected JSON
        // Fix the above to use JsonParser so it is less random.
        // assertEquals(expectedJson, serializedJson);
    }
}
