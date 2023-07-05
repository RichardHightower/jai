package com.cloudurable.jai.requests;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditRequestSerializer;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EditClientSyncTest {


    HttpClientMock httpClientMock;
    OpenAIClient client;

    String responseBody;
    String requestBody;
    EditRequest editRequest;

    /**
     * Test method to verify the behavior of the completion method in the OpenAIClient.
     * This test mocks a POST request to the /completions endpoint and verifies
     * the response from the OpenAIClient completion method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void edit() throws Exception {
        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Mock the response
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponsePost("/edits", requestBody, responseBody);

        final ClientResponse<EditRequest, EditResponse> response = client.edit(editRequest);

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(editResponse -> {
            assertEquals("What day of the week is it?", editResponse.getChoices().get(0).getText());
        });

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
                "  \"object\": \"edit\",\n" +
                "  \"created\": 1589478378,\n" +
                "  \"choices\": [\n" +
                "    {\n" +
                "      \"text\": \"What day of the week is it?\",\n" +
                "      \"index\": 0,\n" +
                "    }\n" +
                "  ],\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 25,\n" +
                "    \"completion_tokens\": 32,\n" +
                "    \"total_tokens\": 57\n" +
                "  }\n" +
                "}");

        // Create the request body
        editRequest = EditRequest.builder()
                .model("gpt-3.5-turbo")
                .input("This is a prompt.")
                .instruction("This is a prompt.")
                .build();

        requestBody = EditRequestSerializer.serialize(editRequest);
        System.out.println(requestBody);
    }
}
