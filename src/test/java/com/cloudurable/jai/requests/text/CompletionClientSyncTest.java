package com.cloudurable.jai.requests.text;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionRequestSerializer;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CompletionClientSyncTest {


    HttpClientMock httpClientMock;
    OpenAIClient client;

    String basicCompletionResponseBody;
    String basicCompletionRequestBody;
    CompletionRequest basicCompletionRequest;

    /**
     * Test method to verify the behavior of the completion method in the OpenAIClient.
     * This test mocks a POST request to the /completions endpoint and verifies
     * the response from the OpenAIClient completion method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void completion() throws Exception {
        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Mock the response
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponsePost("/completions", basicCompletionRequestBody, basicCompletionResponseBody);

        final ClientResponse<CompletionRequest, CompletionResponse> response = client.completion(basicCompletionRequest);

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(completionResponse -> {
            assertEquals("This is the completion response.", completionResponse.getChoices().get(0).getText());
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
        basicCompletionResponseBody = Json.niceJson("{\n" +
                "  'id': 'completion-123',\n" +
                "  'object': 'completion',\n" +
                "  'created': 1687413620,\n" +
                "  'model': 'gpt-3.5-turbo',\n" +
                "  'choices': [\n" +
                "    {\n" +
                "      'text': 'This is the completion response.',\n" +
                "      'finish_reason': 'stop',\n" +
                "      'index': 0\n" +
                "    }\n" +
                "  ],\n" +
                "  'usage': {\n" +
                "    'prompt_tokens': 10,\n" +
                "    'completion_tokens': 5,\n" +
                "    'total_tokens': 15\n" +
                "  }\n" +
                "}");

        // Create the request body
        basicCompletionRequest = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .maxTokens(10)
                .prompt("This is a prompt.")
                .build();

        basicCompletionRequestBody = CompletionRequestSerializer.serialize(basicCompletionRequest);

    }
}
