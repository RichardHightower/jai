package com.cloudurable.jai.model.model;

import io.nats.jparse.Json;
import io.nats.jparse.node.ArrayNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelDataTest {

    @Test
    public void testModelDataEquality() {
        String id = "123";
        String object = "Model";
        String ownedBy = "Organization";
        ArrayNode permission = Json.toArrayNode("[]");

        ModelData modelData1 = ModelData.builder()
                .id(id)
                .object(object)
                .ownedBy(ownedBy)
                .permission(permission)
                .build();

        ModelData modelData2 = ModelData.builder()
                .id(id)
                .object(object)
                .ownedBy(ownedBy)
                .permission(permission)
                .build();

        assertEquals(modelData1, modelData2);
        assertEquals(modelData1.hashCode(), modelData2.hashCode());
        assertEquals(modelData1.toString(), modelData2.toString());
        assertEquals(modelData1.getOwnedBy(), modelData2.getOwnedBy());
        assertEquals(modelData1.getPermission(), modelData2.getPermission());
    }


}
