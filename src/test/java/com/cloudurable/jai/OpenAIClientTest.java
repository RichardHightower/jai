package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.chat.*;
import com.cloudurable.jai.test.mock.HttpClientMock;
import io.nats.jparse.Json;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class OpenAIClientTest {



    @Test
    void chat() throws Exception {
        final HttpClientMock httpClientMock = new HttpClientMock();

        final OpenAIClient client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        // Create the response body
        final String responseBody = Json.niceJson("{\n" +
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
        final ChatRequest chatRequest = ChatRequest.builder().setModel("gpt-3.5-turbo")
                .addMessage(Message.builder().setContent("What is AI?").setRole(Role.USER).build())
                .build();
        final String requestBody = ChatRequestSerializer.serialize(chatRequest);

        //Mock it
        httpClientMock.setResponsePost("/chat/completions", requestBody, responseBody);


        final ClientResponse<ChatRequest, ChatResponse> response = client.chat(chatRequest);

        //assertFalse(response.getStatusMessage().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(new Consumer<ChatResponse>() {
            @Override
            public void accept(ChatResponse chatResponse) {
                assertEquals(1, chatResponse.getChoices().size());
                assertEquals("AI stands for artificial intelligence. It refers to the development of computer systems that can perform tasks that typically require human intelligence, such as visual perception, speech recognition, decision-making, and language translation. AI technologies include machine learning, deep learning, natural language processing, and robotics. With AI, machines can learn to recognize patterns in data and make decisions based on that analysis. AI is rapidly advancing and has the potential to significantly impact industries such as healthcare, finance, transportation, and manufacturing.",
                        chatResponse.getChoices().get(0).getMessage().getContent());
            }
        });

    }

    @Test
    void chatAsync() {
    }
}
