package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;

import java.util.concurrent.CompletableFuture;

public interface ClientAsync {
    CompletableFuture<ClientResponse<ChatRequest, ChatResponse>> chatAsync(ChatRequest chatRequest);

    CompletableFuture<ClientResponse<CompletionRequest, CompletionResponse>> completionAsync(CompletionRequest completionRequest);
}
