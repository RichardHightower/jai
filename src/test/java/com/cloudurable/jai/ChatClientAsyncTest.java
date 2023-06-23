package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.chat.*;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the OpenAIClient.
 */
class ChatClientAsyncTest {


    String basicChatResponseBody;
    String basicChatRequestBody;
    ChatRequest basicChatRequest;

    /**
     * Setup method to initialize the client, mock HttpClient,
     * and set up request and response data before each test.
     */
    @BeforeEach
    void before() {

        // Create the response body

        basicChatResponseBody = Json.niceJson("{\n" +
                "  'id': 'chatcmpl-7U7eebdP0zrUDP36wBLvrjCYo7Bkw',\n" +
                "  'object': 'chat.completion',\n" +
                "  'created': 1687413620,\n" +
                "  'model': 'gpt-3.5-turbo-0301',\n" +
                "  'choices': [\n" +
                "    {\n" +
                "      'index': 0,\n" +
                "      'message': {\n" +
                "        'role': 'assistant',\n" +
                "        'content': 'AI stands for artificial intelligence. It refers to the development of computer systems that can perform tasks that typically require human intelligence, such as visual perception, speech recognition, decision-making, and language translation. AI technologies include machine learning, deep learning, natural language processing, and robotics. With AI, machines can learn to recognize patterns in data and make decisions based on that analysis. AI is rapidly advancing and has the potential to significantly impact industries such as healthcare, finance, transportation, and manufacturing.'\n" +
                "      },\n" +
                "      'finish_reason': 'stop'\n" +
                "    }\n" +
                "  ],\n" +
                "  'usage': {\n" +
                "    'prompt_tokens': 12,\n" +
                "    'completion_tokens': 97,\n" +
                "    'total_tokens': 109\n" +
                "  }\n" +
                "}");


        // Create the request body.
       basicChatRequest = ChatRequest.builder()
               .setModel("gpt-3.5-turbo")
                .addMessage(Message.builder()
                        .setContent("What is AI?")
                        .setRole(Role.USER).build()
                )
                .build();

        basicChatRequestBody = ChatRequestSerializer.serialize(basicChatRequest);
    }

    /**
     * Test method to verify the behavior of the chatAsync method in the OpenAIClient.
     * This test mocks an asynchronous POST request to the /chat/completions endpoint and verifies
     * the response from the OpenAIClient chatAsync method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void chatAsync() throws Exception {
        HttpClientMock httpClientMock;
        OpenAIClient client;

        httpClientMock = new HttpClientMock();
        client = OpenAIClient.builder().setApiKey("pk-123456789")
                .setHttpClient(httpClientMock).build();

        final HttpClientMock.RequestResponse requestResponse = httpClientMock
                .setResponsePostAsync("/chat/completions",
                        basicChatRequestBody, basicChatResponseBody);

        final ClientResponse<ChatRequest, ChatResponse> response = client.chatAsync(basicChatRequest).get();

        assertFalse(response.getStatusMessage().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(chatResponse -> {
            assertEquals(1, chatResponse.getChoices().size());
            assertEquals("AI stands for artificial intelligence. It refers to the development of computer " +
                            "systems that can perform tasks that typically require human intelligence, such as visual " +
                            "perception, speech recognition, decision-making, and language translation. AI technologies " +
                            "include machine learning, deep learning, natural language processing, and robotics." +
                            " With AI, machines can learn to recognize patterns in data and make decisions based " +
                            "on that analysis. AI is rapidly advancing and has the potential to significantly " +
                            "impact industries such as healthcare, finance, transportation, and manufacturing.",
                    chatResponse.getChoices().get(0).getMessage().getContent());
        });

        HttpClient mock = httpClientMock.getMock();

        verify(mock, times(1))
                .sendAsync(requestResponse.getRequest(),
                HttpResponse.BodyHandlers.ofString());
    }
}
