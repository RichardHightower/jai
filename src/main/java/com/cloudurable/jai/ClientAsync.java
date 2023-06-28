package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;

import java.util.concurrent.CompletableFuture;

public interface ClientAsync {
    CompletableFuture<ClientResponse<ChatRequest, ChatResponse>> chatAsync(ChatRequest chatRequest);
}
