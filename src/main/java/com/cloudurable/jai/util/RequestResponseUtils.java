package com.cloudurable.jai.util;

import com.cloudurable.jai.model.ClientErrorResponse;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.ClientSuccessResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.CompletionResponseDeserializer;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatResponseDeserializer;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.edit.EditResponseDeserializer;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponseDeserializer;

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
        return builder.request(completionRequest)
                .response(completionResponse)
                .statusCode(statusCode)
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
        return builder.request(completionRequest)
                .statusCode(statusCode)
                .statusMessage(status)
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
        return builder.request(chatRequest).response(chatResponse).statusCode(statusCode).build();
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
        return builder.request(chatRequest).statusCode(statusCode).statusMessage(status).build();
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
    public static ClientSuccessResponse<CompletionRequest, CompletionResponse>
    getCompletionResponse(CompletionRequest completionRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final CompletionResponse completionResponse = CompletionResponseDeserializer.deserialize(response.body());
            return getCompletionResponseSuccess(completionRequest, response.statusCode(), completionResponse);
        } else {
            return getCompletionResponseNotOk(completionRequest, response.statusCode(), response.body());
        }
    }


    //getEmbeddingResponse

    public static ClientSuccessResponse<EmbeddingRequest, EmbeddingResponse>
    getEmbeddingResponse(EmbeddingRequest embeddingRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final EmbeddingResponse embeddingResponse = EmbeddingResponseDeserializer.deserialize(response.body());
            return getEmbeddingResponseSuccess(embeddingRequest, response.statusCode(), embeddingResponse);
        } else {
            return getEmbeddingResponseNotOk(embeddingRequest, response.statusCode(), response.body());
        }
    }

    public static ClientResponse<TranslateRequest, AudioResponse>
    getTranslateResponse(TranslateRequest translateRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            ClientSuccessResponse.Builder<TranslateRequest, AudioResponse> builder = ClientSuccessResponse.builder();

            return builder.request(translateRequest)
                    .response(AudioResponse.builder().body(response.body())
                            .responseFormat(translateRequest.getResponseFormat()).build())
                    .statusCode(response.statusCode())
                    .build();
        } else {
            ClientSuccessResponse.Builder<TranslateRequest, AudioResponse> builder = ClientSuccessResponse.builder();
            return builder.request(translateRequest)
                    .response(AudioResponse.builder().body(response.body())
                            .responseFormat(translateRequest.getResponseFormat()).build())
                    .statusCode(response.statusCode())
                    .build();
        }
    }

    public static ClientResponse<TranscriptionRequest, AudioResponse>
    getTranscriptionResponse(TranscriptionRequest transcriptionRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            ClientSuccessResponse.Builder<TranscriptionRequest, AudioResponse> builder = ClientSuccessResponse.builder();

            return builder.request(transcriptionRequest)
                    .response(AudioResponse.builder().body(response.body())
                            .responseFormat(transcriptionRequest.getResponseFormat()).build())
                    .statusCode(response.statusCode())
                    .build();
        } else {
            ClientSuccessResponse.Builder<TranscriptionRequest, AudioResponse> builder = ClientSuccessResponse.builder();
            return builder.request(transcriptionRequest)
                    .response(AudioResponse.builder().body(response.body())
                            .responseFormat(transcriptionRequest.getResponseFormat()).build())
                    .statusCode(response.statusCode())
                    .build();
        }
    }

    private static ClientSuccessResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> getEmbeddingResponseNotOk(com.cloudurable.jai.model.text.embedding.EmbeddingRequest embeddingRequest, int statusCode, String statusMessage) {
        ClientSuccessResponse.Builder<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> builder = ClientSuccessResponse.builder();
        return builder.request(embeddingRequest)
                .statusCode(statusCode)
                .statusMessage(statusMessage)
                .build();
    }

    private static ClientSuccessResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> getEmbeddingResponseSuccess(com.cloudurable.jai.model.text.embedding.EmbeddingRequest embeddingRequest, int statusCode, EmbeddingResponse embeddingResponse) {
        ClientSuccessResponse.Builder<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> builder = ClientSuccessResponse.builder();
        return builder.request(embeddingRequest)
                .response(embeddingResponse)
                .statusCode(statusCode)
                .build();
    }


    public static ClientSuccessResponse<EditRequest, EditResponse>
    getEditResponse(EditRequest editRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final EditResponse editResponse = EditResponseDeserializer.deserialize(response.body());
            return getEditResponseSuccess(editRequest, response.statusCode(), editResponse);
        } else {
            return getEditResponseNotOk(editRequest, response.statusCode(), response.body());
        }
    }

    public static ClientSuccessResponse<EditRequest, EditResponse> getEditResponseSuccess(EditRequest editRequest,
                                                                                               int statusCode,
                                                                                               EditResponse editResponse) {
        ClientSuccessResponse.Builder<EditRequest, EditResponse> builder = ClientSuccessResponse.builder();
        return builder.request(editRequest)
                .response(editResponse)
                .statusCode(statusCode)
                .build();
    }

    public static ClientSuccessResponse<EditRequest, EditResponse> getEditResponseNotOk(EditRequest editRequest, int statusCode, String status) {
        ClientSuccessResponse.Builder<EditRequest, EditResponse> builder = ClientSuccessResponse.builder();
        return builder.request(editRequest)
                .statusCode(statusCode)
                .statusMessage(status)
                .build();
    }


    //getErrorResponseForEmbeddingRequest

    public static ClientResponse<EditRequest, EditResponse> getErrorResponseForEditRequest(Throwable e, EditRequest editRequest) {
        ClientErrorResponse.Builder<EditRequest, EditResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(editRequest)
                .build();
    }

    public static ClientResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> getErrorResponseForEmbeddingRequest(Throwable e, com.cloudurable.jai.model.text.embedding.EmbeddingRequest embeddingRequest) {
        ClientErrorResponse.Builder<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(embeddingRequest)
                .build();
    }

    public static ClientResponse<TranslateRequest, AudioResponse> getErrorResponseForTranslateRequest(Throwable e, TranslateRequest translateRequest) {
        ClientErrorResponse.Builder<TranslateRequest, AudioResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(translateRequest)
                .build();
    }

    public static ClientResponse<TranscriptionRequest, AudioResponse> getErrorResponseForTranscriptionRequest(Throwable e, TranscriptionRequest transcriptionRequest) {
        ClientErrorResponse.Builder<TranscriptionRequest, AudioResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(transcriptionRequest)
                .build();
    }
}
