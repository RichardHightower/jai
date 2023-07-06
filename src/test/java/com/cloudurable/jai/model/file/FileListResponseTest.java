package com.cloudurable.jai.model.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class FileListResponseTest {

    @Test
    void equals_TwoEqualFileListResponses_ReturnsTrue() {
        // Arrange
        FileData fileData1 = FileData.builder()
                .object("file1")
                .id("123")
                .createdAt(Instant.ofEpochSecond(1625577600))
                .bytes(1024)
                .purpose("test1")
                .build();

        FileData fileData2 = FileData.builder()
                .object("file2")
                .id("456")
                .createdAt(Instant.ofEpochSecond(1625577600))
                .bytes(2048)
                .purpose("test2")
                .build();

        List<FileData> data1 = new ArrayList<>();
        data1.add(fileData1);
        data1.add(fileData2);

        List<FileData> data2 = new ArrayList<>();
        data2.add(fileData1);
        data2.add(fileData2);

        FileListResponse response1 = FileListResponse.builder()
                .object("files")
                .addData(fileData1)
                .addData(fileData2)
                .build();

        FileListResponse response2 = FileListResponse.builder()
                .object("files")
                .data(data2)
                .build();

        // Act & Assert
        Assertions.assertEquals(response1, response2);
        Assertions.assertEquals(response1.hashCode(), response2.hashCode());
        Assertions.assertEquals(response1.toString(), response2.toString());
    }

    @Test
    void equals_TwoDifferentFileListResponses_ReturnsFalse() {
        // Arrange
        FileData fileData1 = FileData.builder()
                .object("file1")
                .id("123")
                .createdAt(Instant.ofEpochSecond(1625577600))
                .bytes(1024)
                .purpose("test1")
                .build();

        FileData fileData2 = FileData.builder()
                .object("file2")
                .id("456")
                .createdAt(Instant.ofEpochSecond(1625577600))
                .bytes(2048)
                .purpose("test2")
                .build();

        List<FileData> data1 = new ArrayList<>();
        data1.add(fileData1);
        data1.add(fileData2);

        FileData fileData3 = FileData.builder()
                .object("file3")
                .id("789")
                .createdAt(Instant.ofEpochSecond(1625577600))
                .bytes(4096)
                .purpose("test3")
                .build();

        FileData fileData4 = FileData.builder()
                .object("file4")
                .id("012")
                .createdAt(Instant.ofEpochSecond(1625577600))
                .bytes(8192)
                .purpose("test4")
                .build();

        List<FileData> data2 = new ArrayList<>();
        data2.add(fileData3);
        data2.add(fileData4);

        FileListResponse response1 = FileListResponse.builder()
                .object("files")
                .data(data1)
                .build();

        FileListResponse response2 = FileListResponse.builder()
                .object("files")
                .data(data2)
                .build();

        // Act & Assert
        Assertions.assertNotEquals(response1, response2);
    }
}
