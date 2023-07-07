package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListFineTuneResponseTest {

    @Test
    public void testBuilder() {
        String object = "list-fine-tune-response";
        List<FineTuneData> data = new ArrayList<>();
        data.add(FineTuneData.builder().id("1").object("object1").build());
        data.add(FineTuneData.builder().id("2").object("object2").build());

        ListFineTuneResponse response = ListFineTuneResponse.builder()
                .object(object)
                .data(data)
                .build();

        assertEquals(object, response.getObject());
        assertEquals(data, response.getData());
    }

    @Test
    public void testGetDataWithNullData() {
        String object = "list-fine-tune-response";
        ListFineTuneResponse response = ListFineTuneResponse.builder()
                .object(object)
                .data(null)
                .build();

        assertEquals(new ArrayList<>(), response.getData());
    }

    @Test
    public void testAddData() {
        String object = "list-fine-tune-response";
        FineTuneData data1 = FineTuneData.builder().id("1").object("object1").build();
        FineTuneData data2 = FineTuneData.builder().id("2").object("object2").build();

        ListFineTuneResponse response = ListFineTuneResponse.builder()
                .object(object)
                .addData(data1)
                .addData(data2)
                .build();

        List<FineTuneData> expectedData = new ArrayList<>();
        expectedData.add(data1);
        expectedData.add(data2);

        assertEquals(expectedData, response.getData());
    }


    @Test
    public void testEquals() {
        String object = "list-fine-tune-response";
        List<FineTuneData> data = new ArrayList<>();
        data.add(FineTuneData.builder().id("1").object("object1").build());
        data.add(FineTuneData.builder().id("2").object("object2").build());

        ListFineTuneResponse response = ListFineTuneResponse.builder()
                .object(object)
                .data(data)
                .build();

        ListFineTuneResponse response2 = ListFineTuneResponse.builder()
                .object(object)
                .addData(FineTuneData.builder().id("1").object("object1").build())
                .addData(FineTuneData.builder().id("2").object("object2").build())
                .build();

        assertEquals(response, response2);
        assertEquals(response.hashCode(), response2.hashCode());
        assertEquals(response.toString(), response2.toString());
    }
}
