package com.cloudurable.jai.requests;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.file.FileDeleteResponse;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FileDeleteClientAsyncTest {


    HttpClientMock httpClientMock;
    OpenAIClient client;

    String responseBody;


    /**
     * Test method to verify the behavior of the completion method in the OpenAIClient.
     * This test mocks a POST request to the /completions endpoint and verifies
     * the response from the OpenAIClient completion method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void deleteFile() throws Exception {
        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Mock the response
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponseDeleteAsync("/files/foo", responseBody);

        final FileDeleteResponse response = client.deleteFileAsync("foo").get();


        assertEquals("file-XjGxS3KTG0uNmNOK362iJua3", response.getId());

        HttpClient mock = httpClientMock.getMock();
        verify(mock, times(1)).sendAsync(requestResponse.getRequest(), HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Setup method to initialize the client, mock HttpClient,
     * and set up request and response data before each test.
     */
    @BeforeEach
    void before() {
        // Create the response body
        responseBody = Json.niceJson("{\n" +
                "  \"id\": \"file-XjGxS3KTG0uNmNOK362iJua3\",\n" +
                "  \"object\": \"file\",\n" +
                "  \"deleted\": true\n" +
                "}");


    }
}
