package com.cloudurable.jai.model.text.completion;


import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.Usage;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletionResponseTest {

    @Test
    void testGetters() {
        String id = "response123";
        String object = "completion";
        Instant created = Instant.now();
        Usage usage = new Usage(10, 5, 15);
        List<CompletionChoice> choices = Arrays.asList(
                new CompletionChoice(1, FinishReason.STOP, "Choice 1", Arrays.asList(0, 1, 2)),
                new CompletionChoice(2, FinishReason.LENGTH, "Choice 2", Arrays.asList(3, 4, 5))
        );

        CompletionResponse response = new CompletionResponse(id, object, created, usage, choices);

        assertEquals(id, response.getId());
        assertEquals(object, response.getObject());
        assertEquals(created, response.getCreated());
        assertEquals(usage, response.getUsage());
        assertEquals(choices, response.getChoices());
    }

    @Test
    void testBuilder() {
        String id = "response456";
        String object = "completion";
        Instant created = Instant.now();
        Usage usage = new Usage(15, 8, 23);
        List<CompletionChoice> choices = Arrays.asList(
                new CompletionChoice(3, FinishReason.STOP, "Choice 3", Arrays.asList(6, 7, 8)),
                new CompletionChoice(4, FinishReason.CONTENT_FILTER, "Choice 4", Arrays.asList(9, 10, 11))
        );

        CompletionResponse response = CompletionResponse.builder()
                .id(id)
                .object(object)
                .created(created)
                .usage(usage)
                .choices(choices)
                .build();

        assertEquals(id, response.getId());
        assertEquals(object, response.getObject());
        assertEquals(created, response.getCreated());
        assertEquals(usage, response.getUsage());
        assertEquals(choices, response.getChoices());
    }
}
