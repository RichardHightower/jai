package com.cloudurable.jai.requests.image;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.image.CreateImageRequest;
import com.cloudurable.jai.model.image.ImageRequestSerializer;
import com.cloudurable.jai.model.image.ImageResponse;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateImageClientAsyncTest {


    HttpClientMock httpClientMock;
    OpenAIClient client;

    String responseBody;
    String requestBody;
    CreateImageRequest createImageRequest;

    /**
     * Test method to verify the behavior of the completion method in the OpenAIClient.
     * This test mocks a POST request to the /completions endpoint and verifies
     * the response from the OpenAIClient completion method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void createImageAsync() throws Exception {
        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Mock the response
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponsePostAsync("/images/generations", requestBody, responseBody);

        final ClientResponse<CreateImageRequest, ImageResponse> response = client.createImageAsync(createImageRequest).get();

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(editResponse -> {
            assertNotNull(editResponse.getData().get(0).getUrl().orElse(null));
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
                "  \"created\": 1589478378,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"url\": \"https://foo.com/foo.png\"" +
                "    },\n" +
                "    {\n" +
                "      \"url\": \"https://foo.com/foo2.png\"" +
                "    }\n" +
                "  ]\n" +
                "}");

        // Create the request body
        createImageRequest = CreateImageRequest.builder()
                .prompt("This is a prompt.")
                .build();

        requestBody = ImageRequestSerializer.buildJson(createImageRequest);
    }
}
