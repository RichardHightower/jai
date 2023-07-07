package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ListFineTuneEventResponseTest {

    @Test
    public void testBuilder() {
        String object = "fine-tune-event-list";

        final var now = Instant.now();
        List<FineTuneEvent> data = new ArrayList<>();

        data.add(FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build());
        data.add(FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build());

        ListFineTuneEventResponse expectedResponse = ListFineTuneEventResponse.builder()
                .object(object)
                .data(data)
                .build();

        assertEquals(object, expectedResponse.getObject());
        assertEquals(data, expectedResponse.getData());
    }

    @Test
    public void testGetDataWhenNull() {
        ListFineTuneEventResponse response = ListFineTuneEventResponse.builder()
                .object("fine-tune-event-list")
                .build();

        List<FineTuneEvent> data = response.getData();
        assertTrue(data.isEmpty());
    }

    @Test
    public void testAddData() {

        final var now = Instant.now();
        FineTuneEvent event1 = FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build();
        FineTuneEvent event2 = FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build();

        ListFineTuneEventResponse response = ListFineTuneEventResponse.builder()
                .object("fine-tune-event-list")
                .addData(event1)
                .addData(event2)
                .build();

        List<FineTuneEvent> expectedData = new ArrayList<>();
        expectedData.add(event1);
        expectedData.add(event2);

        assertEquals(expectedData, response.getData());
    }

    @Test
    public void testEqualsAndHashCode() {

        final var now = Instant.now();
        FineTuneEvent event1 = FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build();
        FineTuneEvent event2 = FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build();

        List<FineTuneEvent> data2 = new ArrayList<>();

        data2.add(FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build());
        data2.add(FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(now).build());

        ListFineTuneEventResponse response1 = ListFineTuneEventResponse.builder().object("fine-tune-event-list")
                .addData(event1).addData(event2).build();
        ListFineTuneEventResponse response2 = ListFineTuneEventResponse.builder().object("fine-tune-event-list")
                .data(data2).build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertEquals(response1.toString(), response2.toString());
    }

}
