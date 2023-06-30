package com.cloudurable.jai.model.text.completion.chat;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.Usage;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class ChatResponseTest {

    @Test
    void testBuilder() {
        String id = "test_id";
        String object = "test_object";
        Instant created = Instant.now();
        List<ChatChoice> choices = new ArrayList<>();
        Usage usage = new Usage(10, 1000, 1010);

        ChatResponse.Builder builder = ChatResponse.builder()
                .setId(id)
                .setObject(object)
                .setCreated(created)
                .setChoices(choices)
                .setUsage(usage);

        ChatResponse chatResponse = builder.build();

        assertEquals(id, chatResponse.getId());
        assertEquals(object, chatResponse.getObject());
        assertEquals(created, chatResponse.getCreated());
        assertEquals(choices, chatResponse.getChoices());
        assertEquals(usage, chatResponse.getUsage());
    }

    @Test
    void testEqualsAndHashCode() {
        final Instant now = Instant.now();
        ChatResponse chatResponse1 = ChatResponse.builder()
                .setId("id1")
                .setObject("object1")
                .setCreated(now)
                .setChoices(new ArrayList<>())
                .setUsage(new Usage(10, 1000, 1010))
                .build();

        ChatResponse chatResponse2 = ChatResponse.builder()
                .setId("id1")
                .setObject("object1")
                .setCreated(now)
                .setChoices(new ArrayList<>())
                .setUsage(new Usage(10, 1000, 1010))
                .build();

        ChatResponse chatResponse3 = ChatResponse.builder()
                .setId("id2")
                .setObject("object2")
                .setCreated(now)
                .addChoice(ChatChoice.builder().setIndex(99).setMessage(Message.builder().build())
                        .setFinishReason(FinishReason.STOP).build())
                .setUsage(new Usage(20, 2000, 2020))
                .build();

        // Test equality
        assertEquals(chatResponse1, chatResponse2);
        assertNotEquals(chatResponse1, chatResponse3);

        // Test hash code
        assertEquals(chatResponse1.hashCode(), chatResponse2.hashCode());
        assertEquals(chatResponse1.toString(), chatResponse2.toString());
        assertNotEquals(chatResponse1.hashCode(), chatResponse3.hashCode());
    }
}
