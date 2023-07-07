package com.cloudurable.jai.requests.finetunes;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.file.FileListResponse;
import com.cloudurable.jai.model.finetune.ListFineTuneResponse;
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
class FineTuneListAsyncTest {


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
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponseGetAsync("/fine-tunes", responseBody);


        ListFineTuneResponse response = client.listFineTunesAsync().get();


        assertEquals("ft-AF1WoRqd3aJAHsqc9NY7iL8F", response.getData().get(0).getId());


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
                "  'object': 'list',\n" +
                "  'data': [\n" +
                "    {\n" +
                "      'id': 'ft-AF1WoRqd3aJAHsqc9NY7iL8F',\n" +
                "      'object': 'fine-tune',\n" +
                "      'model': 'curie',\n" +
                "      'created_at': 1614807352,\n" +
                "      'fine_tuned_model': 'foo',\n" +
                "      'hyperparams': { },\n" +
                "      'organization_id': 'orgid',\n" +
                "      'result_files': [],\n" +
                "      'status': 'pending',\n" +
                "      'validation_files': [],\n" +
                "      'training_files': [],\n" +
                "      'updated_at': 1614807352\n" +
                "    } \n" +
                "  ]\n" +
                "}");


    }

}
