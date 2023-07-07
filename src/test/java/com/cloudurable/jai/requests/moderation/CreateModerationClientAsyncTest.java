package com.cloudurable.jai.requests.moderation;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.moderation.CreateModerationRequest;
import com.cloudurable.jai.model.moderation.CreateModerationRequestSerializer;
import com.cloudurable.jai.model.moderation.CreateModerationResponse;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateModerationClientAsyncTest {


    HttpClientMock httpClientMock;
    OpenAIClient client;

    String responseBody;
    String requestBody;
    CreateModerationRequest createModerationRequest;

    /**
     * Test method to verify the behavior of the completion method in the OpenAIClient.
     * This test mocks a POST request to the /completions endpoint and verifies
     * the response from the OpenAIClient completion method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void createModeration() throws Exception {
        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Mock the response
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponsePostAsync("/moderations", requestBody, responseBody);

        final ClientResponse<CreateModerationRequest, CreateModerationResponse> response = client.moderateAsync(createModerationRequest).get();

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(editResponse -> {
            assertNotNull(editResponse.getResults());
        });

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
                "  \"id\": \"modr-XXXXX\",\n" +
                "  \"model\": \"text-moderation-005\",\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"flagged\": true,\n" +
                "      \"categories\": {\n" +
                "        \"sexual\": false,\n" +
                "        \"hate\": false,\n" +
                "        \"harassment\": false,\n" +
                "        \"self-harm\": false,\n" +
                "        \"sexual/minors\": false,\n" +
                "        \"hate/threatening\": false,\n" +
                "        \"violence/graphic\": false,\n" +
                "        \"self-harm/intent\": false,\n" +
                "        \"self-harm/instructions\": false,\n" +
                "        \"harassment/threatening\": true,\n" +
                "        \"violence\": true,\n" +
                "      },\n" +
                "      \"category_scores\": {\n" +
                "        \"sexual\": 1.2282071e-06,\n" +
                "        \"hate\": 0.010696256,\n" +
                "        \"harassment\": 0.29842457,\n" +
                "        \"self-harm\": 1.5236925e-08,\n" +
                "        \"sexual/minors\": 5.7246268e-08,\n" +
                "        \"hate/threatening\": 0.0060676364,\n" +
                "        \"violence/graphic\": 4.435014e-06,\n" +
                "        \"self-harm/intent\": 8.098441e-10,\n" +
                "        \"self-harm/instructions\": 2.8498655e-11,\n" +
                "        \"harassment/threatening\": 0.63055265,\n" +
                "        \"violence\": 0.99011886,\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n");

        // Create the request body
        createModerationRequest = CreateModerationRequest.builder()
                .input("This is a prompt.")
                .build();

        requestBody = CreateModerationRequestSerializer.serialize(createModerationRequest);
        System.out.println(requestBody);
    }
}
