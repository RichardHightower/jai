/**
 * This package contains classes and interfaces related to the OpenAIClient library.
 */
package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientErrorResponse;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.SecretHolder;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.file.*;
import com.cloudurable.jai.model.finetune.*;
import com.cloudurable.jai.model.image.*;
import com.cloudurable.jai.model.model.ModelData;
import com.cloudurable.jai.model.model.ModelDataDeserializer;
import com.cloudurable.jai.model.model.ModelListResponse;
import com.cloudurable.jai.model.model.ModelListResponseDeserializer;
import com.cloudurable.jai.model.moderation.CreateModerationRequest;
import com.cloudurable.jai.model.moderation.CreateModerationRequestSerializer;
import com.cloudurable.jai.model.moderation.CreateModerationResponse;
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
import com.cloudurable.jai.util.RequestResponseUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.cloudurable.jai.model.audio.AudioRequestSerializer.buildForm;
import static com.cloudurable.jai.model.audio.AudioRequestSerializer.getEncodingContentType;
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


    @Override
    public CompletableFuture<ClientResponse<CreateModerationRequest, CreateModerationResponse>> moderateAsync(CreateModerationRequest moderationRequest) {
        final String jsonRequest = CreateModerationRequestSerializer.serialize(moderationRequest);

        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/moderations")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> getCreateModerationResponse(moderationRequest, response));

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    public ClientResponse<CreateModerationRequest, CreateModerationResponse> moderate(CreateModerationRequest moderationRequest) {

        final String jsonRequest = CreateModerationRequestSerializer.serialize(moderationRequest);

        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/moderations")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getCreateModerationResponse(moderationRequest, response);
        } catch (Exception e) {
            return getErrorResponseForCreateModerationRequest(e, moderationRequest);
        }
    }

    @Override
    public CompletableFuture<ModelListResponse> listModelsAsync() {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/models");
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(r -> ModelListResponseDeserializer.deserialize(r.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<ModelData> getModelAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/models/" + id);
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(r -> ModelDataDeserializer.deserialize(r.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<FileData> getFileDataAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/files/" + id);
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(r -> FileDataDeserializer.deserialize(r.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    public CompletableFuture<FileListResponse> listFilesAsync() {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/files");
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(r -> FileListResponseDeserializer.deserialize(r.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    public ModelListResponse listModels() {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/models");
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ModelListResponseDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileListResponse listFiles() {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/files");
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return FileListResponseDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileData getFileData(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/files/" + id);
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return FileDataDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientResponse<UploadFileRequest, FileData> uploadFile(UploadFileRequest uploadFileRequest) {
        MultipartEntityBuilder form = UploadFileRequestSerializer.buildForm(uploadFileRequest);
        try {
            final String contentType = getEncodingContentType(form);
            final HttpRequest request = createRequestBuilderWithBody("/files")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", contentType)
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return RequestResponseUtils.getFileUploadResponse(uploadFileRequest, response);
        } catch (Exception e) {
            return RequestResponseUtils.getErrorResponseForUploadFileRequest(e, uploadFileRequest);
        }
    }

    @Override
    public CompletableFuture<ClientResponse<UploadFileRequest, FileData>> uploadFileAsync(UploadFileRequest uploadFileRequest) {
        MultipartEntityBuilder form = UploadFileRequestSerializer.buildForm(uploadFileRequest);
        try {
            final String contentType = getEncodingContentType(form);
            final HttpRequest request = createRequestBuilderWithBody("/files")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", contentType)
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();


            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                    .thenApply(r -> RequestResponseUtils.getFileUploadResponse(uploadFileRequest, r))
                    .exceptionally(e -> RequestResponseUtils.getErrorResponseForUploadFileRequest(e, uploadFileRequest));

        } catch (Exception ex) {
            return CompletableFuture.failedFuture(ex);
        }
    }


    @Override
    public byte[] getFileContentBinary(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGetNoContent("/files/" + id + "/content");
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFileContentString(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGetNoContent("/files/" + id + "/content");
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CompletableFuture<byte[]> getFileContentBinaryAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGetNoContent("/files/" + id + "/content");
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenApply(HttpResponse::body);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<String> getFileContentStringAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGetNoContent("/files/" + id + "/content");
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    public FileDeleteResponse deleteFile(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilder("/files/" + id).DELETE();
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return FileDeleteResponseDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ListFineTuneResponse listFineTunes() {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/fine-tunes");
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ListFineTuneResponseDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CompletableFuture<ListFineTuneResponse> listFineTunesAsync() {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/fine-tunes");
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> ListFineTuneResponseDeserializer.deserialize(response.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<ListFineTuneEventResponse> listFineTuneEventsAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/fine-tunes/" + id + "/events");
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> ListFineTuneEventResponseDeserializer.deserialize(response.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    public ListFineTuneEventResponse listFineTuneEvents(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/fine-tunes/" + id + "/events");
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ListFineTuneEventResponseDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<FineTuneData> getFineTuneDataAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/fine-tunes/" + id);
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> FineTuneDataDeserializer.deserialize(response.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    public FineTuneData getFineTuneData(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/fine-tunes/" + id);
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return FineTuneDataDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public DeleteFineTuneResponse deleteFineTune(String id) {

        final HttpRequest.Builder requestBuilder = createRequestBuilder("/models/" + id).DELETE();
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return DeleteFineTuneResponseDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<DeleteFineTuneResponse> deleteFineTuneAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilder("/models/" + id).DELETE();
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> DeleteFineTuneResponseDeserializer.deserialize(response.body()));

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<FineTuneData> cancelFineTuneAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilder("/fine-tunes/" + id + "/cancel").POST(HttpRequest.BodyPublishers.ofString(""));
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> FineTuneDataDeserializer.deserialize(response.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public FineTuneData cancelFineTune(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilder("/fine-tunes/" + id + "/cancel").POST(HttpRequest.BodyPublishers.ofString(""));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return FineTuneDataDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CompletableFuture<ClientResponse<CreateFineTuneRequest, FineTuneData>> createFineTuneAsync(CreateFineTuneRequest createFineTuneRequest) {
        final String jsonRequest = CreateFineTuneRequestSerializer.serialize(createFineTuneRequest);
        // Build and send the HTTP request
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/fine-tunes")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> getCreateFineTuneResponse(createFineTuneRequest, response));

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public ClientResponse<CreateFineTuneRequest, FineTuneData> createFineTune(CreateFineTuneRequest createFineTuneRequest) {
        final String jsonRequest = CreateFineTuneRequestSerializer.serialize(createFineTuneRequest);
        // Build and send the HTTP request
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/fine-tunes")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getCreateFineTuneResponse(createFineTuneRequest, response);
        } catch (Exception e) {
            return getErrorResponseForCreateFineTuneRequest(e, createFineTuneRequest);
        }
    }


    @Override
    public CompletableFuture<FileDeleteResponse> deleteFileAsync(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilder("/files/" + id).DELETE();
        final HttpRequest request = requestBuilder.build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(r -> FileDeleteResponseDeserializer.deserialize(r.body()));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    public ModelData getModel(String id) {
        final HttpRequest.Builder requestBuilder = createRequestBuilderGet("/models/" + id);
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return ModelDataDeserializer.deserialize(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest.Builder createRequestBuilderGet(String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey.getSecret())
                .header("Content-Type", "application/json")
                .uri(URI.create(apiEndpoint + path)).GET();
    }

    private HttpRequest.Builder createRequestBuilderGetNoContent(String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey.getSecret())
                .uri(URI.create(apiEndpoint + path)).GET();
    }

    private HttpRequest.Builder createRequestBuilder(String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey.getSecret())
                .header("Content-Type", "application/json")
                .uri(URI.create(apiEndpoint + path));
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
            return getErrorResponseForCreateFineTuneRequest(e, editRequest);
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
            final String contentType = getEncodingContentType(form);
            final HttpRequest request = createRequestBuilderWithBody("/audio/transcriptions")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", contentType)
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return RequestResponseUtils.getTranscriptionResponse(transcriptionRequest, response);
        } catch (Exception e) {
            return RequestResponseUtils.getErrorResponseForTranscriptionRequest(e, transcriptionRequest);
        }

    }

    @Override
    public CompletableFuture<ClientResponse<TranscriptionRequest, AudioResponse>> transcribeAsync(TranscriptionRequest transcriptionRequest) {
        final MultipartEntityBuilder form = buildForm(transcriptionRequest);
        final String contentType = getEncodingContentType(form);
        if (transcriptionRequest.getLanguage() != null) {
            form.addTextBody("language", transcriptionRequest.getLanguage());
        }
        try {
            final HttpRequest request = createRequestBuilderWithBody("/audio/translations")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", contentType)
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).thenApply(response ->
                    RequestResponseUtils.getTranscriptionResponse(transcriptionRequest, response)
            ).exceptionally(e -> RequestResponseUtils.getErrorResponseForTranscriptionRequest(e, transcriptionRequest));
        } catch (Exception ex) {
            return CompletableFuture.failedFuture(ex);
        }
    }

    @Override
    public CompletableFuture<ClientResponse<TranslateRequest, AudioResponse>> translateAsync(TranslateRequest translateRequest) {
        MultipartEntityBuilder form = buildForm(translateRequest);
        final String contentType = getEncodingContentType(form);
        try {
            final HttpRequest request = createRequestBuilderWithBody("/audio/translations")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", contentType)
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).thenApply(response ->
                    RequestResponseUtils.getTranslateResponse(translateRequest, response)
            ).exceptionally(e -> RequestResponseUtils.getErrorResponseForTranslateRequest(e, translateRequest));

        } catch (Exception ex) {
            return CompletableFuture.failedFuture(ex);
        }

    }


    @Override
    public ClientResponse<TranslateRequest, AudioResponse> translate(TranslateRequest translateRequest) {
        MultipartEntityBuilder form = buildForm(translateRequest);
        final String contentType = getEncodingContentType(form);
        try {
            final HttpRequest request = createRequestBuilderWithBody("/audio/translations")
                    // The Content-Type header is important, don't forget to set it.
                    .header("Content-Type", contentType)
                    // Reads data from a pipeline stream.
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build())).build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return RequestResponseUtils.getTranslateResponse(translateRequest, response);
        } catch (Exception e) {
            ClientErrorResponse.Builder<TranslateRequest, AudioResponse> builder = ClientErrorResponse.builder();
            return builder.exception(e).request(translateRequest)
                    .build();
        }
    }

    @Override
    public ClientResponse<CreateImageRequest, ImageResponse> createImage(CreateImageRequest imageRequest) {
        final String jsonRequest = ImageRequestSerializer.buildJson(imageRequest);

        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/images/generations")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getCreateImageResponse(imageRequest, response);
        } catch (Exception e) {
            return getErrorResponseForCreateImageRequest(e, imageRequest);
        }
    }

    @Override
    public ClientResponse<EditImageRequest, ImageResponse> editImage(EditImageRequest imageRequest) {
        MultipartEntityBuilder form = ImageRequestSerializer.buildEditForm(imageRequest);
        try {
            final String contentType = getEncodingContentType(form);
            final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/images/edits")
                    .header("Content-Type", contentType)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build()));
            final HttpRequest request = requestBuilder.build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getEditImageResponse(imageRequest, response);
        } catch (Exception e) {
            return getErrorResponseForEditImageRequest(e, imageRequest);
        }
    }

    @Override
    public ClientResponse<CreateImageVariationRequest, ImageResponse> createImageVariation(CreateImageVariationRequest imageRequest) {
        MultipartEntityBuilder form = ImageRequestSerializer.buildVariationForm(imageRequest);
        try {
            final String contentType = getEncodingContentType(form);
            final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/images/variations")
                    .header("Content-Type", contentType)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(form.build()));
            final HttpRequest request = requestBuilder.build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return getCreateVariationImageResponse(imageRequest, response);
        } catch (Exception e) {
            return getErrorResponseForCreateImageVariationRequest(e, imageRequest);
        }
    }


    @Override
    public CompletableFuture<ClientResponse<CreateImageRequest, ImageResponse>> createImageAsync(CreateImageRequest imageRequest) {
        final String jsonRequest = ImageRequestSerializer.buildJson(imageRequest);

        final HttpRequest.Builder requestBuilder = createRequestBuilderWithJsonBody("/images/generations")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest));
        final HttpRequest request = requestBuilder.build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response ->
                        getCreateImageResponse(imageRequest, response)).exceptionally(e ->
                        getErrorResponseForCreateImageRequest(e, imageRequest));

    }

    @Override
    public CompletableFuture<ClientResponse<EditImageRequest, ImageResponse>> editImageAsync(EditImageRequest imageRequest) {
        MultipartEntityBuilder form = ImageRequestSerializer.buildEditForm(imageRequest);
        final String contentType = getEncodingContentType(form);
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/images/edits")
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofByteArray(form.build()));
        final HttpRequest request = requestBuilder.build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response ->
                        getEditImageResponse(imageRequest, response)).exceptionally(e ->
                        getErrorResponseForEditImageRequest(e, imageRequest));

    }

    @Override
    public CompletableFuture<ClientResponse<CreateImageVariationRequest, ImageResponse>> createImageVariationAsync(CreateImageVariationRequest imageRequest) {

        final MultipartEntityBuilder form = ImageRequestSerializer.buildVariationForm(imageRequest);
        final String contentType = getEncodingContentType(form);
        final HttpRequest.Builder requestBuilder = createRequestBuilderWithBody("/images/variations")
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofByteArray(form.build()));
        final HttpRequest request = requestBuilder.build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response ->
                        getCreateVariationImageResponse(imageRequest, response)).exceptionally(e ->
                        getErrorResponseForCreateImageVariationRequest(e, imageRequest));
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
                        getErrorResponseForCreateFineTuneRequest(e, editRequest));
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
