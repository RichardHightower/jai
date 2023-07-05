package com.cloudurable.jai.model.image;

import com.cloudurable.jai.util.MultipartEntityBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ImageRequestSerializerTest {

    @Test
    public void testBuildCreateForm() {
        CreateImageRequest request = CreateImageRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .prompt("Prompt")
                .build();

        MultipartEntityBuilder form = ImageRequestSerializer.buildCreateForm(request);

        Assertions.assertNotNull(form.build());
    }

    @Test
    public void testBuildEditForm() {
        EditImageRequest request = EditImageRequest.builder()
                .n(5)
                .size(ImageSize.SIZE_512_BY_512)
                .responseFormat(ImageResponseFormat.URL)
                .user("user2")
                .prompt("Prompt")
                .imageBody(new byte[]{0x00, 0x01, 0x02})
                .imageFileName("image.jpg")
                .maskBody(new byte[]{0x03, 0x04, 0x05})
                .maskImageFileName("mask.jpg")
                .build();

        MultipartEntityBuilder form = ImageRequestSerializer.buildEditForm(request);


        Assertions.assertNotNull(form.build());
    }

    @Test
    public void testBuildVariationForm() {
        CreateImageVariationRequest request = CreateImageVariationRequest.builder()
                .n(1)
                .size(ImageSize.SIZE_512_BY_512)
                .responseFormat(ImageResponseFormat.URL)
                .user("user3")
                .imageBody(new byte[]{0x00, 0x01, 0x02})
                .imageFileName("image.jpg")
                .build();

        MultipartEntityBuilder form = ImageRequestSerializer.buildVariationForm(request);

        Assertions.assertNotNull(form.build());
    }


    @Test
    public void testBuildJson() {
        CreateImageRequest request = CreateImageRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .prompt("Prompt")
                .build();

        String json = ImageRequestSerializer.buildJson(request);

        // Check JSON content
        String expectedJson = "{" +
                "\"prompt\":\"Prompt\"," +
                "\"response_format\":\"url\"," +
                "\"size\":\"256x256\"," +
                "\"n\":10," +
                "\"user\":\"user1\"" +
                "}";

        Assertions.assertEquals(expectedJson, json);
    }

    @Test
    public void testGetEncodingContentType() {
        MultipartEntityBuilder form = MultipartEntityBuilder.create();
        String contentType = ImageRequestSerializer.getEncodingContentType(form);

        // Check content type
        Assertions.assertEquals("multipart/form-data;boundary=\"" + form.getBoundary() + "\"", contentType);
    }

}
