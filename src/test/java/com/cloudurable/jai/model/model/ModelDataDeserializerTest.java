package com.cloudurable.jai.model.model;

import io.nats.jparse.Json;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelDataDeserializerTest {

    @Test
    public void testDeserialize() {
        String json = "{\"id\":\"123\",\"object\":\"Model\",\"owned_by\":\"Organization\",\"permission\":[]}";

        ModelData expectedModelData = ModelData.builder()
                .id("123")
                .object("Model")
                .ownedBy("Organization")
                .permission(Json.toArrayNode("[]"))
                .build();

        ModelData deserializedModelData = ModelDataDeserializer.deserialize(json);

        assertEquals(expectedModelData, deserializedModelData);
    }
}
