package com.cloudurable.jai.requests.text;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequestSerializer;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EmbeddingClientTest {


    HttpClientMock httpClientMock;
    OpenAIClient client;

    String responseBody;
    String requestBody;
    EmbeddingRequest embeddingRequest;

    /**
     * Test method to verify the behavior of the completion method in the OpenAIClient.
     * This test mocks a POST request to the /completions endpoint and verifies
     * the response from the OpenAIClient completion method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void embedding() throws Exception {
        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Mock the response
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponsePost("/embeddings", requestBody, responseBody);

        final ClientResponse<EmbeddingRequest, EmbeddingResponse> response = client.embedding(embeddingRequest);

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(textResponse -> {
            assertEquals(1.0, textResponse.getData().get(0).getEmbedding()[0]);
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
                "  \"object\": \"list\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"object\": \"embedding\",\n" +
                "      \"embedding\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3\n" +
                "      ],\n" +
                "      \"index\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"model\": \"text-embedding-ada-002\",\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 8,\n" +
                "    \"total_tokens\": 8\n" +
                "  }\n" +
                "}");

        // Create the request body
        embeddingRequest = EmbeddingRequest.builder()
                .model("gpt-3.5-turbo")
                .input("This is a prompt.")
                .build();

        requestBody = EmbeddingRequestSerializer.serialize(embeddingRequest);
        System.out.println(requestBody);
    }
}
