package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListFineTuneResponseDeserializerTest {

    @Test
    public void testDeserialize() {
        String json = "{\"object\":\"list-fine-tune-response\"," +
                "\"data\":[" +
                "{\"id\":\"1\",\"object\":\"object1\",\"created_at\":1625712000}," +
                "{\"id\":\"2\",\"object\":\"object2\",\"created_at\":1625712100}]" +
                "}";

        // Mock the JsonParser and ObjectNode

        // Deserialize the JSON
        ListFineTuneResponse response = ListFineTuneResponseDeserializer.deserialize(json);

        // Verify the deserialized response
        assertEquals("list-fine-tune-response", response.getObject());

        List<FineTuneData> data = response.getData();
        assertEquals(2, data.size());

        FineTuneData data1 = data.get(0);
        assertEquals("1", data1.getId());
        assertEquals("object1", data1.getObject());
        assertEquals(Instant.ofEpochSecond(1625712000), data1.getCreatedAt());

        FineTuneData data2 = data.get(1);
        assertEquals("2", data2.getId());
        assertEquals("object2", data2.getObject());
        assertEquals(Instant.ofEpochSecond(1625712100), data2.getCreatedAt());
    }
}
