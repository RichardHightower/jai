package com.cloudurable.jai.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MultipartEntityBuilderTest {

    @Test
    public void testBuildMultipartEntity() throws IOException {
        // Create a new MultipartEntityBuilder
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        // Add text body part
        String textName = "text";
        String textValue = "Hello, world!";
        builder.addTextBody(textName, textValue);

        // Add binary body part
        String binaryName = "file";
        byte[] binaryData = "This is the binary data.".getBytes(StandardCharsets.UTF_8);
        String binaryContentType = "application/octet-stream";
        String binaryFilename = "data.txt";
        builder.addBinaryBody(binaryName, binaryData, binaryContentType, binaryFilename);

        // Build the multipart entity
        byte[] entityBytes = builder.build();

        // Validate the entity bytes
        Assertions.assertNotNull(entityBytes);
        Assertions.assertTrue(entityBytes.length > 0);

        Assertions.assertEquals(builder.getBoundary(), builder.getBoundary());
        // ... additional assertions based on the expected entity structure and content
    }

}
