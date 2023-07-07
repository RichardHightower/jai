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

    CompletableFuture<ModelListResponse> listModelsAsync();

    CompletableFuture<ModelData> getModelAsync(String id);

    CompletableFuture<FileListResponse> listFilesAsync();

    CompletableFuture<FileData> getFileDataAsync(String id);

    CompletableFuture<FileDeleteResponse> deleteFileAsync(String id);


    CompletableFuture<byte[]> getFileContentBinaryAsync(String id);

    CompletableFuture<String> getFileContentStringAsync(String id);

    CompletableFuture<ClientResponse<UploadFileRequest, FileData>> uploadFileAsync(UploadFileRequest uploadFileRequest);




    CompletableFuture<ListFineTuneResponse> listFineTunesAsync();

    CompletableFuture<ListFineTuneEventResponse> listFineTuneEventsAsync(String id);

    CompletableFuture<FineTuneData> getFineTuneDataAsync(String id);

    CompletableFuture<ClientResponse<CreateFineTuneRequest, FineTuneData>> createFineTuneAsync(CreateFineTuneRequest createFineTuneRequest);

    CompletableFuture<DeleteFineTuneResponse> deleteFineTuneAsync(String id);

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
