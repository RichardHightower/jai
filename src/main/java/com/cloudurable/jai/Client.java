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

/**
 * The Client interface defines synchronous methods for interacting with the JAI (Just AI) client.
 * These methods allow performing various tasks such as chat, completion, editing, embedding, transcription, translation,
 * and image manipulation synchronously.
 * <p>Implementations of this interface provide methods to send requests to the JAI client and receive corresponding responses.
 * Each method corresponds to a specific operation and accepts a request object as a parameter. The method returns a
 * ClientResponse object containing the response received from the JAI client.</p>
 * <p>Note that these methods are synchronous, meaning they will block until a response is received from the client.</p>
 */
public interface Client {


    /**
     * Moderation
     * @param moderationRequest moderationRequest
     * @return results.
     */
    ClientResponse<CreateModerationRequest, CreateModerationResponse> moderate(CreateModerationRequest moderationRequest);

    /**
     * Retrieves a list of models.
     *
     * @return The response containing the list of models.
     */
    ModelListResponse listModels();

    /**
     * Retrieves information about a specific model.
     *
     * @param id The ID of the model.
     * @return The response containing the model data.
     */
    ModelData getModel(String id);

    /**
     * Retrieves a list of files.
     *
     * @return The response containing the list of files.
     */
    FileListResponse listFiles();

    /**
     * Retrieves data about a specific file.
     *
     * @param id The ID of the file.
     * @return The response containing the file data.
     */
    FileData getFileData(String id);

    /**
     * Uploads a file to the JAI system.
     *
     * @param uploadFileRequest The request containing the file to upload.
     * @return The response containing the uploaded file data.
     */
    ClientResponse<UploadFileRequest, FileData> uploadFile(UploadFileRequest uploadFileRequest);

    /**
     * Retrieves the binary content of a file.
     *
     * @param id The ID of the file.
     * @return The binary content of the file.
     */
    byte[] getFileContentBinary(String id);

    /**
     * Retrieves the string content of a file.
     *
     * @param id The ID of the file.
     * @return The string content of the file.
     */
    String getFileContentString(String id);

    /**
     * Deletes a file.
     *
     * @param id The ID of the file to delete.
     * @return The response containing the file delete status.
     */
    FileDeleteResponse deleteFile(String id);

    /**
     * Retrieves a list of fine tunes.
     *
     * @return The response containing the list of fine tunes.
     */
    ListFineTuneResponse listFineTunes();

    /**
     * Retrieves a list of fine tune events for a specific fine tune.
     *
     * @param id The ID of the fine tune.
     * @return The response containing the list of fine tune events.
     */
    ListFineTuneEventResponse listFineTuneEvents(String id);

    /**
     * Retrieves data about a specific fine tune.
     *
     * @param id The ID of the fine tune.
     * @return The response containing the fine tune data.
     */
    FineTuneData getFineTuneData(String id);

    /**
     * Creates a new fine tune.
     *
     * @param createFineTuneRequest The request containing the fine tune parameters.
     * @return The response containing the created fine tune data.
     */
    ClientResponse<CreateFineTuneRequest, FineTuneData> createFineTune(CreateFineTuneRequest createFineTuneRequest);

    /**
     * Deletes a fine tune.
     *
     * @param id The ID of the fine tune to delete.
     * @return The response containing the fine tune delete status.
     */
    DeleteFineTuneResponse deleteFineTune(String id);

    /**
     * Cancels a fine tune in progress.
     *
     * @param id The ID of the fine tune to cancel.
     * @return The fine tune data with the updated status.
     */
    FineTuneData cancelFineTune(String id);

    /**
     * Sends a chat request and retrieves the corresponding chat response.
     *
     * @param chatRequest the chat request to be sent.
     * @return a `ClientResponse` object containing the chat response of type `ChatResponse`.
     */
    ClientResponse<ChatRequest, ChatResponse> chat(ChatRequest chatRequest);

    /**
     * Sends a completion request and retrieves the corresponding completion response.
     *
     * @param completionRequest the completion request to be sent.
     * @return a `ClientResponse` object containing the completion response of type `CompletionResponse`.
     */
    ClientResponse<CompletionRequest, CompletionResponse> completion(CompletionRequest completionRequest);

    /**
     * Sends an edit request and retrieves the corresponding edit response.
     *
     * @param editRequest the edit request to be sent.
     * @return a `ClientResponse` object containing the edit response of type `EditResponse`.
     */
    ClientResponse<EditRequest, EditResponse> edit(EditRequest editRequest);

    /**
     * Sends an embedding request and retrieves the corresponding embedding response.
     *
     * @param embeddingRequest the embedding request to be sent.
     * @return a `ClientResponse` object containing the embedding response of type `EmbeddingResponse`.
     */
    ClientResponse<EmbeddingRequest, EmbeddingResponse> embedding(EmbeddingRequest embeddingRequest);

    /**
     * Sends a transcription request and retrieves the corresponding transcription response.
     *
     * @param transcriptionRequest the transcription request to be sent.
     * @return a `ClientResponse` object containing the transcription response of type `AudioResponse`.
     */
    ClientResponse<TranscriptionRequest, AudioResponse> transcribe(TranscriptionRequest transcriptionRequest);

    /**
     * Sends a translate request and retrieves the corresponding transcription response.
     *
     * @param translateRequest the translate request to be sent.
     * @return a `ClientResponse` object containing the transcription response of type `AudioResponse`.
     */
    ClientResponse<TranslateRequest, AudioResponse> translate(TranslateRequest translateRequest);


    /**
     * Sends a create image request and retrieves the corresponding create image response.
     *
     * @param imageRequest the create image request to be sent.
     * @return a `ClientResponse` object containing the create image response of type `ImageResponse`.
     */
    ClientResponse<CreateImageRequest, ImageResponse> createImage(CreateImageRequest imageRequest);

    /**
     * Sends an edit image request and retrieves the corresponding edit image response.
     *
     * @param imageRequest the edit image request to be sent.
     * @return a `ClientResponse` object containing the edit image response of type `ImageResponse`.
     */
    ClientResponse<EditImageRequest, ImageResponse> editImage(EditImageRequest imageRequest);

    /**
     * Sends a create image variation request and retrieves the corresponding create image variation response.
     *
     * @param imageRequest the create image variation request to be sent.
     * @return a `ClientResponse` object containing the create image variation response of type `ImageResponse`.
     */
    ClientResponse<CreateImageVariationRequest, ImageResponse> createImageVariation(CreateImageVariationRequest imageRequest);
}
