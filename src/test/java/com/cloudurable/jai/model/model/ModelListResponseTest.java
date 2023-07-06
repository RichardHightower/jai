package com.cloudurable.jai.model.model;

import io.nats.jparse.Json;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelListResponseTest {

    @Test
    public void testModelListResponseEquality() {
        String object = "ModelList";
        List<ModelData> data = new ArrayList<>();

        ModelData modelData1 = ModelData.builder()
                .id("1")
                .object("Model")
                .ownedBy("Organization")
                .permission(Json.toArrayNode("[]"))
                .build();
        data.add(modelData1);

        ModelData modelData2 = ModelData.builder()
                .id("2")
                .object("Model")
                .ownedBy("Organization")
                .permission(Json.toArrayNode("[]"))
                .build();
        data.add(modelData2);

        ModelListResponse response1 = ModelListResponse.builder()
                .object(object)
                .addData(modelData1)
                .addData(modelData2)
                .build();

        ModelListResponse response2 = ModelListResponse.builder()
                .object(object)
                .data(data)
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.toString(), response2.toString());
        assertEquals(response1.hashCode(), response2.hashCode());
        assertEquals(response1.getData(), response2.getData());
        assertEquals(response1.getObject(), response2.getObject());
    }

}

