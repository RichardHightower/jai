package com.cloudurable.jai.model.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileDeleteResponseTest {

    @Test
    void equals_TwoEqualFileDeleteResponses_ReturnsTrue() {
        // Arrange
        FileDeleteResponse response1 = FileDeleteResponse.builder()
                .id("file-XjGxS3KTG0uNmNOK362iJua3")
                .object("file")
                .deleted(true)
                .build();

        FileDeleteResponse response2 = FileDeleteResponse.builder()
                .id("file-XjGxS3KTG0uNmNOK362iJua3")
                .object("file")
                .deleted(true)
                .build();

        // Act & Assert
        Assertions.assertEquals(response1, response2);
        Assertions.assertEquals(response1.hashCode(), response2.hashCode());
        Assertions.assertEquals(response1.toString(), response2.toString());
    }

    @Test
    void equals_TwoDifferentFileDeleteResponses_ReturnsFalse() {
        // Arrange
        FileDeleteResponse response1 = FileDeleteResponse.builder()
                .id("file-XjGxS3KTG0uNmNOK362iJua3")
                .object("file")
                .deleted(true)
                .build();

        FileDeleteResponse response2 = FileDeleteResponse.builder()
                .id("file-1234567890")
                .object("file")
                .deleted(false)
                .build();

        // Act & Assert
        Assertions.assertNotEquals(response1, response2);
    }
}
