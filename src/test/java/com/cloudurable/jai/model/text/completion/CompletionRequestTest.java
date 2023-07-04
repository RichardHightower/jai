package com.cloudurable.jai.model.text.completion;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CompletionRequestTest {

    @Test
    void testCompletionRequestBuilder() {
        String model = "model";
        String prompt = "Hello";
        String suffix = "World";
        int bestOf = 3;
        float temperature = 0.8f;
        float topP = 0.9f;
        int completionCount = 1;
        boolean stream = false;
        List<String> stop = Collections.singletonList("!");
        int maxTokens = 100;
        float presencePenalty = 0.5f;
        float frequencyPenalty = 0.5f;
        Map<Integer, Float> logitBias = new HashMap<>();
        String user = "john_doe";
        Integer logprobs = 1;
        boolean echo = true;

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model(model)
                .prompt(prompt)
                .suffix(suffix)
                .bestOf(bestOf)
                .temperature(temperature)
                .topP(topP)
                .completionCount(completionCount)
                .stream(stream)
                .stop(stop)
                .maxTokens(maxTokens)
                .presencePenalty(presencePenalty)
                .frequencyPenalty(frequencyPenalty)
                .logitBias(logitBias)
                .user(user)
                .logprobs(logprobs)
                .echo(echo)
                .build();

        assertNotNull(completionRequest);
        assertEquals(model, completionRequest.getModel());
        assertEquals(prompt, completionRequest.getPrompt());
        assertEquals(suffix, completionRequest.getSuffix());
        assertEquals(bestOf, completionRequest.getBestOf());
        assertEquals(temperature, completionRequest.getTemperature());
        assertEquals(topP, completionRequest.getTopP());
        assertEquals(completionCount, completionRequest.getCompletionCount());
        assertEquals(stream, completionRequest.isStream());
        assertEquals(stop, completionRequest.getStop());
        assertEquals(maxTokens, completionRequest.getMaxTokens());
        assertEquals(presencePenalty, completionRequest.getPresencePenalty());
        assertEquals(frequencyPenalty, completionRequest.getFrequencyPenalty());
        assertEquals(logitBias, completionRequest.getLogitBias());
        assertEquals(user, completionRequest.getUser());
        assertEquals(logprobs, completionRequest.getLogprobs());
        assertEquals(echo, completionRequest.isEcho());
    }

    @Test
    void testCompletionRequestEqualsAndHashCode() {
        CompletionRequest request1 = new CompletionRequest("model", "Hello", "World", 3,
                0.8f, 0.9f, 1, false, Collections.singletonList("!"), 100,
                0.5f, 0.5f, new HashMap<>(), "john_doe", 1, true);

        CompletionRequest request2 = new CompletionRequest("model", "Hello", "World", 3,
                0.8f, 0.9f, 1, false, Collections.singletonList("!"), 100,
                0.5f, 0.5f, new HashMap<>(), "john_doe", 1, true);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
