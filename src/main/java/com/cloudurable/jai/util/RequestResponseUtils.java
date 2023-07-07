package com.cloudurable.jai.util;

import com.cloudurable.jai.model.ClientErrorResponse;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.ClientSuccessResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.file.FileData;
import com.cloudurable.jai.model.file.FileDataDeserializer;
import com.cloudurable.jai.model.file.UploadFileRequest;
import com.cloudurable.jai.model.finetune.CreateFineTuneRequest;
import com.cloudurable.jai.model.finetune.FineTuneData;
import com.cloudurable.jai.model.finetune.FineTuneDataDeserializer;
import com.cloudurable.jai.model.image.*;
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


    /**
     * Retrieves the embedding response from the HTTP response.
     *
     * @param embeddingRequest The embedding request.
     * @param response         The HTTP response containing the embedding response data.
     * @return The client success response with the embedding request and response, or an error response.
     */
    public static ClientSuccessResponse<EmbeddingRequest, EmbeddingResponse>
    getEmbeddingResponse(EmbeddingRequest embeddingRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final EmbeddingResponse embeddingResponse = EmbeddingResponseDeserializer.deserialize(response.body());
            return getEmbeddingResponseSuccess(embeddingRequest, response.statusCode(), embeddingResponse);
        } else {
            return getEmbeddingResponseNotOk(embeddingRequest, response.statusCode(), response.body());
        }
    }

    /**
     * Retrieves the translate response from the HTTP response.
     *
     * @param translateRequest The translate request.
     * @param response         The HTTP response containing the translate response data.
     * @return The client response with the translate request and audio response, or an error response.
     */
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
                    .statusCode(response.statusCode())
                    .statusMessage(response.body())
                    .build();
        }
    }

    /**
     * Retrieves the transcription response from the HTTP response.
     *
     * @param transcriptionRequest The transcription request.
     * @param response             The HTTP response containing the transcription response data.
     * @return The client response with the transcription request and audio response, or an error response.
     */
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
                    .statusCode(response.statusCode())
                    .statusMessage(response.body())
                    .build();
        }
    }

    public static ClientResponse<UploadFileRequest, FileData>
    getFileUploadResponse(UploadFileRequest transcriptionRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            ClientSuccessResponse.Builder<UploadFileRequest, FileData> builder = ClientSuccessResponse.builder();
            return builder.request(transcriptionRequest).statusCode(response.statusCode())
                    .response(FileDataDeserializer.deserialize(response.body())).build();

        } else {
            ClientSuccessResponse.Builder<UploadFileRequest, FileData> builder = ClientSuccessResponse.builder();
            return builder.request(transcriptionRequest)
                    .statusCode(response.statusCode())
                    .statusMessage(response.body())
                    .build();
        }
    }

    /**
     * getCreateImageResponse
     *
     * @param imageRequest imageRequest
     * @param response     response
     * @return cilent response
     */
    public static ClientResponse<CreateImageRequest, ImageResponse>
    getCreateImageResponse(CreateImageRequest imageRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            ClientSuccessResponse.Builder<CreateImageRequest, ImageResponse> builder = ClientSuccessResponse.builder();
            ImageResponse imageResponse = ImageResponseDeserializer.deserialize(response.body());
            return builder.request(imageRequest)
                    .response(imageResponse)
                    .statusCode(response.statusCode())
                    .build();
        } else {
            ClientSuccessResponse.Builder<CreateImageRequest, ImageResponse> builder = ClientSuccessResponse.builder();
            return builder.request(imageRequest)
                    .statusCode(response.statusCode())
                    .statusMessage(response.body())
                    .build();
        }
    }

    /**
     * getEditImageResponse
     *
     * @param imageRequest imageRequest
     * @param response     response
     * @return cilent response
     */
    public static ClientResponse<EditImageRequest, ImageResponse>
    getEditImageResponse(EditImageRequest imageRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            ClientSuccessResponse.Builder<EditImageRequest, ImageResponse> builder = ClientSuccessResponse.builder();
            ImageResponse imageResponse = ImageResponseDeserializer.deserialize(response.body());
            return builder.request(imageRequest)
                    .response(imageResponse)
                    .statusCode(response.statusCode())
                    .build();
        } else {
            ClientSuccessResponse.Builder<EditImageRequest, ImageResponse> builder = ClientSuccessResponse.builder();
            return builder.request(imageRequest)
                    .statusCode(response.statusCode())
                    .statusMessage(response.body())
                    .build();
        }
    }

    /**
     * getCreateVariationImageResponse
     *
     * @param imageRequest imageRequest
     * @param response     response
     * @return cilent response
     */
    public static ClientResponse<CreateImageVariationRequest, ImageResponse>
    getCreateVariationImageResponse(CreateImageVariationRequest imageRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            ClientSuccessResponse.Builder<CreateImageVariationRequest, ImageResponse> builder = ClientSuccessResponse.builder();
            ImageResponse imageResponse = ImageResponseDeserializer.deserialize(response.body());
            return builder.request(imageRequest)
                    .response(imageResponse)
                    .statusCode(response.statusCode())
                    .build();
        } else {
            ClientSuccessResponse.Builder<CreateImageVariationRequest, ImageResponse> builder = ClientSuccessResponse.builder();
            return builder.request(imageRequest)
                    .statusCode(response.statusCode())
                    .statusMessage(response.body())
                    .build();
        }
    }

    /**
     * getErrorResponseForCreateImageRequest
     *
     * @param e                  exception
     * @param createImageRequest image request
     * @return cilent response
     */
    public static ClientResponse<CreateImageRequest, ImageResponse> getErrorResponseForCreateImageRequest(Throwable e, CreateImageRequest createImageRequest) {
        ClientErrorResponse.Builder<CreateImageRequest, ImageResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(createImageRequest)
                .build();
    }

    /**
     * getErrorResponseForEditImageRequest
     *
     * @param e            exception
     * @param imageRequest image request
     * @return cilent response
     */
    public static ClientResponse<EditImageRequest, ImageResponse> getErrorResponseForEditImageRequest(Throwable e, EditImageRequest imageRequest) {
        ClientErrorResponse.Builder<EditImageRequest, ImageResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(imageRequest)
                .build();
    }

    /**
     * getErrorResponseForCreateImageVariationRequest
     *
     * @param e            exception
     * @param imageRequest image request
     * @return cilent response
     */
    public static ClientResponse<CreateImageVariationRequest, ImageResponse> getErrorResponseForCreateImageVariationRequest(Throwable e, CreateImageVariationRequest imageRequest) {
        ClientErrorResponse.Builder<CreateImageVariationRequest, ImageResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(imageRequest)
                .build();
    }


    /**
     * getEmbeddingResponseNotOk
     *
     * @param embeddingRequest embedding Request
     * @param statusCode       status Code
     * @param statusMessage    status Message
     * @return client response
     */
    private static ClientSuccessResponse<EmbeddingRequest, EmbeddingResponse> getEmbeddingResponseNotOk(EmbeddingRequest embeddingRequest, int statusCode, String statusMessage) {
        ClientSuccessResponse.Builder<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> builder = ClientSuccessResponse.builder();
        return builder.request(embeddingRequest)
                .statusCode(statusCode)
                .statusMessage(statusMessage)
                .build();
    }

    /**
     * getEmbeddingResponseSuccess
     *
     * @param embeddingRequest  embeddingRequest
     * @param statusCode        http status code
     * @param embeddingResponse response
     * @return cilent response
     */
    private static ClientSuccessResponse<EmbeddingRequest, EmbeddingResponse> getEmbeddingResponseSuccess(EmbeddingRequest embeddingRequest, int statusCode, EmbeddingResponse embeddingResponse) {
        ClientSuccessResponse.Builder<EmbeddingRequest, EmbeddingResponse> builder = ClientSuccessResponse.builder();
        return builder.request(embeddingRequest)
                .response(embeddingResponse)
                .statusCode(statusCode)
                .build();
    }


    /**
     * getEditResponse
     *
     * @param editRequest editRequest
     * @param response    http response
     * @return cilent response
     */
    public static ClientSuccessResponse<EditRequest, EditResponse>
    getEditResponse(EditRequest editRequest, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final EditResponse editResponse = EditResponseDeserializer.deserialize(response.body());
            return getEditResponseSuccess(editRequest, response.statusCode(), editResponse);
        } else {
            return getEditResponseNotOk(editRequest, response.statusCode(), response.body());
        }
    }

    public static ClientSuccessResponse<CreateFineTuneRequest, FineTuneData>
    getCreateFineTuneResponse(CreateFineTuneRequest request, HttpResponse<String> response) {
        if (isOk(response.statusCode())) {
            final FineTuneData fineTuneResponse = FineTuneDataDeserializer.deserialize(response.body());
            return getCreateFineTuneResponseSuccess(request, response.statusCode(), fineTuneResponse);
        } else {
            return getCreateFineTuneNotOk(request, response.statusCode(), response.body());
        }
    }

    /**
     * getEditResponseSuccess
     *
     * @param editRequest  editRequest
     * @param statusCode   http status code
     * @param editResponse edit repsonse
     * @return cilent response
     */
    public static ClientSuccessResponse<EditRequest, EditResponse> getEditResponseSuccess(EditRequest editRequest,
                                                                                          int statusCode,
                                                                                          EditResponse editResponse) {
        ClientSuccessResponse.Builder<EditRequest, EditResponse> builder = ClientSuccessResponse.builder();
        return builder.request(editRequest)
                .response(editResponse)
                .statusCode(statusCode)
                .build();
    }

    public static ClientSuccessResponse<CreateFineTuneRequest, FineTuneData> getCreateFineTuneResponseSuccess(CreateFineTuneRequest request,
                                                                                          int statusCode,
                                                                                                              FineTuneData response) {
        ClientSuccessResponse.Builder<CreateFineTuneRequest, FineTuneData> builder = ClientSuccessResponse.builder();
        return builder.request(request)
                .response(response)
                .statusCode(statusCode)
                .build();
    }

    /**
     * Retrieves an error response for an edit request that encountered an error.
     *
     * @param editRequest The edit request.
     * @param statusCode  The status code indicating the error.
     * @param status      The status message of the error.
     * @return The client response with the edit request and error response.
     */
    public static ClientSuccessResponse<EditRequest, EditResponse> getEditResponseNotOk(EditRequest editRequest, int statusCode, String status) {
        ClientSuccessResponse.Builder<EditRequest, EditResponse> builder = ClientSuccessResponse.builder();
        return builder.request(editRequest)
                .statusCode(statusCode)
                .statusMessage(status)
                .build();
    }

    public static ClientSuccessResponse<CreateFineTuneRequest, FineTuneData> getCreateFineTuneNotOk(CreateFineTuneRequest request, int statusCode, String status) {
        ClientSuccessResponse.Builder<CreateFineTuneRequest, FineTuneData> builder = ClientSuccessResponse.builder();
        return builder.request(request)
                .statusCode(statusCode)
                .statusMessage(status)
                .build();
    }

    /**
     * Retrieves an error response for an edit request that encountered an exception.
     *
     * @param e           The exception encountered during the edit request.
     * @param editRequest The edit request.
     * @return The client response with the edit request and error response.
     */
    public static ClientResponse<EditRequest, EditResponse> getErrorResponseForCreateFineTuneRequest(Throwable e, EditRequest editRequest) {
        ClientErrorResponse.Builder<EditRequest, EditResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(editRequest)
                .build();
    }

    public static ClientResponse<CreateFineTuneRequest, FineTuneData> getErrorResponseForCreateFineTuneRequest(Throwable e, CreateFineTuneRequest req) {
        ClientErrorResponse.Builder<CreateFineTuneRequest, FineTuneData> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(req)
                .build();
    }

    /**
     * Retrieves an error response for an embedding request that encountered an exception.
     *
     * @param e                The exception encountered during the embedding request.
     * @param embeddingRequest The embedding request.
     * @return The client response with the embedding request and error response.
     */
    public static ClientResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> getErrorResponseForEmbeddingRequest(Throwable e, com.cloudurable.jai.model.text.embedding.EmbeddingRequest embeddingRequest) {
        ClientErrorResponse.Builder<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(embeddingRequest)
                .build();
    }

    /**
     * Retrieves an error response for a translate request that encountered an exception.
     *
     * @param e                The exception encountered during the translate request.
     * @param translateRequest The translate request.
     * @return The client response with the translate request and error response.
     */
    public static ClientResponse<TranslateRequest, AudioResponse> getErrorResponseForTranslateRequest(Throwable e, TranslateRequest translateRequest) {
        ClientErrorResponse.Builder<TranslateRequest, AudioResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(translateRequest)
                .build();
    }

    /**
     * Retrieves an error response for a transcription request that encountered an exception.
     *
     * @param e                    The exception encountered during the transcription request.
     * @param transcriptionRequest The transcription request.
     * @return The client response with the transcription request and error response.
     */
    public static ClientResponse<TranscriptionRequest, AudioResponse> getErrorResponseForTranscriptionRequest(Throwable e, TranscriptionRequest transcriptionRequest) {
        ClientErrorResponse.Builder<TranscriptionRequest, AudioResponse> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(transcriptionRequest)
                .build();
    }

    public static ClientResponse<UploadFileRequest, FileData> getErrorResponseForUploadFileRequest(Throwable e, UploadFileRequest uploadFileRequest) {
        ClientErrorResponse.Builder<UploadFileRequest, FileData> builder = ClientErrorResponse.builder();
        return builder.exception(e)
                .request(uploadFileRequest)
                .build();
    }
}
