package com.cloudurable.jai.requests.file;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the OpenAIClient.
 */
class FileGetStringContentsSyncTest {


    String responseBody;


    /**
     * Test method to verify the behavior of the chat method in the OpenAIClient.
     * This test mocks a POST request to the /chat/completions endpoint and verifies
     * the response from the OpenAIClient chat method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void getFile() throws Exception {


        HttpClientMock httpClientMock;
        OpenAIClient client;

        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        //Mock it
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponseGetNoContentType("/files/file-ccdDZrC3iZVNiQVeEA6Z66wf/content", responseBody);


        String body = client.getFileContentString("file-ccdDZrC3iZVNiQVeEA6Z66wf");

        assertNotNull(body);
        assertTrue(body.contains("ccdDZrC3iZVNiQVeEA6Z66wf"));

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
                "      \"id\": \"file-ccdDZrC3iZVNiQVeEA6Z66wf\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 175,\n" +
                "      \"created_at\": 1613677385,\n" +
                "      \"filename\": \"train.jsonl\",\n" +
                "      \"purpose\": \"search\"\n" +
                "    }");

    }

}
