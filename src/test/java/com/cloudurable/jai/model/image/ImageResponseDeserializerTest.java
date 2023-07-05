package com.cloudurable.jai.model.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Instant;
import java.util.List;

class ImageResponseDeserializerTest {

    @Test
    public void testDeserialize() {
        String json = "{" +
                "\"created\": 1589478378," +
                "\"data\": [" +
                "{\"url\": \"https://image1.jpg\"}," +
                "{\"url\": \"https://image2.jpg\"}" +
                "]" +
                "}";

        ImageResponse expectedResponse = ImageResponse.builder()
                .created(Instant.ofEpochSecond(1589478378))
                .data(List.of(
                        new ImageResponseData(URI.create("https://image1.jpg")),
                        new ImageResponseData(URI.create("https://image2.jpg"))
                ))
                .build();

        ImageResponse deserializedResponse = ImageResponseDeserializer.deserialize(json);

        Assertions.assertEquals(expectedResponse, deserializedResponse);
    }


}
