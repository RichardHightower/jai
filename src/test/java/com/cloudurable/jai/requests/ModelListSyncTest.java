package com.cloudurable.jai.requests;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.model.ModelListResponse;
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
class ModelListSyncTest {


    String responseBody;


    /**
     * Test method to verify the behavior of the chat method in the OpenAIClient.
     * This test mocks a POST request to the /chat/completions endpoint and verifies
     * the response from the OpenAIClient chat method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void listModel() throws Exception {


        HttpClientMock httpClientMock;
        OpenAIClient client;

        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        //Mock it
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponseGet("/models", responseBody);


        ModelListResponse response = client.listModels();


        assertEquals("model-id-0", response.getData().get(0).getId());


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
                "      \"id\": \"model-id-0\",\n" +
                "      \"object\": \"model\",\n" +
                "      \"owned_by\": \"organization-owner\",\n" +
                "      \"permission\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"model-id-1\",\n" +
                "      \"object\": \"model\",\n" +
                "      \"owned_by\": \"organization-owner\",\n" +
                "      \"permission\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"model-id-2\",\n" +
                "      \"object\": \"model\",\n" +
                "      \"owned_by\": \"openai\",\n" +
                "      \"permission\": []\n" +
                "    },\n" +
                "  ],\n" +
                "  \"object\": \"list\"\n" +
                "}");

    }

}
