package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;

public interface Client {
    ClientResponse<ChatRequest, ChatResponse> chat(ChatRequest chatRequest);
    ClientResponse<CompletionRequest, CompletionResponse> completion(CompletionRequest completionRequest);
    ClientResponse<EditRequest, EditResponse> edit(EditRequest editRequest);
    ClientResponse<EmbeddingRequest, EmbeddingResponse> embedding(EmbeddingRequest embeddingRequest);

    ClientResponse<TranscriptionRequest, AudioResponse> transcribe(TranscriptionRequest transcriptionRequest);
    ClientResponse<TranslateRequest, AudioResponse> translate(TranslateRequest translateRequest);

}
