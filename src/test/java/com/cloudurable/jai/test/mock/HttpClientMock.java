package com.cloudurable.jai.test.mock;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

/**
 * HttpClientMock extends HttpClient for creating a mock HttpClient useful for testing.
 * It provides a set of methods to set up responses for various request types including synchronous and asynchronous POST and PUT requests.
 */
public class HttpClientMock extends HttpClient {
    private final String apiEndpoint = "https://api.openai.com/v1/";
    protected HttpClient mockClient;

    /**
     * Set up a mocked response for a synchronous POST request.
     *
     * @param path the request path
     * @param requestBody the request body as a String
     * @param responseBody the response body as a String
     * @return this HttpClientMock instance
     * @throws Exception in case of errors
     */
    public HttpClientMock setResponsePost(String path, String requestBody, String responseBody) throws Exception{
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody(path);
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
        final HttpRequest request = requestBuilder.build();
        final HttpResponse<String> response = httpResponseBuilder().setBody(responseBody).build();
        when(mockClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        return this;
    }

    /**
     * Set up a mocked response for a synchronous PUT request.
     *
     * @param path the request path
     * @param requestBody the request body as a String
     * @param responseBody the response body as a String
     * @return this HttpClientMock instance
     * @throws Exception in case of errors
     */
    public HttpClientMock setResponsePut(String path, String requestBody, String responseBody) throws Exception{
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody(path);
        requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(requestBody));
        final HttpRequest request = requestBuilder.build();
        final HttpResponse<String> response = httpResponseBuilder().setBody(responseBody).build();
        when(mockClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        verify(mockClient.send(request, HttpResponse.BodyHandlers.ofString()), atLeastOnce());
        return this;
    }

    /**
     * Set up a mocked response for a asynchronous POST request.
     *
     * @param path the request path
     * @param requestBody the request body as a String
     * @param responseBody the response body as a String
     * @return this HttpClientMock instance
     * @throws Exception in case of errors
     */
    public HttpClientMock setResponsePostAsync(String path, String requestBody, String responseBody) throws Exception{
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody(path);
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
        final HttpRequest request = requestBuilder.build();
        final HttpResponse<String> response = httpResponseBuilder().setBody(responseBody).build();
        final CompletableFuture<HttpResponse<String>> future = CompletableFuture.supplyAsync(() -> response);
        when(mockClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())).thenReturn(future);
        return this;
    }

    /**
     * Set up a mocked response for a asynchronous PUT request.
     *
     * @param path the request path
     * @param requestBody the request body as a String
     * @param responseBody the response body as a String
     * @return this HttpClientMock instance
     * @throws Exception in case of errors
     */
    public HttpClientMock setResponsePutAsync(String path, String requestBody, String responseBody) throws Exception{
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody(path);
        requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(requestBody));
        final HttpRequest request = requestBuilder.build();
        final HttpResponse<String> response = httpResponseBuilder().setBody(responseBody).build();
        final CompletableFuture<HttpResponse<String>> future = CompletableFuture.supplyAsync(() -> response);
        when(mockClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())).thenReturn(future);
        verify(mockClient.send(request, HttpResponse.BodyHandlers.ofString()), atLeastOnce());
        return this;
    }

    /**
     * Helper method to create a request builder with default headers and given path.
     *
     * @param path the request path
     * @return HttpRequest.Builder instance
     */
    private HttpRequest.Builder createRequestBuilderWithBody(final String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + "pk-123456789")
                .header("Content-Type", "application/json")
                .uri(URI.create(apiEndpoint + path));
    }

    /**
     * Get an instance of HttpResponseBuilder.
     *
     * @return HttpResponseBuilder instance
     */
    public static HttpResponseBuilder httpResponseBuilder() {
        return new HttpResponseBuilder();
    }


    /**
     * HttpResponseBuilder is a builder for creating mock HttpResponse instances for testing.
     */
    public static class HttpResponseBuilder {
        private HttpResponseBuilder(){}

        private String contentType = "application/json";
        private int statusCode = 200;

        private String body = "null";

        public String getBody() {
            return body;
        }

        public HttpResponseBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public String getContentType() {
            return contentType;
        }

        public HttpResponseBuilder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public HttpResponseBuilder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public HttpResponse<String> build() {

            final HttpResponse<String> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(this.getStatusCode());
            when(mockResponse.body()).thenReturn(this.getBody());
            return mockResponse;
        }
    }




    /**
     * Constructor for HttpClientMock, creating a mock HttpClient instance.
     */
    public HttpClientMock() {
        mockClient = mock(HttpClient.class);
    }

    @Override
    public Optional<CookieHandler> cookieHandler() {
        return mockClient.cookieHandler();
    }

    @Override
    public Optional<Duration> connectTimeout() {
        return mockClient.connectTimeout();
    }

    @Override
    public Redirect followRedirects() {
        return mockClient.followRedirects();
    }

    @Override
    public Optional<ProxySelector> proxy() {
        return mockClient.proxy();
    }

    @Override
    public SSLContext sslContext() {
        return mockClient.sslContext();
    }

    @Override
    public SSLParameters sslParameters() {
        return mockClient.sslParameters();
    }

    @Override
    public Optional<Authenticator> authenticator() {
        return mockClient.authenticator();
    }

    @Override
    public Version version() {
        return mockClient.version();
    }

    @Override
    public Optional<Executor> executor() {
        return mockClient.executor();
    }

    @Override
    public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) throws IOException, InterruptedException {
        return mockClient.send(request, responseBodyHandler);
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        return mockClient.sendAsync(request, responseBodyHandler);
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler,
                                                            HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
        return mockClient.sendAsync(request, responseBodyHandler, pushPromiseHandler);
    }
}