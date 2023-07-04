package com.cloudurable.jai.model.text.completion.chat;

import com.cloudurable.jai.model.text.completion.chat.function.Function;
import com.cloudurable.jai.model.text.completion.chat.function.FunctionalCall;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ChatRequestTest {

    @Test
    void testBuilder() {
        String model = "test_model";
        List<Message> messages = new ArrayList<>();
        List<Function> functions = new ArrayList<>();
        FunctionalCall functionalCall = new FunctionalCall("function", null);
        float temperature = 0.8f;
        float topP = 0.9f;
        boolean stream = true;
        List<String> stop = new ArrayList<>();
        int maxTokens = 100;
        float presencePenalty = 1.0f;
        float frequencyPenalty = 0.5f;
        Map<Integer, Float> logitBias = new TreeMap<>();
        String user = "test_user";
        int completionCount = 5;

        ChatRequest.Builder builder = ChatRequest.builder()
                .model(model)
                .messages(messages)
                .functions(functions)
                .functionalCall(functionalCall)
                .temperature(temperature)
                .topP(topP)
                .stream(stream)
                .stop(stop)
                .maxTokens(maxTokens)
                .presencePenalty(presencePenalty)
                .frequencyPenalty(frequencyPenalty)
                .logitBias(logitBias)
                .user(user)
                .completionCount(completionCount);

        ChatRequest chatRequest = builder.build();

        assertEquals(model, chatRequest.getModel());
        assertEquals(messages, chatRequest.getMessages());
        assertEquals(functions, chatRequest.getFunctions());
        assertEquals(functionalCall, chatRequest.getFunctionalCall());
        assertEquals(functionalCall, builder.getFunctionalCall());
        assertEquals(temperature, chatRequest.getTemperature());
        assertEquals(topP, chatRequest.getTopP());
        assertEquals(stream, chatRequest.isStream());
        assertEquals(stop, chatRequest.getStop());
        assertEquals(maxTokens, chatRequest.getMaxTokens());
        assertEquals(presencePenalty, chatRequest.getPresencePenalty());
        assertEquals(frequencyPenalty, chatRequest.getFrequencyPenalty());
        assertEquals(logitBias, chatRequest.getLogitBias());
        assertEquals(user, chatRequest.getUser());
        assertEquals(completionCount, builder.getCompletionCount());
        assertEquals(completionCount, builder.build().getN());
    }

    @Test
    void testEqualsAndHashCode() {
        ChatRequest chatRequest1 = ChatRequest.builder()
                .model("model1")
                .messages(new ArrayList<>())
                .functions(new ArrayList<>())
                .functionalCall(new FunctionalCall("function1", null))
                .temperature(0.8f)
                .topP(0.9f)
                .stream(true)
                .stop(new ArrayList<>())
                .maxTokens(100)
                .presencePenalty(1.0f)
                .frequencyPenalty(0.5f)
                .logitBias(new TreeMap<>())
                .user("user1")
                .completionCount(5)
                .build();

        ChatRequest chatRequest2 = ChatRequest.builder()
                .model("model1")
                .messages(new ArrayList<>())
                .functions(new ArrayList<>())
                .functionalCall(new FunctionalCall("function1", null))
                .temperature(0.8f)
                .topP(0.9f)
                .stream(true)
                .stop(new ArrayList<>())
                .maxTokens(100)
                .presencePenalty(1.0f)
                .frequencyPenalty(0.5f)
                .logitBias(new TreeMap<>())
                .user("user1")
                .completionCount(5)
                .build();

        ChatRequest chatRequest3 = ChatRequest.builder()
                .model("model2")
                .messages(new ArrayList<>())
                .functions(new ArrayList<>())
                .functionalCall(new FunctionalCall("function2", null))
                .temperature(0.7f)
                .topP(0.8f)
                .stream(false)
                .stop(new ArrayList<>())
                .maxTokens(200)
                .presencePenalty(0.9f)
                .frequencyPenalty(0.4f)
                .logitBias(new TreeMap<>())
                .user("user2")
                .completionCount(10)
                .build();

        // Test equality
        assertEquals(chatRequest1, chatRequest2);
        assertNotEquals(chatRequest1, chatRequest3);

        // Test hash code
        assertEquals(chatRequest1.hashCode(), chatRequest2.hashCode());
        assertNotEquals(chatRequest1.hashCode(), chatRequest3.hashCode());
    }
}
