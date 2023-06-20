/**
 * This package contains classes and interfaces related to the OpenAIClient library.
 */
package com.cloudurable;

import com.cloudurable.com.cloudurable.model.chat.ChatRequest;
import com.cloudurable.com.cloudurable.model.chat.ChatRequestSerializer;
import com.cloudurable.com.cloudurable.model.chat.ChatResponse;
import com.cloudurable.com.cloudurable.model.chat.ChatResponseDeserializer;
import com.cloudurable.impl.ClientErrorResponse;
import com.cloudurable.impl.ClientSuccessResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Represents a client for interacting with the OpenAI API.
 */
public class OpenAIClient {

    private final String apiKey;
    private final String apiEndpoint;
    private final HttpClient httpClient;

    /**
     * Constructs an OpenAIClient object.
     *
     * @param apiKey      The API key for authentication with the OpenAI API.
     * @param apiEndpoint The API endpoint URL for the OpenAI API.
     * @param httpClient  The HTTP client used for making API requests.
     */
    public OpenAIClient(String apiKey, String apiEndpoint, HttpClient httpClient) {
        this.apiKey = apiKey;
        this.apiEndpoint = apiEndpoint;
        this.httpClient = httpClient;
    }

    /**
     * Sends a chat request to the OpenAI API and returns the client response.
     *
     * @param chatRequest The chat request to be sent.
     * @return The client response containing the chat request and the corresponding chat response.
     */
    public ClientResponse<ChatRequest, ChatResponse> chat(final ChatRequest chatRequest) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/chat/completions")
                .POST(HttpRequest.BodyPublishers.ofString(ChatRequestSerializer.serialize(chatRequest)));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            final ChatResponse chatResponse = ChatResponseDeserializer.deserialize(send.body());
            ClientSuccessResponse.Builder<ChatRequest, ChatResponse> builder = ClientSuccessResponse.builder();
            return builder.setRequest(chatRequest).setResponse(chatResponse).build();
        } catch (Exception e) {
            ClientErrorResponse.Builder<ChatRequest, ChatResponse> builder = ClientErrorResponse.builder();
            ClientResponse<ChatRequest, ChatResponse> errorResponse = builder.setException(e).setRequest(chatRequest).build();
            return errorResponse;
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
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .uri(URI.create(apiEndpoint + path));
    }
}
