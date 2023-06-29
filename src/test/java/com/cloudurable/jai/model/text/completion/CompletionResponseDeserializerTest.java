package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.Usage;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletionResponseDeserializerTest {


    @Test
    void testDeserialize() {
        String jsonBody = "{\"id\":\"response123\",\"object\":\"completion\"," +
                "\"created\":1627362600,\"usage\":{\"prompt_tokens\":10," +
                "\"completion_tokens\":5, \"total_tokens\":15},\"choices\":" +
                "[{\"finish_reason\":\"stop\",\"index\":1,\"text\":" +
                "\"Choice 1\",\"logprobs\":[0,1,2]}," +
                "{\"finish_reason\":\"length\",\"index\":2,\"text\":" +
                "\"Choice 2\",\"logprobs\":[3,4,5]}]}";

        CompletionResponse response = CompletionResponseDeserializer.deserialize(jsonBody);

        assertEquals("response123", response.getId());
        assertEquals("completion", response.getObject());
        assertEquals(Instant.ofEpochSecond(1627362600), response.getCreated());
        assertEquals(new Usage(10, 5, 15), response.getUsage());
        assertEquals(2, response.getChoices().size());

        CompletionChoice choice1 = response.getChoices().get(0);
        assertEquals(FinishReason.STOP, choice1.getFinishReason());
        assertEquals(1, choice1.getIndex());
        assertEquals("Choice 1", choice1.getText());
        assertEquals(Arrays.asList(0, 1, 2), choice1.getLogprobs());

        CompletionChoice choice2 = response.getChoices().get(1);
        assertEquals(FinishReason.LENGTH, choice2.getFinishReason());
        assertEquals(2, choice2.getIndex());
        assertEquals("Choice 2", choice2.getText());
        assertEquals(Arrays.asList(3, 4, 5), choice2.getLogprobs());

    }

}
