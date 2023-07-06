package com.cloudurable.jai.model.file;


import com.cloudurable.jai.util.MultipartEntityBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UploadFileRequestSerializerTest {

    @Test
    void buildForm_ValidUploadFileRequest_ReturnsMultipartEntityBuilder() {
        // Arrange
        String fileName = "example.txt";
        byte[] fileContents = "Example file contents".getBytes();
        String purpose = "fine-tune";

        UploadFileRequest request = UploadFileRequest.builder()
                .fileName(fileName)
                .file(fileContents)
                .purpose(purpose)
                .build();

        // Act
        MultipartEntityBuilder form = UploadFileRequestSerializer.buildForm(request);

        // Assert
        Assertions.assertNotNull(form.build());

    }

    @Test
    void getEncodingContentType_ValidMultipartEntityBuilder_ReturnsContentTypeWithBoundary() {
        // Arrange
        MultipartEntityBuilder form = MultipartEntityBuilder.create();

        // Act
        String contentType = UploadFileRequestSerializer.getEncodingContentType(form);

        // Assert
        Assertions.assertEquals("multipart/form-data;boundary=\"" + form.getBoundary() + "\"", contentType);
    }
}

