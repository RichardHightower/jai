package com.cloudurable.jai.util;

import com.cloudurable.jai.model.ClientErrorResponse;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.ClientSuccessResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.CompletionResponseDeserializer;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatResponseDeserializer;

import java.net.http.HttpResponse;

/**
 * Utility class for handling requests and responses in the OpenAIClient.
 */
public class RequestResponseUtils {

    private RequestResponseUtils() {
    }

    /**
     * Retrieves an error response for a chat request with the given error and request.
     *
     * @param e           The error that occurred.
     * @param chatRequest The chat request.
     * @return The client error response.
     */
    public static ClientResponse<ChatRequest, ChatResponse> getErrorResponseForChatRequest(Throwable e, ChatRequest chatRequest) {
        ClientErrorResponse.Builder<ChatRequest, ChatResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e).request(chatRequest).build();
    }

    /**
     * Retrieves an error response for a completion request with the given error and request.
     *
     * @param e                 The error that occurred.
     * @param completionRequest The completion request.
     * @return The client error response.
     */
    public static ClientResponse<CompletionRequest, CompletionResponse> getErrorResponseForCompletionRequest(Throwable e, CompletionRequest completionRequest) {
        ClientErrorResponse.Builder<CompletionRequest, CompletionResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(completionRequest)
                .build();
    }

    /**
     * Retrieves a success response for a completion request with the given completion request, status code, and completion response.
     *
     * @param completionRequest  The completion request.
     * @param statusCode         The status code.
     * @param completionResponse The completion response.
     * @return The client success response.
     */
    public static ClientSuccessResponse<CompletionRequest, CompletionResponse> getCompletionResponseSuccess(CompletionRequest completionRequest,
                                                                                                            int statusCode,
                                                                                                            CompletionResponse completionResponse) {
        ClientSuccessResponse.Builder<CompletionRequest, CompletionResponse> builder = ClientSuccessResponse.builder();
        return builder.setRequest(completionRequest)
                .setResponse(completionResponse)
                .setStatusCode(statusCode)
                .build();
    }

    /**
     * Retrieves a success response for a completion request with a non-OK status code and status message.
     *
     * @param completionRequest The completion request.
     * @param statusCode        The status code.
     * @param status            The status message.
     * @return The client success response.
     */
    public static ClientSuccessResponse<CompletionRequest, CompletionResponse> getCompletionResponseNotOk(CompletionRequest completionRequest, int statusCode, String status) {
        ClientSuccessResponse.Builder<CompletionRequest, CompletionResponse> builder = ClientSuccessResponse.builder();
        return builder.setRequest(completionRequest)
                .setStatusCode(statusCode)
                .setStatusMessage(status)
                .build();
    }

    /**
     * Retrieves a success response for a chat request with the given chat request, status code, and chat response.
     *
     * @param chatRequest  The chat request.
     * @param statusCode   The status code.
     * @param chatResponse The chat response.
     * @return The client success response.
     */
    public static ClientSuccessResponse<ChatRequest, ChatResponse> getChatResponseSuccess(ChatRequest chatRequest, int statusCode, ChatResponse chatResponse) {
        ClientSuccessResponse.Builder<ChatRequest, ChatResponse> builder = ClientSuccessResponse.builder();
        return builder.setRequest(chatRequest).setResponse(chatResponse).setStatusCode(statusCode).build();
    }

    /**
     * Checks if the status code indicates a successful response (2xx range).
     *
     * @param statusCode The status code.
     * @return True if the status code is in the 2xx range, false otherwise.
     */
    public static boolean isOk(int statusCode) {
        return statusCode >= 200 && statusCode < 299;
    }

    /**
     * Retrieves a success response for a chat request with a non-OK status code and status message.
     *
     * @param chatRequest The chat request.
     * @param statusCode  The status code.
     * @param status      The status message.
     * @return The client success response.
     */
    public static ClientSuccessResponse<ChatRequest, ChatResponse> getChatResponseNotOk(ChatRequest chatRequest, int statusCode, String status) {
        ClientSuccessResponse.Builder<ChatRequest, ChatResponse> builder = ClientSuccessResponse.builder();
        return builder.setRequest(chatRequest).setStatusCode(statusCode).setStatusMessage(status).build();
    }

    /**
     * Retrieves a client success response for a chat request based on the HTTP response.
     *
     * @param chatRequest The chat request.
     * @param response    The HTTP response.
     * @return The client success response.
     */
    public static ClientSuccessResponse<ChatRequest, ChatResponse> getChatResponse(ChatRequest chatRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final ChatResponse chatResponse = ChatResponseDeserializer.deserialize(response.body());
            return getChatResponseSuccess(chatRequest, response.statusCode(), chatResponse);
        } else {
            return getChatResponseNotOk(chatRequest, response.statusCode(), response.body());
        }
    }

    /**
     * Retrieves a client success response for a completion request based on the HTTP response.
     *
     * @param completionRequest The completion request.
     * @param response          The HTTP response.
     * @return The client success response.
     */
    public static ClientSuccessResponse<CompletionRequest, CompletionResponse> getCompletionResponse(CompletionRequest completionRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final CompletionResponse completionResponse = CompletionResponseDeserializer.deserialize(response.body());
            return getCompletionResponseSuccess(completionRequest, response.statusCode(), completionResponse);
        } else {
            return getCompletionResponseNotOk(completionRequest, response.statusCode(), response.body());
        }
    }
}
