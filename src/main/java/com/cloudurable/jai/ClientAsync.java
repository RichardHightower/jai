package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.file.FileData;
import com.cloudurable.jai.model.file.FileDeleteResponse;
import com.cloudurable.jai.model.file.FileListResponse;
import com.cloudurable.jai.model.file.UploadFileRequest;
import com.cloudurable.jai.model.finetune.*;
import com.cloudurable.jai.model.image.CreateImageRequest;
import com.cloudurable.jai.model.image.CreateImageVariationRequest;
import com.cloudurable.jai.model.image.EditImageRequest;
import com.cloudurable.jai.model.image.ImageResponse;
import com.cloudurable.jai.model.model.ModelData;
import com.cloudurable.jai.model.model.ModelListResponse;
import com.cloudurable.jai.model.moderation.CreateModerationRequest;
import com.cloudurable.jai.model.moderation.CreateModerationResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;

import java.util.concurrent.CompletableFuture;

/**
 * The ClientAsync interface defines asynchronous methods for interacting with the JAI (Just AI) client.
 * These methods allow performing various tasks such as chat, completion, editing, embedding, transcription, translation,
 * and image manipulation asynchronously.
 */
public interface ClientAsync {

    /**
     * Moderation
     * @param moderationRequest moderationRequest
     * @return results.
     */
    CompletableFuture<ClientResponse<CreateModerationRequest, CreateModerationResponse>> moderateAsync(CreateModerationRequest moderationRequest);

    /**
     * Retrieves a list of models asynchronously.
     *
     * @return A CompletableFuture that resolves to the response containing the list of models.
     */
    CompletableFuture<ModelListResponse> listModelsAsync();

    /**
     * Retrieves information about a specific model asynchronously.
     *
     * @param id The ID of the model.
     * @return A CompletableFuture that resolves to the response containing the model data.
     */
    CompletableFuture<ModelData> getModelAsync(String id);

    /**
     * Retrieves a list of files asynchronously.
     *
     * @return A CompletableFuture that resolves to the response containing the list of files.
     */
    CompletableFuture<FileListResponse> listFilesAsync();

    /**
     * Retrieves data about a specific file asynchronously.
     *
     * @param id The ID of the file.
     * @return A CompletableFuture that resolves to the response containing the file data.
     */
    CompletableFuture<FileData> getFileDataAsync(String id);

    /**
     * Deletes a file asynchronously.
     *
     * @param id The ID of the file to delete.
     * @return A CompletableFuture that resolves to the response containing the file delete status.
     */
    CompletableFuture<FileDeleteResponse> deleteFileAsync(String id);

    /**
     * Retrieves the binary content of a file asynchronously.
     *
     * @param id The ID of the file.
     * @return A CompletableFuture that resolves to the binary content of the file.
     */
    CompletableFuture<byte[]> getFileContentBinaryAsync(String id);

    /**
     * Retrieves the string content of a file asynchronously.
     *
     * @param id The ID of the file.
     * @return A CompletableFuture that resolves to the string content of the file.
     */
    CompletableFuture<String> getFileContentStringAsync(String id);

    /**
     * Uploads a file to the JAI system asynchronously.
     *
     * @param uploadFileRequest The request containing the file to upload.
     * @return A CompletableFuture that resolves to the response containing the uploaded file data.
     */
    CompletableFuture<ClientResponse<UploadFileRequest, FileData>> uploadFileAsync(UploadFileRequest uploadFileRequest);

    /**
     * Retrieves a list of fine tunes asynchronously.
     *
     * @return A CompletableFuture that resolves to the response containing the list of fine tunes.
     */
    CompletableFuture<ListFineTuneResponse> listFineTunesAsync();

    /**
     * Retrieves a list of fine tune events for a specific fine tune asynchronously.
     *
     * @param id The ID of the fine tune.
     * @return A CompletableFuture that resolves to the response containing the list of fine tune events.
     */
    CompletableFuture<ListFineTuneEventResponse> listFineTuneEventsAsync(String id);

