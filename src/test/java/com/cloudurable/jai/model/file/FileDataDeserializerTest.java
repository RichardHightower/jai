package com.cloudurable.jai.model.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class FileDataDeserializerTest {

    @Test
    void deserialize_ValidJson_ReturnsFileDataObject() {
        // Arrange
        String json = "{\"object\":\"file\",\"id\":\"123\",\"created_at\":1625577600,\"bytes\":1024,\"purpose\":\"test\", \"filename\":\"foo.bar\"}";

        // Act
        FileData result = FileDataDeserializer.deserialize(json);

        // Assert
        Assertions.assertEquals("file", result.getObject());
        Assertions.assertEquals("123", result.getId());
        Assertions.assertEquals(Instant.ofEpochSecond(1625577600), result.getCreatedAt());
        Assertions.assertEquals(1024, result.getBytes());
        Assertions.assertEquals("test", result.getPurpose());
    }

    @Test
    void deserialize_InvalidJson_ThrowsException() {
        // Arrange
        String json = "{\"object\":\"file\",\"id\":\"123\",\"created_at\":\"invalid\",\"bytes\":1024,\"purpose\":\"test\"}";

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> FileDataDeserializer.deserialize(json));
    }
}
