package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class DeleteFineTuneResponseTest {

    @Test
    public void testBuilder() {
        String id = "file-XjGxS3KTG0uNmNOK362iJua3";
        String object = "file";
        boolean deleted = true;

        DeleteFineTuneResponse response = DeleteFineTuneResponse.builder()
                .id(id)
                .object(object)
                .deleted(deleted)
                .build();

        assertEquals(id, response.getId());
        assertEquals(object, response.getObject());
        assertEquals(deleted, response.isDeleted());
    }

    @Test
    public void testEqualsAndHashCode() {
        String id1 = "file-123";
        String id2 = "file-456";
        String object = "file";
        boolean deleted = true;

        DeleteFineTuneResponse response1 = DeleteFineTuneResponse.builder()
                .id(id1)
                .object(object)
                .deleted(deleted)
                .build();

        DeleteFineTuneResponse response2 = DeleteFineTuneResponse.builder()
                .id(id1)
                .object(object)
                .deleted(deleted)
                .build();

        DeleteFineTuneResponse response3 = DeleteFineTuneResponse.builder()
                .id(id2)
                .object(object)
                .deleted(deleted)
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertEquals(response1.toString(), response2.toString());

        assertEquals(response1.hashCode(), response1.hashCode());
        assertEquals(response1.toString(), response1.toString());

        assertNotEquals(response1, response3);
    }
}
