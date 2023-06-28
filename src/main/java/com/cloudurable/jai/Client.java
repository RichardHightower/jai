package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;

public interface Client {
    ClientResponse<ChatRequest, ChatResponse> chat(ChatRequest chatRequest);

    ClientResponse<CompletionRequest, CompletionResponse> completion(CompletionRequest completionRequest);
}
