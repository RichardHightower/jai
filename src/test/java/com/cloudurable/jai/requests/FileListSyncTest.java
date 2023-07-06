package com.cloudurable.jai.requests;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.file.FileListResponse;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the OpenAIClient.
 */
class FileListSyncTest {


    String responseBody;


    /**
     * Test method to verify the behavior of the chat method in the OpenAIClient.
     * This test mocks a POST request to the /chat/completions endpoint and verifies
     * the response from the OpenAIClient chat method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void listFiles() throws Exception {


        HttpClientMock httpClientMock;
        OpenAIClient client;

        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        //Mock it
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponseGet("/files", responseBody);


        FileListResponse response = client.listFiles();


        assertEquals("file-ccdDZrC3iZVNiQVeEA6Z66wf", response.getData().get(0).getId());


        HttpClient mock = httpClientMock.getMock();


        verify(mock, times(1)).send(requestResponse.getRequest(), HttpResponse.BodyHandlers.ofString());

    }


    /**
     * Setup method to initialize the client, mock HttpClient,
     * and set up request and response data before each test.
     */
    @BeforeEach
    void before() {

        // Create the response body

        responseBody = Json.niceJson("{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"file-ccdDZrC3iZVNiQVeEA6Z66wf\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 175,\n" +
                "      \"created_at\": 1613677385,\n" +
                "      \"filename\": \"train.jsonl\",\n" +
                "      \"purpose\": \"search\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"file-XjGxS3KTG0uNmNOK362iJua3\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 140,\n" +
                "      \"created_at\": 1613779121,\n" +
                "      \"filename\": \"puppy.jsonl\",\n" +
                "      \"purpose\": \"search\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"object\": \"list\"\n" +
                "}");

    }

}
