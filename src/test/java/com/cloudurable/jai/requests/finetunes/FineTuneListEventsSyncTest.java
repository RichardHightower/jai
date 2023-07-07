package com.cloudurable.jai.requests.finetunes;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.finetune.ListFineTuneEventResponse;
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
class FineTuneListEventsSyncTest {


    String responseBody;


    /**
     * Test method to verify the behavior of the chat method in the OpenAIClient.
     * This test mocks a POST request to the /chat/completions endpoint and verifies
     * the response from the OpenAIClient chat method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void listFineTunesAsync() throws Exception {


        OpenAIClient client;

        HttpClientMock httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        //Mock it
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponseGet("/fine-tunes/foo/events", responseBody);


        ListFineTuneEventResponse response = client.listFineTuneEvents("foo");


        assertEquals("Job started.", response.getData().get(1).getMessage());


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
                "  \"object\": \"list\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"created_at\": 1614807352,\n" +
                "      \"level\": \"info\",\n" +
                "      \"message\": \"Job enqueued. Waiting for jobs ahead to complete. Queue number: 0.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"created_at\": 1614807356,\n" +
                "      \"level\": \"info\",\n" +
                "      \"message\": \"Job started.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"created_at\": 1614807861,\n" +
                "      \"level\": \"info\",\n" +
                "      \"message\": \"Uploaded snapshot: curie:ft-acmeco-2021-03-03-21-44-20.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"created_at\": 1614807864,\n" +
                "      \"level\": \"info\",\n" +
                "      \"message\": \"Uploaded result files: file-QQm6ZpqdNwAaVC3aSz5sWwLT.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"created_at\": 1614807864,\n" +
                "      \"level\": \"info\",\n" +
                "      \"message\": \"Job succeeded.\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n");


    }

}
