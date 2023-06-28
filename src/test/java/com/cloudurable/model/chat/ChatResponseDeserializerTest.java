package com.cloudurable.model.chat;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatResponseDeserializer;
import com.cloudurable.jai.model.text.completion.chat.Role;
import io.nats.jparse.Json;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatResponseDeserializerTest {

    @Test
    void deserialize() {

        final String jsonBody = "{\n" +
                "   'id':'chatcmpl-123',\n" +
                "   'object':'chat.completion',\n" +
                "   'created':1677652288,\n" +
                "   'choices':[\n" +
                "      {\n" +
                "         'index':0,\n" +
                "         'message':{\n" +
                "            'role':'assistant',\n" +
                "            'content':'`n`nHello there, how may I assist you today?'\n" +
                "         },\n" +
                "         'finish_reason':'stop'\n" +
                "      }\n" +
                "   ],\n" +
                "   'usage':{\n" +
                "      'prompt_tokens':9,\n" +
                "      'completion_tokens':12,\n" +
                "      'total_tokens':21\n" +
                "   }\n" +
                "}";
        ChatResponse response = ChatResponseDeserializer.deserialize(Json.niceJson(jsonBody));
        assertEquals("chatcmpl-123", response.getId());
        assertEquals("chat.completion", response.getObject());
        assertEquals(1677652288, response.getCreated().toEpochMilli() / 1000);
        assertEquals(9, response.getUsage().getPromptTokens());
        assertEquals(12, response.getUsage().getCompletionTokens());
        assertEquals(21, response.getUsage().getTotalTokens());

        assertEquals(1, response.getChoices().size());
        assertEquals(Role.ASSISTANT, response.getChoices().get(0).getMessage().getRole());
        assertEquals("\n\nHello there, how may I assist you today?", response.getChoices().get(0).getMessage().getContent());
        assertEquals(FinishReason.STOP, response.getChoices().get(0).getFinishReason());

    }
}
