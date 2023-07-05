package com.cloudurable.jai.model.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateImageRequestTest {

    @Test
    public void testEqualsAndHashCode() {
        CreateImageRequest request1 = CreateImageRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .prompt("Prompt 1")
                .build();

        CreateImageRequest request2 = CreateImageRequest.builder()
                .n(10)
                .size(ImageSize.SIZE_256_BY_256)
                .responseFormat(ImageResponseFormat.URL)
                .user("user1")
                .prompt("Prompt 1")
                .build();

        CreateImageRequest request3 = CreateImageRequest.builder()
                .n(5)
                .size(ImageSize.SIZE_512_BY_512)
                .responseFormat(ImageResponseFormat.B64_JSON)
                .user("user2")
                .prompt("Prompt 2")
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


}
