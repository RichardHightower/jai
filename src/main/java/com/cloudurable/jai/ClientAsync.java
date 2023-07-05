package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.image.CreateImageRequest;
import com.cloudurable.jai.model.image.CreateImageVariationRequest;
import com.cloudurable.jai.model.image.EditImageRequest;
import com.cloudurable.jai.model.image.ImageResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;

import java.util.concurrent.CompletableFuture;

public interface ClientAsync {
    CompletableFuture<ClientResponse<ChatRequest, ChatResponse>> chatAsync(ChatRequest chatRequest);

    CompletableFuture<ClientResponse<CompletionRequest, CompletionResponse>> completionAsync(CompletionRequest completionRequest);

    CompletableFuture<ClientResponse<EditRequest, EditResponse>> editAsync(EditRequest editRequest);

    CompletableFuture<ClientResponse<EmbeddingRequest, EmbeddingResponse>> embeddingAsync(EmbeddingRequest embeddingRequest);

    CompletableFuture<ClientResponse<TranscriptionRequest, AudioResponse>> transcribeAsync(TranscriptionRequest transcriptionRequest);

    CompletableFuture<ClientResponse<TranslateRequest, AudioResponse>> translateAsync(TranslateRequest translateRequest);

    CompletableFuture<ClientResponse<CreateImageRequest, ImageResponse>> createImageAsync(CreateImageRequest imageRequest);

    CompletableFuture<ClientResponse<EditImageRequest, ImageResponse>> editImageAsync(EditImageRequest imageRequest);

    CompletableFuture<ClientResponse<CreateImageVariationRequest, ImageResponse>> createImageVariationAsync(CreateImageVariationRequest imageRequest);
}
