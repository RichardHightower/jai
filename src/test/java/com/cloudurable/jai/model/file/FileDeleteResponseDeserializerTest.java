package com.cloudurable.jai.model.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileDeleteResponseDeserializerTest {

    @Test
    void deserialize_ValidJson_ReturnsFileDeleteResponseObject() {
        // Arrange
        String json = "{\"id\":\"file-XjGxS3KTG0uNmNOK362iJua3\",\"object\":\"file\",\"deleted\":true}";

        // Act
        FileDeleteResponse result = FileDeleteResponseDeserializer.deserialize(json);

        // Assert
        Assertions.assertEquals("file-XjGxS3KTG0uNmNOK362iJua3", result.getId());
        Assertions.assertEquals("file", result.getObject());
        Assertions.assertTrue(result.isDeleted());
    }

    @Test
    void deserialize_InvalidJson_ThrowsException() {
        // Arrange
        String json = "{\"id\":\"file-XjGxS3KTG0uNmNOK362iJua3\",\"object\":\"file\",\"deleted\":\"invalid\"}";

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> FileDeleteResponseDeserializer.deserialize(json));
    }
}
