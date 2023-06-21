package com.cloudurable.jai;

import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.chat.*;

import java.util.function.Consumer;

public class Main {
    public static void main(final String... args) {
        try {

            final OpenAIClient client = OpenAIClient.builder().setApiKey("").build();

            final ChatRequest chatRequest = ChatRequest.builder().setModel("gpt-3.5-turbo")
                    .addMessage(Message.builder().setContent("What is AI?").setRole(Role.USER).build())
                    .build();

            ClientResponse<ChatRequest, ChatResponse> response = client.chat(chatRequest);

            response.getResponse().ifPresent(new Consumer<ChatResponse>() {
                @Override
                public void accept(ChatResponse chatResponse) {
                    chatResponse.getChoices().forEach(new Consumer<ChatChoice>() {
                        @Override
                        public void accept(ChatChoice chatChoice) {
                            System.out.println(chatChoice);
                        }
                    });
                }
            });

            response.getException().ifPresent(new Consumer<Exception>() {
                @Override
                public void accept(Exception e) {
                    e.printStackTrace();
                }
            });

            response.getStatusMessage().ifPresent(new Consumer<String>() {
                @Override
                public void accept(String error) {
                    System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0));
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
