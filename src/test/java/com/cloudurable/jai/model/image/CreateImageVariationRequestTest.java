package com.cloudurable.jai.model.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


class CreateImageVariationRequestTest {

    @Test
    public void testEqualsAndHashCode() {
        byte[] imageBody = new byte[]{0x00, 0x01, 0x02};
        String imageFileName = "image.jpg";

        CreateImageVariationRequest request1 = CreateImageVariationRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .imageBody(imageBody)
                .imageFileName(imageFileName)
                .build();

        CreateImageVariationRequest request2 = CreateImageVariationRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .imageBody(imageBody)
                .imageFileName(imageFileName)
                .build();

        CreateImageVariationRequest request3 = CreateImageVariationRequest.builder()
                .n(5)
                .size(ImageSize.SIZE_512_BY_512)
                .responseFormat(ImageResponseFormat.B64_JSON)
                .user("user2")
                .imageBody(new byte[]{0x03, 0x04, 0x05})
                .imageFileName("image2.jpg")
                .build();

        // Test equality
        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request2, request1);

        // Test hash code
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
        Assertions.assertEquals(request1.toString(), request2.toString());

        // Test inequality
        Assertions.assertNotEquals(request1, request3);
        Assertions.assertNotEquals(request2, request3);
    }


    @Test
    public void testImageFile() throws IOException {
        File imageFile = new File("super_hero.png");
        byte[] imageBody = Files.readAllBytes(imageFile.toPath());
        String imageFileName = imageFile.toString();

        CreateImageVariationRequest request = CreateImageVariationRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_1024_1024)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .imageFile(imageFile)
                .build();

        Assertions.assertArrayEquals(imageBody, request.getImageBody());
        Assertions.assertEquals(imageFileName, request.getImageFileName());
    }


}