    /**
     * Retrieves data about a specific fine tune asynchronously.
     *
     * @param id The ID of the fine tune.
     * @return A CompletableFuture that resolves to the response containing the fine tune data.
     */
    CompletableFuture<FineTuneData> getFineTuneDataAsync(String id);

    /**
     * Creates a new fine tune asynchronously.
     *
     * @param createFineTuneRequest The request containing the fine tune parameters.
     * @return A CompletableFuture that resolves to the response containing the created fine tune data.
     */
    CompletableFuture<ClientResponse<CreateFineTuneRequest, FineTuneData>> createFineTuneAsync(CreateFineTuneRequest createFineTuneRequest);

    /**
     * Deletes a fine tune asynchronously.
     *
     * @param id The ID of the fine tune to delete.
     * @return A CompletableFuture that resolves to the response containing the fine tune delete status.
     */
    CompletableFuture<DeleteFineTuneResponse> deleteFineTuneAsync(String id);

    /**
     * Cancels a fine tune in progress asynchronously.
     *
     * @param id The ID of the fine tune to cancel.
     * @return A CompletableFuture that resolves to the fine tune data with the updated status.
     */
    CompletableFuture<FineTuneData> cancelFineTuneAsync(String id);

    /**
     * Asynchronously performs a chat request.
     *
     * @param chatRequest the chat request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type ClientResponse&lt;ChatRequest, ChatResponse&gt;.
     */
    CompletableFuture<ClientResponse<ChatRequest, ChatResponse>> chatAsync(ChatRequest chatRequest);

    /**
     * Asynchronously performs a completion request.
     *
     * @param completionRequest the completion request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;CompletionRequest, CompletionResponse&gt;`.
     */
    CompletableFuture<ClientResponse<CompletionRequest, CompletionResponse>> completionAsync(CompletionRequest completionRequest);

    /**
     * Asynchronously performs an edit request.
     *
     * @param editRequest the edit request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;EditRequest, EditResponse&gt;`.
     */
    CompletableFuture<ClientResponse<EditRequest, EditResponse>> editAsync(EditRequest editRequest);

    /**
     * Asynchronously performs an embedding request.
     *
     * @param embeddingRequest the embedding request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;mbeddingRequest, EmbeddingResponse&gt;`.
     */
    CompletableFuture<ClientResponse<EmbeddingRequest, EmbeddingResponse>> embeddingAsync(EmbeddingRequest embeddingRequest);

    /**
     * Asynchronously performs a transcription request.
     *
     * @param transcriptionRequest the transcription request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;TranscriptionRequest, AudioResponse&gt;`.
     */
    CompletableFuture<ClientResponse<TranscriptionRequest, AudioResponse>> transcribeAsync(TranscriptionRequest transcriptionRequest);

    /**
     * Asynchronously performs a translation request.
     *
     * @param translateRequest the translation request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;TranslateRequest, AudioResponse&gt;`.
     */
    CompletableFuture<ClientResponse<TranslateRequest, AudioResponse>> translateAsync(TranslateRequest translateRequest);

    /**
     * Asynchronously performs a create image request.
     *
     * @param imageRequest the create image request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;CreateImageRequest, ImageResponse&gt;`.
     */
    CompletableFuture<ClientResponse<CreateImageRequest, ImageResponse>> createImageAsync(CreateImageRequest imageRequest);

    /**
     * Asynchronously performs an edit image request.
     *
     * @param imageRequest the edit image request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;ditImageRequest, ImageResponse&gt;`.
     */
    CompletableFuture<ClientResponse<EditImageRequest, ImageResponse>> editImageAsync(EditImageRequest imageRequest);

    /**
     * Asynchronously performs a create image variation request.
     *
     * @param imageRequest the create image variation request to be sent.
     * @return a CompletableFuture that represents the asynchronous operation
     * and contains the client response of type `ClientResponse&lt;CreateImageVariationRequest, ImageResponse&gt;`.
     */
    CompletableFuture<ClientResponse<CreateImageVariationRequest, ImageResponse>> createImageVariationAsync(CreateImageVariationRequest imageRequest);

}
