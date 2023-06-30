/**
 * This package contains classes and interfaces related to the OpenAIClient library.
 */
package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.SecretHolder;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionRequestSerializer;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatRequestSerializer;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.cloudurable.jai.util.RequestResponseUtils.*;

/**
 * Represents a client for interacting with the OpenAI API.
 */
public class OpenAIClient implements Client, ClientAsync {

    private final SecretHolder apiKey;
    private final String apiEndpoint;
    private final HttpClient httpClient;

    /**
     * Constructs an OpenAIClient object.
     *
     * @param apiKey      The API key for authentication with the OpenAI API.
     * @param apiEndpoint The API endpoint URL for the OpenAI API.
     * @param httpClient  The HTTP client used for making API requests.
     */
    public OpenAIClient(SecretHolder apiKey, String apiEndpoint, HttpClient httpClient) {
        this.apiKey = apiKey;
        this.apiEndpoint = apiEndpoint;
        this.httpClient = httpClient;
    }

    /**
     * builder
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }


    /**
     * Sends a chat request to the OpenAI API and returns the client response.
     *
     * @param chatRequest The chat request to be sent.
     * @return The client response containing the chat request and the corresponding chat response.
     */
    @Override
    public CompletableFuture<ClientResponse<ChatRequest, ChatResponse>> chatAsync(final ChatRequest chatRequest) {

        final String jsonRequest = ChatRequestSerializer.serialize(chatRequest);
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/chat/completions")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply((Function<HttpResponse<String>, ClientResponse<ChatRequest, ChatResponse>>) response ->
                        getChatResponse(chatRequest, response)).exceptionally(e ->
                        getErrorResponseForChatRequest(e, chatRequest));

    }

    /**
     * Sends a chat request to the OpenAI API and returns the client response.
     *
     * @param chatRequest The chat request to be sent.
     * @return The client response containing the chat request and the corresponding chat response.
     */
    @Override
    public ClientResponse<ChatRequest, ChatResponse> chat(final ChatRequest chatRequest) {

        final String jsonRequest = ChatRequestSerializer.serialize(chatRequest);
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/chat/completions")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getChatResponse(chatRequest, response);
        } catch (Exception e) {
            return getErrorResponseForChatRequest(e, chatRequest);
        }
    }


    /**
     * Creates an HTTP request builder with common headers and the specified path.
     *
     * @param path The path for the HTTP request.
     * @return The HTTP request builder.
     */
    private HttpRequest.Builder createRequestBuilderWithBody(final String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey.getSecret())
                .header("Content-Type", "application/json")
                .uri(URI.create(apiEndpoint + path));
    }


    /**
     * Sends a chat request to the OpenAI API and returns the client response.
     *
     * @param completionRequest The completion request to be sent.
     * @return The client response containing the completion request and the corresponding chat response.
     */
    @Override
    public ClientResponse<CompletionRequest, CompletionResponse> completion(final CompletionRequest completionRequest) {
        final String jsonRequest = CompletionRequestSerializer.serialize(completionRequest);
        // Build and send the HTTP request
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/completions")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getCompletionResponse(completionRequest, response);
        } catch (Exception e) {
            return getErrorResponseForCompletionRequest(e, completionRequest);
        }
    }

    /**
     * Builder for client.
     */
    public static class Builder {
        private SecretHolder apiKey;
        private String apiEndpoint = "https://api.openai.com/v1/";
        private HttpClient httpClient;

        private HttpClient.Builder httpClientBuilder;

        private Builder() {
        }

        /**
         * HttpClient.Builder
         *
         * @return HttpClient Builder
         */
        public HttpClient.Builder getHttpClientBuilder() {
            if (httpClientBuilder == null) {
                httpClientBuilder = HttpClient.newBuilder();
            }
            return httpClientBuilder;
        }

        /**
         * Set Http Client Builder
         *
         * @param httpClientBuilder httpBuilder
         * @return this
         */
        public Builder setHttpClientBuilder(HttpClient.Builder httpClientBuilder) {
            this.httpClientBuilder = httpClientBuilder;
            return this;
        }

        /**
         * HttpClient
         *
         * @return HttpClient
         */
        public HttpClient getHttpClient() {
            if (httpClient == null) {
                httpClient = getHttpClientBuilder().build();
            }
            return httpClient;
        }

        /**
         * Sets the HTTP client used for making API requests.
         *
         * @param httpClient The HTTP client.
         * @return The builder instance.
         */
        public Builder setHttpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Sets the API key for authentication with the OpenAI API.
         *
         * @param apiKey The API key.
         * @return The builder instance.
         */
        public Builder setApiKey(String apiKey) {
            this.apiKey = new SecretHolder(apiKey);
            return this;
        }

        /**
         * Sets the API endpoint URL for the OpenAI API.
         *
         * @param apiEndpoint The API endpoint URL.
         * @return The builder instance.
         */
        public Builder setApiEndpoint(String apiEndpoint) {
            this.apiEndpoint = apiEndpoint;
            return this;
        }

        /**
         * Builds the OpenAIClient object.
         *
         * @return The OpenAIClient object.
         * @throws IllegalArgumentException If any of the required parameters are missing.
         */
        public OpenAIClient build() {
            validateParameters();
            return new OpenAIClient(apiKey, apiEndpoint, getHttpClient());
        }

        private void validateParameters() {
            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalArgumentException("API key is required");
            }
            if (apiEndpoint == null || apiEndpoint.isEmpty()) {
                throw new IllegalArgumentException("API endpoint is required");
            }

        }
    }

}
