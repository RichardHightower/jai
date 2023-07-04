package com.cloudurable.jai;

import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class OpenAIClientTest {

    @Test
    void testBuilder() throws Exception {

        OpenAIClient.builder().setApiKey("foo")
                .setHttpClientBuilder(HttpClient.newBuilder()).setApiEndpoint("https://foo.com").build();

        OpenAIClient.builder().setApiKey("foo")
                .setApiEndpoint("https://foo.com").build();


        try {
            OpenAIClient.builder()
                    .setApiEndpoint("https://foo.com").build();
            fail();
        } catch (IllegalArgumentException ae) {
            assertTrue(true);
        }


        try {
            OpenAIClient.builder().setApiKey("foo")
                    .setApiEndpoint(null).build();
            fail();
        } catch (IllegalArgumentException ae) {
            assertTrue(true);
        }

    }

}
