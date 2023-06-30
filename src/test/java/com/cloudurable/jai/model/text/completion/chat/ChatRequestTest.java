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
                .setModel(model)
                .setMessages(messages)
                .setFunctions(functions)
                .setFunctionalCall(functionalCall)
                .setTemperature(temperature)
                .setTopP(topP)
                .setStream(stream)
                .setStop(stop)
                .setMaxTokens(maxTokens)
                .setPresencePenalty(presencePenalty)
                .setFrequencyPenalty(frequencyPenalty)
                .setLogitBias(logitBias)
                .setUser(user)
                .setCompletionCount(completionCount);

        ChatRequest chatRequest = builder.build();

        assertEquals(model, chatRequest.getModel());
        assertEquals(messages, chatRequest.getMessages());
        assertEquals(functions, chatRequest.getFunctions());
        assertEquals(functionalCall, chatRequest.getFunctionalCall());
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
    }

    @Test
    void testEqualsAndHashCode() {
        ChatRequest chatRequest1 = ChatRequest.builder()
                .setModel("model1")
                .setMessages(new ArrayList<>())
                .setFunctions(new ArrayList<>())
                .setFunctionalCall(new FunctionalCall("function1", null))
                .setTemperature(0.8f)
                .setTopP(0.9f)
                .setStream(true)
                .setStop(new ArrayList<>())
                .setMaxTokens(100)
                .setPresencePenalty(1.0f)
                .setFrequencyPenalty(0.5f)
                .setLogitBias(new TreeMap<>())
                .setUser("user1")
                .setCompletionCount(5)
                .build();

        ChatRequest chatRequest2 = ChatRequest.builder()
                .setModel("model1")
                .setMessages(new ArrayList<>())
                .setFunctions(new ArrayList<>())
                .setFunctionalCall(new FunctionalCall("function1", null))
                .setTemperature(0.8f)
                .setTopP(0.9f)
                .setStream(true)
                .setStop(new ArrayList<>())
                .setMaxTokens(100)
                .setPresencePenalty(1.0f)
                .setFrequencyPenalty(0.5f)
                .setLogitBias(new TreeMap<>())
                .setUser("user1")
                .setCompletionCount(5)
                .build();

        ChatRequest chatRequest3 = ChatRequest.builder()
                .setModel("model2")
                .setMessages(new ArrayList<>())
                .setFunctions(new ArrayList<>())
                .setFunctionalCall(new FunctionalCall("function2", null))
                .setTemperature(0.7f)
                .setTopP(0.8f)
                .setStream(false)
                .setStop(new ArrayList<>())
                .setMaxTokens(200)
                .setPresencePenalty(0.9f)
                .setFrequencyPenalty(0.4f)
                .setLogitBias(new TreeMap<>())
                .setUser("user2")
                .setCompletionCount(10)
                .build();

        // Test equality
        assertEquals(chatRequest1, chatRequest2);
        assertNotEquals(chatRequest1, chatRequest3);

        // Test hash code
        assertEquals(chatRequest1.hashCode(), chatRequest2.hashCode());
        assertNotEquals(chatRequest1.hashCode(), chatRequest3.hashCode());
    }
}
