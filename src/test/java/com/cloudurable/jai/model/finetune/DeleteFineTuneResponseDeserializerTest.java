package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DeleteFineTuneResponseDeserializerTest {

    @Test
    public void testDeserialize() {
        String json = "{\"id\":\"file-XjGxS3KTG0uNmNOK362iJua3\",\"object\":\"file\",\"deleted\":true}";


        DeleteFineTuneResponse response = DeleteFineTuneResponseDeserializer.deserialize(json);

        assertEquals("file-XjGxS3KTG0uNmNOK362iJua3", response.getId());
        assertEquals("file", response.getObject());
        assertTrue(response.isDeleted());
    }
}
