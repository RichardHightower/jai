package com.cloudurable.jai.model.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class ImageResponseTest {
    @Test
    public void testConstructorAndGetters() {
        Instant created = Instant.ofEpochSecond(1589478378);
        List<ImageResponseData> data = new ArrayList<>();
        data.add(new ImageResponseData(URI.create("https://image1.jpg")));
        data.add(new ImageResponseData(URI.create("https://image1.jpg")));

        ImageResponse response = new ImageResponse(created, data);

        Assertions.assertEquals(created, response.getCreated());
        Assertions.assertEquals(data, response.getData());
    }

    @Test
    public void testEqualsAndHashCode() {
        Instant created = Instant.ofEpochSecond(1589478378);
        List<ImageResponseData> data = new ArrayList<>();
        data.add(new ImageResponseData(URI.create("https://image1.jpg")));
        data.add(new ImageResponseData(URI.create("https://image1.jpg")));

        ImageResponse response1 = new ImageResponse(created, data);
        ImageResponse response2 = new ImageResponse(created, data);

        Assertions.assertEquals(response1, response2);
        Assertions.assertEquals(response1.hashCode(), response2.hashCode());
    }


    @Test
    public void testBuilder() {
        Instant created = Instant.ofEpochSecond(1589478378);
        List<ImageResponseData> data = new ArrayList<>();
        data.add(new ImageResponseData(URI.create("https://image1.jpg")));
        data.add(new ImageResponseData(URI.create("https://image1.jpg")));

        ImageResponse response = ImageResponse.builder()
                .created(created)
                .data(data)
                .build();

        Assertions.assertEquals(created, response.getCreated());
        Assertions.assertEquals(data, response.getData());
    }

    @Test
    public void testBuilderAdd() {
        Instant created = Instant.ofEpochSecond(1589478378);
        List<ImageResponseData> data = new ArrayList<>();

        ImageResponse response = ImageResponse.builder()
                .created(created)
                .add(new ImageResponseData(URI.create("https://image3.jpg")))
                .add(new ImageResponseData(URI.create("https://image4.jpg")))
                .build();

        data.add(new ImageResponseData(URI.create("https://image3.jpg")));
        data.add(new ImageResponseData(URI.create("https://image4.jpg")));

        Assertions.assertEquals(created, response.getCreated());
        Assertions.assertEquals(data.get(0), response.getData().get(0));
        Assertions.assertEquals(data.get(0).toString(), response.getData().get(0).toString());
        Assertions.assertEquals(data.get(0).getUrl().orElse(null), response.getData().get(0).getUrl().orElse(URI.create("https://www.google.com")));
        Assertions.assertEquals(data.get(0).hashCode(), response.getData().get(0).hashCode());
    }

}
