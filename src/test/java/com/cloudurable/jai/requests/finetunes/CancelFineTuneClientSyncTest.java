package com.cloudurable.jai.requests.finetunes;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.finetune.FineTuneData;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CancelFineTuneClientSyncTest {


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
    void cancelFineTune() throws Exception {
        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Mock the response
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponsePost("/fine-tunes/foo/cancel", responseBody);

        final FineTuneData response = client.cancelFineTune("foo");


        assertEquals("ft-xhrpBbvVUzYGo8oUO1FY4nI7", response.getId());

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
                "  \"id\": \"ft-xhrpBbvVUzYGo8oUO1FY4nI7\",\n" +
                "  \"object\": \"fine-tune\",\n" +
                "  \"model\": \"curie\",\n" +
                "  \"created_at\": 1614807770,\n" +
                "  \"events\": [  ],\n" +
                "  \"fine_tuned_model\": 'foo',\n" +
                "  \"hyperparams\": {},\n" +
                "  \"organization_id\": \"org-...\",\n" +
                "  \"result_files\": [],\n" +
                "  \"status\": \"cancelled\",\n" +
                "  \"validation_files\": [],\n" +
                "  \"training_files\": [\n" +
                "    {\n" +
                "      \"id\": \"file-XGinujblHPwGLSztz8cPS8XY\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 1547276,\n" +
                "      \"created_at\": 1610062281,\n" +
                "      \"filename\": \"my-data-train.jsonl\",\n" +
                "      \"purpose\": \"fine-tune-train\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"updated_at\": 1614807789,\n" +
                "}\n");


    }
}
