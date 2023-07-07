package com.cloudurable.jai.requests.finetunes;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.finetune.CreateFineTuneRequest;
import com.cloudurable.jai.model.finetune.CreateFineTuneRequestSerializer;
import com.cloudurable.jai.model.finetune.FineTuneData;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FineTuneCreateSyncTest {
    HttpClientMock httpClientMock;
    OpenAIClient client;
    String responseBody;
    String requestBody;
    CreateFineTuneRequest request;

    @Test
    void createFineTuneAsync() throws Exception {

        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponsePost("/fine-tunes", requestBody, responseBody);



        ClientResponse<CreateFineTuneRequest, FineTuneData> response =  client.createFineTune(request);

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(uploadResponse -> {
            assertEquals("file", uploadResponse.getObject());
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
                "  \"id\": \"file-XjGxS3KTG0uNmNOK362iJua3\",\n" +
                "  \"object\": \"file\",\n" +
                "  \"bytes\": 140,\n" +
                "  \"created_at\": 1613779121,\n" +
                "  \"filename\": \"mydata.jsonl\",\n" +
                "  \"purpose\": \"fine-tune\"\n" +
                "}");

        // Create the request body
        request = CreateFineTuneRequest.builder()
                .trainingFile("trainingFile")
                .build();

        requestBody  = CreateFineTuneRequestSerializer.serialize(request);

    }
}
