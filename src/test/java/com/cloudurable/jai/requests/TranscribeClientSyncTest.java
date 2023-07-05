package com.cloudurable.jai.requests;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.audio.AudioRequestSerializer;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.AudioResponseFormat;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.util.MultipartEntityBuilder;
import io.nats.jparse.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.cloudurable.jai.model.audio.AudioRequestSerializer.getEncodingContentType;
import static org.junit.jupiter.api.Assertions.*;

public class TranscribeClientSyncTest {
    HttpClient httpClientMock;
    OpenAIClient client;
    String responseBody;
    String contentType;
    byte[] requestBody;
    TranscriptionRequest transcriptionRequest;

    /**
     * Test method to verify the behavior of the completion method in the OpenAIClient.
     * This test mocks a POST request to the /completions endpoint and verifies
     * the response from the OpenAIClient completion method.
     *
     * @throws Exception in case of errors
     */
    @Test
    void embeddingAsync() throws Exception {
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        final ClientResponse<TranscriptionRequest, AudioResponse> response = client.transcribe(transcriptionRequest);

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(textResponse -> {
            assertEquals("Imagine the wildest idea", textResponse.getText());
        });

    }

    /**
     * Setup method to initialize the client, mock HttpClient,
     * and set up request and response data before each test.
     */
    @BeforeEach
    void before() {
        // Create the response body
        responseBody = Json.niceJson("{ 'text': 'Imagine the wildest idea', 'model':'whisper-1'}");

        // Create the request body
        transcriptionRequest = TranscriptionRequest.builder()
                .model("gpt-3.5-turbo").responseFormat(AudioResponseFormat.JSON)
                .file(new File("test.m4a"))
                .build();

        final MultipartEntityBuilder form = AudioRequestSerializer.buildForm(transcriptionRequest);

        contentType = getEncodingContentType(form);

        requestBody = form.build();
        System.out.println(requestBody);


        httpClientMock = new HttpClient() {
            @Override
            public Optional<CookieHandler> cookieHandler() {
                return Optional.empty();
            }

            @Override
            public Optional<Duration> connectTimeout() {
                return Optional.empty();
            }

            @Override
            public Redirect followRedirects() {
                return null;
            }

            @Override
            public Optional<ProxySelector> proxy() {
                return Optional.empty();
            }

            @Override
            public SSLContext sslContext() {
                return null;
            }

            @Override
            public SSLParameters sslParameters() {
                return null;
            }

            @Override
            public Optional<Authenticator> authenticator() {
                return Optional.empty();
            }

            @Override
            public Version version() {
                return null;
            }

            @Override
            public Optional<Executor> executor() {
                return Optional.empty();
            }

            @Override
            public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) throws IOException, InterruptedException {
                return new HttpResponse<T>() {
                    @Override
                    public int statusCode() {
                        return 200;
                    }

                    @Override
                    public HttpRequest request() {
                        return null;
                    }

                    @Override
                    public Optional<HttpResponse<T>> previousResponse() {
                        return Optional.empty();
                    }

                    @Override
                    public HttpHeaders headers() {
                        return null;
                    }

                    @Override
                    public T body() {
                        return (T) (Object) responseBody;
                    }

                    @Override
                    public Optional<SSLSession> sslSession() {
                        return Optional.empty();
                    }

                    @Override
                    public URI uri() {
                        return null;
                    }

                    @Override
                    public Version version() {
                        return null;
                    }
                };
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
                return null;
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler, HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
                return null;
            }
        };
    }
}
