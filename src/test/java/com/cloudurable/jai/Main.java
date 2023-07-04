package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(final String... args) {
        try {

//            callEmbeddingAsyncExample();
//            callEmbeddingExample();
//            callEditAsyncExample();
//            callEditExample();
//            callCompletionExample();
//            callChatExample();
//            callChatExampleAsync();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }



    private static void callEmbeddingAsyncExample() throws Exception {


        final CountDownLatch latch = new CountDownLatch(1);
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the request
        final EmbeddingRequest request = EmbeddingRequest.builder()
                .model("text-embedding-ada-002")
                .input("What is AI?")
                .build();

        // Call Open AI API with chat message
        //
        client.embeddingAsync(request).exceptionally(e -> {
                    e.printStackTrace();
                    latch.countDown();
                    return null;
                }

        ).thenAccept(response ->
        {
            response.getResponse().ifPresent(response1 -> response1.getData().forEach(System.out::println));
            response.getException().ifPresent(Throwable::printStackTrace);
            response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));
            latch.countDown();
        });


        latch.await(10, TimeUnit.SECONDS);

    }
    private static void callEmbeddingExample() {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the chat request
        final EmbeddingRequest request = EmbeddingRequest.builder()
                .model("text-embedding-ada-002")
                .input("What is AI?")
                .build();

        // Call Open AI API with chat message
        final ClientResponse<EmbeddingRequest, EmbeddingResponse> response = client.embedding(request);

        response.getResponse().ifPresent(completionResponse -> completionResponse.getData().forEach(System.out::println));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void callCompletionExample() {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the chat request
        final CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003").maxTokens(500)
                .prompt("What is AI?")
                .completionCount(3)
                .build();

        // Call Open AI API with chat message
        final ClientResponse<CompletionRequest, CompletionResponse> response = client.completion(request);

        response.getResponse().ifPresent(completionResponse -> completionResponse.getChoices().forEach(System.out::println));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void callEditExample() {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the chat request
        final EditRequest request = EditRequest.builder()
                .model("text-davinci-edit-001")
                .input("Where is u going?")
                .instruction("Fix grammar")
                .completionCount(3)
                .build();

        // Call Open AI API with chat message
        final ClientResponse<EditRequest, EditResponse> response = client.edit(request);

        response.getResponse().ifPresent(completionResponse -> completionResponse.getChoices().forEach(System.out::println));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }


    private static void callEditAsyncExample() throws Exception {


        final CountDownLatch latch = new CountDownLatch(1);
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the chat request
        final EditRequest request = EditRequest.builder()
                .model("text-davinci-edit-001")
                .input("Where is u going?")
                .instruction("Fix grammar")
                .completionCount(3)
                .build();

        // Call Open AI API with chat message
        //
        client.editAsync(request).exceptionally(e -> {
                    e.printStackTrace();
                    latch.countDown();
                    return null;
                }

        ).thenAccept(response ->
        {
            response.getResponse().ifPresent(response1 -> response1.getChoices().forEach(System.out::println));
            response.getException().ifPresent(Throwable::printStackTrace);
            response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));
            latch.countDown();
        });


        latch.await(10, TimeUnit.SECONDS);

    }

    private static void callChatExampleAsync() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);

        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the chat request
        final ChatRequest chatRequest = ChatRequest.builder().model("gpt-3.5-turbo")
                .addMessage(Message.builder().content("What is AI?").role(Role.USER).build())
                .build();

        // Call Open AI API with chat message
        client.chatAsync(chatRequest).exceptionally(e -> {
            e.printStackTrace();
            latch.countDown();
            return null;
        }).thenAccept(response -> {
            response.getResponse().ifPresent(chatResponse -> chatResponse.getChoices().forEach(System.out::println));
            response.getException().ifPresent(Throwable::printStackTrace);
            response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));
            latch.countDown();
        });

        latch.await(10, TimeUnit.SECONDS);
    }

    private static void callChatExample() throws Exception {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the chat request
        final ChatRequest chatRequest = ChatRequest.builder().model("gpt-3.5-turbo")
                .addMessage(Message.builder().content("What is AI?").role(Role.USER).build())
                .completionCount(5)
                .build();

        // Call Open AI API with chat message
        final ClientResponse<ChatRequest, ChatResponse> response = client.chat(chatRequest);

        response.getResponse().ifPresent(chatResponse -> chatResponse.getChoices().forEach(System.out::println));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));
    }
}
