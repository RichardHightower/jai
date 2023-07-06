package com.cloudurable.jai.model.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

class FileListResponseDeserializerTest {

    @Test
    void deserialize_ValidJson_ReturnsFileListResponseObject() {
        // Arrange
        String json = "{\"object\":\"files\",\"data\":[{\"id\":\"file1\",\"created_at\":1625577600,\"bytes\":1024,\"object\":\"file\",\"purpose\":\"test1\"},{\"id\":\"file2\",\"created_at\":1625577600,\"bytes\":2048,\"object\":\"file\",\"purpose\":\"test2\"}]}";

        // Act
        FileListResponse result = FileListResponseDeserializer.deserialize(json);

        // Assert
        Assertions.assertEquals("files", result.getObject());

        List<FileData> data = result.getData();
        Assertions.assertEquals(2, data.size());

        FileData fileData1 = data.get(0);
        Assertions.assertEquals("file1", fileData1.getId());
        Assertions.assertEquals(Instant.ofEpochSecond(1625577600), fileData1.getCreatedAt());
        Assertions.assertEquals(1024, fileData1.getBytes());
        Assertions.assertEquals("file", fileData1.getObject());
        Assertions.assertEquals("test1", fileData1.getPurpose());

        FileData fileData2 = data.get(1);
        Assertions.assertEquals("file2", fileData2.getId());
        Assertions.assertEquals(Instant.ofEpochSecond(1625577600), fileData2.getCreatedAt());
        Assertions.assertEquals(2048, fileData2.getBytes());
        Assertions.assertEquals("file", fileData2.getObject());
        Assertions.assertEquals("test2", fileData2.getPurpose());
    }

    @Test
    void deserialize_InvalidJson_ThrowsException() {
        // Arrange
        String json = "{\"object\":\"files\",\"data\":[{\"id\":\"file1\",\"created_at\":\"invalid\",\"bytes\":1024,\"object\":\"file\",\"purpose\":\"test1\"},{\"id\":\"file2\",\"created_at\":1625577600,\"bytes\":2048,\"object\":\"file\",\"purpose\":\"test2\"}]}";

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> FileListResponseDeserializer.deserialize(json));
    }
}

