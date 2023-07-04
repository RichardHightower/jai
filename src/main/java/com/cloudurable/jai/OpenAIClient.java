/**
 * This package contains classes and interfaces related to the OpenAIClient library.
 */
package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientErrorResponse;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.ClientSuccessResponse;
import com.cloudurable.jai.model.SecretHolder;
import com.cloudurable.jai.model.audio.AudioRequest;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionRequestSerializer;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatRequestSerializer;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditRequestSerializer;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequestSerializer;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;
import com.cloudurable.jai.util.MultipartEntityBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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

    private static MultipartEntityBuilder buildForm(AudioRequest audioRequest) {
        MultipartEntityBuilder form = MultipartEntityBuilder.create()
                .addTextBody("model", audioRequest.getModel());


        if (audioRequest.getPrompt() != null) {
            form.addTextBody("prompt", audioRequest.getPrompt());
        }

        if (audioRequest.getResponseFormat() != null) {
            form.addTextBody("response_format", audioRequest.getResponseFormat());
        }

        if (audioRequest.getTemperature() != 0.0) {
            form.addTextBody("temperature", String.valueOf(audioRequest.getTemperature()));
        }

        form.addBinaryBody("file", audioRequest.getFile(), "application/binary", "transcribe.m4a");
        return form;
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
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/chat/completions")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply((Function<HttpResponse<String>, ClientResponse<ChatRequest, ChatResponse>>) response ->
                        getChatResponse(chatRequest, response)).exceptionally(e ->
                        getErrorResponseForChatRequest(e, chatRequest));

    }

    /**
     * Sends a completion request to the OpenAI API and returns the client response.
     *
     * @param completionRequest The chat request to be sent.
     * @return The client response containing the completion request and the corresponding completion response.
     */
    @Override
    public CompletableFuture<ClientResponse<CompletionRequest, CompletionResponse>> completionAsync(
            final CompletionRequest completionRequest) {
        final String jsonRequest = CompletionRequestSerializer.serialize(completionRequest);
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/completions")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply((Function<HttpResponse<String>, ClientResponse<CompletionRequest, CompletionResponse>>) response ->
                        getCompletionResponse(completionRequest, response)).exceptionally(e ->
                        getErrorResponseForCompletionRequest(e, completionRequest));
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
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/chat/completions")
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
    private HttpRequest.Builder createRequestBuilderWithJsonBody(final String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey.getSecret())
                .header("Content-Type", "application/json")
                .uri(URI.create(apiEndpoint + path));
    }

    private HttpRequest.Builder createRequestBuilderWithBody(final String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey.getSecret())
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
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/completions")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getCompletionResponse(completionRequest, response);
        } catch (Exception e) {
            return getErrorResponseForCompletionRequest(e, completionRequest);
        }
    }

    @Override
    public ClientResponse<EditRequest, EditResponse> edit(final EditRequest editRequest) {
        final String jsonRequest = EditRequestSerializer.serialize(editRequest);
        // Build and send the HTTP request
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/edits")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getEditResponse(editRequest, response);
        } catch (Exception e) {
            return getErrorResponseForEditRequest(e, editRequest);
        }
    }

    @Override
    public ClientResponse<EmbeddingRequest, EmbeddingResponse> embedding(EmbeddingRequest embeddingRequest) {
        final String jsonRequest = EmbeddingRequestSerializer.serialize(embeddingRequest);
        // Build and send the HTTP request
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/embeddings")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getEmbeddingResponse(embeddingRequest, response);
        } catch (Exception e) {
            return getErrorResponseForEmbeddingRequest(e, embeddingRequest);
        }
    }

    @Override
    public ClientResponse<TranscriptionRequest, AudioResponse> transcribe(TranscriptionRequest transcriptionRequest) {

        MultipartEntityBuilder form = buildForm(transcriptionRequest);

        if (transcriptionRequest.getLanguage() != null) {
            form.addTextBody("language", transcriptionRequest.getLanguage());
        }

        try {

            HttpRequest request = createRequestBuilderWithBody("/audio/transcriptions")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", "multipart/form-data; boundary")
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();


            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            ClientSuccessResponse.Builder<TranscriptionRequest, AudioResponse> builder = ClientSuccessResponse.builder();
            return builder.setRequest(transcriptionRequest)
                    .setResponse(AudioResponse.builder().body(response.body()).build())
                    .setStatusCode(response.statusCode())
                    .build();
        } catch (Exception e) {
            ClientErrorResponse.Builder<TranscriptionRequest, AudioResponse> builder = ClientErrorResponse.builder();
            return builder.exception(e).request(transcriptionRequest)
                    .build();
        }

    }

    @Override
    public ClientResponse<TranslateRequest, AudioResponse> translate(TranslateRequest translateRequest) {

        MultipartEntityBuilder form = buildForm(translateRequest);

        try {
            final String contentType = "multipart/form-data;boundary=\"" + form.getBoundary() + "\"";
            HttpRequest request = createRequestBuilderWithBody("/audio/translations")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", contentType)
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();


            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            ClientSuccessResponse.Builder<TranslateRequest, AudioResponse> builder = ClientSuccessResponse.builder();
            return builder.setRequest(translateRequest)
                    .setResponse(AudioResponse.builder().body(response.body()).build())
                    .setStatusCode(response.statusCode())
                    .build();
        } catch (Exception e) {
            ClientErrorResponse.Builder<TranslateRequest, AudioResponse> builder = ClientErrorResponse.builder();
            return builder.exception(e).request(translateRequest)
                    .build();
        }
    }

    public CompletableFuture<ClientResponse<EmbeddingRequest, EmbeddingResponse>> embeddingAsync(final EmbeddingRequest embeddingRequest) {
        final String jsonRequest = EmbeddingRequestSerializer.serialize(embeddingRequest);
        // Build and send the HTTP request
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/embeddings")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply((Function<HttpResponse<String>, ClientResponse<EmbeddingRequest, EmbeddingResponse>>) response ->
                        getEmbeddingResponse(embeddingRequest, response)).exceptionally(e ->
                        getErrorResponseForEmbeddingRequest(e, embeddingRequest));
    }

    @Override
    public CompletableFuture<ClientResponse<EditRequest, EditResponse>> editAsync(EditRequest editRequest) {
        final String jsonRequest = EditRequestSerializer.serialize(editRequest);
        // Build and send the HTTP request
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/edits")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply((Function<HttpResponse<String>, ClientResponse<EditRequest, EditResponse>>) response ->
                        getEditResponse(editRequest, response)).exceptionally(e ->
                        getErrorResponseForEditRequest(e, editRequest));
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
