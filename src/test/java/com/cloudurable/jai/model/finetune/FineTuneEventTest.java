package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FineTuneEventTest {

    @Test
    public void testBuilder() {
        String object = "fine-tune-event";
        String level = "info";
        String message = "Job started.";
        Instant createdAt = Instant.ofEpochSecond(1614807356);

        FineTuneEvent expectedFineTuneEvent = FineTuneEvent.builder()
                .object(object)
                .level(level)
                .message(message)
                .createdAt(createdAt)
                .build();

        assertEquals(object, expectedFineTuneEvent.getObject());
        assertEquals(level, expectedFineTuneEvent.getLevel());
        assertEquals(message, expectedFineTuneEvent.getMessage());
        assertEquals(createdAt, expectedFineTuneEvent.getCreatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        FineTuneEvent event1 = FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(Instant.ofEpochSecond(1614807356)).build();
        FineTuneEvent event2 = FineTuneEvent.builder().object("fine-tune-event").level("info").message("Job started.")
                .createdAt(Instant.ofEpochSecond(1614807356)).build();

        assertEquals(event1, event2);
        assertEquals(event1.hashCode(), event2.hashCode());
        assertEquals(event1.toString(), event2.toString());
    }

}
