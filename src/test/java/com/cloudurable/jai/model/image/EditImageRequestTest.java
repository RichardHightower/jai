package com.cloudurable.jai.model.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class EditImageRequestTest {

    @Test
    public void testEqualsAndHashCode() {
        byte[] imageBody = new byte[]{0x00, 0x01, 0x02};
        String imageFileName = "image.jpg";
        byte[] maskBody = new byte[]{0x03, 0x04, 0x05};
        String maskImageFileName = "mask.jpg";

        EditImageRequest request1 = EditImageRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .prompt("Prompt 1")
                .imageBody(imageBody)
                .imageFileName(imageFileName)
                .maskBody(maskBody)
                .maskImageFileName(maskImageFileName)
                .build();

        EditImageRequest request2 = EditImageRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .prompt("Prompt 1")
                .imageBody(imageBody)
                .imageFileName(imageFileName)
                .maskBody(maskBody)
                .maskImageFileName(maskImageFileName)
                .build();

        EditImageRequest request3 = EditImageRequest.builder()
                .n(5)
                .size(ImageSize.SIZE_512_BY_512)
                .responseFormat(ImageResponseFormat.B64_JSON)
                .user("user2")
                .prompt("Prompt 2")
                .imageBody(new byte[]{0x06, 0x07, 0x08})
                .imageFileName("image2.jpg")
                .maskBody(new byte[]{0x09, 0x0A, 0x0B})
                .maskImageFileName("mask2.jpg")
                .build();

        // Test equality
        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request2, request1);

        // Test hash code
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());

        Assertions.assertEquals(request1.toString(), request2.toString());

        Assertions.assertEquals(request1.getImageBody(), request2.getImageBody());

        Assertions.assertEquals(request1.getImageFileName(), request2.getImageFileName());

        Assertions.assertEquals(request1.getPrompt(), request2.getPrompt());
        Assertions.assertEquals(request1.getN(), request2.getN());

        Assertions.assertEquals(request1.getMaskBody(), request2.getMaskBody());

        Assertions.assertEquals(request1.getMaskImageFileName(), request2.getMaskImageFileName());


        // Test inequality
        Assertions.assertNotEquals(request1, request3);
        Assertions.assertNotEquals(request2, request3);
    }

    @Test
    public void testImageFile() throws IOException {
        File imageFile = new File("super_hero.png");
        byte[] imageBody = Files.readAllBytes(imageFile.toPath());
        String imageFileName = imageFile.toString();

        File maskFile = new File("super_hero.png");
        byte[] maskBody = Files.readAllBytes(maskFile.toPath());
        String maskImageFileName = maskFile.toString();

        EditImageRequest request = EditImageRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_1024_1024)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .prompt("Prompt")
                .imageFile(imageFile)
                .maskFile(maskFile)
                .build();

        Assertions.assertArrayEquals(imageBody, request.getImageBody());
        Assertions.assertEquals(imageFileName, request.getImageFileName());
        Assertions.assertArrayEquals(maskBody, request.getMaskBody());
        Assertions.assertEquals(maskImageFileName, request.getMaskImageFileName());
    }


}
