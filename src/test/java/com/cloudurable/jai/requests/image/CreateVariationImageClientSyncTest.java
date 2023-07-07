package com.cloudurable.jai.requests.image;


import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.image.CreateImageVariationRequest;
import com.cloudurable.jai.model.image.ImageRequestSerializer;
import com.cloudurable.jai.model.image.ImageResponse;
import com.cloudurable.jai.model.image.ImageResponseFormat;
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

public class CreateVariationImageClientSyncTest {
    HttpClient httpClientMock;
    OpenAIClient client;
    String responseBody;
    String contentType;
    byte[] requestBody;
    CreateImageVariationRequest imageRequest;


    @Test
    void editImageAsync() throws Exception {
        client = OpenAIClient.builder().setApiKey("pk-123456789").setHttpClient(httpClientMock).build();

        final ClientResponse<CreateImageVariationRequest, ImageResponse> response = client.createImageVariation(imageRequest);

        response.getException().ifPresent(throwable -> throwable.printStackTrace());
        assertFalse(response.getException().isPresent());
        assertEquals(200, response.getStatusCode().orElse(-666));
        assertTrue(response.getResponse().isPresent());

        response.getResponse().ifPresent(imageResponse -> {
            assertNotNull(imageResponse.getData().get(0).getUrl().orElse(null));
        });

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
        imageRequest = CreateImageVariationRequest.builder()
                .responseFormat(ImageResponseFormat.URL)
                .imageFile(new File("super_hero.png"))
                .build();

        final MultipartEntityBuilder form = ImageRequestSerializer.buildVariationForm(imageRequest);

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
                try {
                    return CompletableFuture.completedFuture(send(request, responseBodyHandler));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler, HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
                return null;
            }
        };
    }
}
