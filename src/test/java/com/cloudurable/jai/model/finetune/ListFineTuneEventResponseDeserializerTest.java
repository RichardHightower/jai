package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListFineTuneEventResponseDeserializerTest {

    @Test
    public void testDeserialize() {
        String json = "{\n" +
                "  \"object\": \"fine-tune-event-list\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"level\": \"info\",\n" +
                "      \"message\": \"Job started.\",\n" +
                "      \"created_at\": 1614807356\n" +
                "    },\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"level\": \"warning\",\n" +
                "      \"message\": \"Job in progress.\",\n" +
                "      \"created_at\": 1614807392\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        List<FineTuneEvent> expectedData = new ArrayList<>();
        expectedData.add(FineTuneEvent.builder()
                .object("fine-tune-event")
                .level("info")
                .message("Job started.")
                .createdAt(Instant.ofEpochSecond(1614807356))
                .build());
        expectedData.add(FineTuneEvent.builder()
                .object("fine-tune-event")
                .level("warning")
                .message("Job in progress.")
                .createdAt(Instant.ofEpochSecond(1614807392))
                .build());

        ListFineTuneEventResponse expectedResponse = ListFineTuneEventResponse.builder()
                .object("fine-tune-event-list")
                .data(expectedData)
                .build();

        ListFineTuneEventResponse actualResponse = ListFineTuneEventResponseDeserializer.deserialize(json);

        assertEquals(expectedResponse, actualResponse);
    }
}
