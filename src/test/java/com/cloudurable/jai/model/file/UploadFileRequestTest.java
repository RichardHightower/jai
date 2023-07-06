package com.cloudurable.jai.model.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class UploadFileRequestTest {

    @Test
    void equals_TwoEqualUploadFileRequests_ReturnsTrue() {
        // Arrange
        String fileName = "example.txt";
        byte[] fileContents = "Example file contents".getBytes();
        String purpose = "fine-tune";

        UploadFileRequest request1 = UploadFileRequest.builder()
                .fileName(fileName)
                .file(fileContents)
                .purpose(purpose)
                .build();

        UploadFileRequest request2 = UploadFileRequest.builder()
                .fileName(fileName)
                .file(fileContents)
                .purpose(purpose)
                .build();

        // Act & Assert
        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
        Assertions.assertEquals(request1.toString(), request2.toString());
    }

    @Test
    void equals_TwoDifferentUploadFileRequests_ReturnsFalse() throws IOException {
        // Arrange
        String fileName1 = "file1.txt";
        byte[] fileContents1 = "File 1 contents".getBytes();
        String purpose1 = "fine-tune";

        String fileName2 = "file2.txt";
        byte[] fileContents2 = "File 2 contents".getBytes();
        String purpose2 = "classification";

        UploadFileRequest request1 = UploadFileRequest.builder()
                .fileName(fileName1)
                .file(fileContents1)
                .purpose(purpose1)
                .build();

        UploadFileRequest request2 = UploadFileRequest.builder()
                .fileName(fileName2)
                .file(fileContents2)
                .purpose(purpose2)
                .build();

        // Act & Assert
        Assertions.assertNotEquals(request1, request2);
    }

    @Test
    void builder_WithFile_CorrectlySetsFileContentsAndFileName() throws IOException {
        // Arrange
        File file = new File("example.txt");
        String fileContents = "Example file contents";
        Files.write(file.toPath(), fileContents.getBytes());

        // Act
        UploadFileRequest request = UploadFileRequest.builder()
                .file(file)
                .purpose("fine-tune")
                .build();

        // Assert
        Assertions.assertEquals("example.txt", request.getFileName());
        Assertions.assertArrayEquals(fileContents.getBytes(), request.getFileContents());
    }
}
