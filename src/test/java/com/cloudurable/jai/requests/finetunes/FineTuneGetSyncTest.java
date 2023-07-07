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

/**
 * Tests for the OpenAIClient.
 */
class FineTuneGetSyncTest {


    String responseBody;


    @Test
    void getFineTune() throws Exception {


        HttpClientMock httpClientMock;
        OpenAIClient client;

        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        //Mock it
        final HttpClientMock.RequestResponse requestResponse = httpClientMock.setResponseGet("/fine-tunes/ft-AF1WoRqd3aJAHsqc9NY7iL8F", responseBody);


        FineTuneData response = client.getFineTuneData("ft-AF1WoRqd3aJAHsqc9NY7iL8F");


        assertEquals("ft-AF1WoRqd3aJAHsqc9NY7iL8F", response.getId());


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
                "  \"id\": \"ft-AF1WoRqd3aJAHsqc9NY7iL8F\",\n" +
                "  \"object\": \"fine-tune\",\n" +
                "  \"model\": \"curie\",\n" +
                "  \"created_at\": 1614807352,\n" +
                "  \"events\": [\n" +
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
                "  ],\n" +
                "  \"fine_tuned_model\": \"curie:ft-acmeco-2021-03-03-21-44-20\",\n" +
                "  \"hyperparams\": {\n" +
                "    \"batch_size\": 4,\n" +
                "    \"learning_rate_multiplier\": 0.1,\n" +
                "    \"n_epochs\": 4,\n" +
                "    \"prompt_loss_weight\": 0.1,\n" +
                "  },\n" +
                "  \"organization_id\": \"org-...\",\n" +
                "  \"result_files\": [\n" +
                "    {\n" +
                "      \"id\": \"file-QQm6ZpqdNwAaVC3aSz5sWwLT\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 81509,\n" +
                "      \"created_at\": 1614807863,\n" +
                "      \"filename\": \"compiled_results.csv\",\n" +
                "      \"purpose\": \"fine-tune-results\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"succeeded\",\n" +
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
                "  \"updated_at\": 1614807865,\n" +
                "}\n");

    }

}
