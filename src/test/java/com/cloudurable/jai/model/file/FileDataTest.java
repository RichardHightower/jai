package com.cloudurable.jai.model.file;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class FileDataTest {

    @Test
    public void testEqualsAndHashCode() {
        // Create two FileData instances with the same values

        Instant now = Instant.now();
        FileData fileData1 = FileData.builder()
                .id("1")
                .object("file.txt")
                .purpose("storage")
                .createdAt(now)
                .bytes(1024)
                .build();

        FileData fileData2 = FileData.builder()
                .id("1")
                .object("file.txt")
                .purpose("storage")
                .createdAt(now)
                .bytes(1024)
                .build();

        // Assert that the two instances are equal and have the same hash code
        assertEquals(fileData1, fileData2);
        assertEquals(fileData1.hashCode(), fileData2.hashCode());
        assertEquals(fileData1.toString(), fileData2.toString());
    }


}
