package com.cloudurable.jai.examples;

import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.AudioResponseFormat;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.file.UploadFileRequest;
import com.cloudurable.jai.model.image.*;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;
import com.cloudurable.jai.model.text.completion.chat.function.*;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;
import com.cloudurable.jai.util.JsonSerializer;
import io.nats.jparse.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Main {
    public static void main(final String... args) {
        try {

            chatWithFunctions();


//            listFiles();
//            final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
//
//            uploadFile(new File("prompts.jsonl"));
//
//            Thread.sleep(1000);
//            listFiles();
//            FileData fileData = client.listFiles().getData().get(0);
//            getFileDataAsync(fileData.getId());
//
//            final String contents = getFileContents(fileData.getId());
//
//
//            client.listFiles().getData().forEach(fileData1 -> deleteFile(fileData1.getId()));
//            listFiles();


//            listFilesAsync();
//            deleteFile("file-oGS5LrLOSrGosbREBfZahbWt");
//            deleteFileAsync("file-I0sbD442zLMWkGZAlCiLGdHW");
//            listFiles();
            //listFilesAsync();
            //getFileData("file-HBNdOLJK7rKf9jbP1DsBc7b8");
            //getFileDataAsync("file-HBNdOLJK7rKf9jbP1DsBc7b8");
            //getModelAsync("whisper-1");
            //listModelsAsync();
            //getModel("whisper-1");
            //listModels();

            //           createVariationsImageAsync();
            //           editImageAsync();
            //           callCreateImageAsync();
//            createVariationsImage();
//            editImage();
//            callCreateImage();
//           callTranslate();
//            callTranscribe();
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


    private static void chatWithFunctions() throws ExecutionException, InterruptedException {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        FunctionDef getCurrentWeatherFunc = FunctionDef.builder().name("get_current_weather")
                .description("Get the current weather in a given location")
                .setParameters(
                    ObjectParameter.objectParamBuilder()
                        .addParameter(Parameter.builder().name("location").description("The city and state, e.g. Austin, TX").build())
                        .addParameter(
                                EnumParameter.enumBuilder()
                                        .name("unit").enumValues("celsius", "fahrenheit").build())
                            .required("location")
                        .build()
        ).build();

        Map<String, java.util.function.Function<ObjectNode, String>> functionMap = new HashMap<>();
        functionMap.put("get_current_weather", objectNode -> {
            final String location = objectNode.getString("location");

            final String unit = Optional.ofNullable(objectNode.get("unit"))
                    .map(node->node.asScalar().stringValue()).orElse("fahrenheit");
            final JsonSerializer json = new JsonSerializer();

            json.startObject();
            json.addAttribute("location", location);
            json.addAttribute("unit", unit);

            switch (location)  {
                case "Austin, TX":
                    json.addAttribute("temperature", 92);
                    json.endObject();
                    return json.toString();
                case "Boston, MA":
                    json.addAttribute("temperature", 72);
                    json.endObject();
                    return json.toString();
                default:
                    json.addAttribute("temperature", 70);
                    json.endObject();
                    return json.toString();
            }
        });



        // messages = [{"role": "user", "content": "What's the weather like in Boston?"}]

        ChatRequest.Builder chatBuilder = ChatRequest.builder().model("gpt-3.5-turbo-0613")
                .addMessage(Message.builder().role(Role.USER).content("What's the weather like in Boston in fahrenheit?").build())
                .addFunction(getCurrentWeatherFunc)
                .functionalCall(ChatRequest.AUTO);


        ChatRequest chatRequest = chatBuilder.build();

        ClientResponse<ChatRequest, ChatResponse> chat = client.chat(chatRequest);

        chat.getResponse().ifPresent(chatResponse -> {
            var responseMessage = chatResponse.getChoices().get(0).getMessage();
            var functionCall = responseMessage.getFunctionCall();
            if (functionCall!=null && functionMap.containsKey(functionCall.getName())) {
                String functionResponse = functionMap.get(functionCall.getName()).apply(functionCall.getArguments());

                chatBuilder.addMessage(Message.builder().name(functionCall.getName()).role(Role.FUNCTION)
                        .content(functionResponse)
                        .build());

                ClientResponse<ChatRequest, ChatResponse> response2 = client.chat(chatBuilder.build());


                response2.getResponse().ifPresent(new Consumer<ChatResponse>() {
                    @Override
                    public void accept(ChatResponse chatResponse) {
                        System.out.println(chatResponse.getChoices().get(0).getMessage().getContent());
                    }
                });

                if (response2.getStatusMessage().isPresent()) {
                    System.out.println(response2.getStatusMessage().orElse(""));
                }
            }
        });

        System.out.println(chat.getStatusCode().orElse(666));
        System.out.println(chat.getStatusMessage().orElse(""));

        chat.getException().ifPresent(e->
                e.printStackTrace());
    }

    private static void uploadFile(File file) throws ExecutionException, InterruptedException {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        System.out.println(client.uploadFile(UploadFileRequest.builder().file(file).purpose("fine-tune").build()));
    }

    private static void getModelAsync(String s) throws ExecutionException, InterruptedException {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        System.out.println(client.getModelAsync(s).get());
    }

    private static void getFileDataAsync(String s) throws ExecutionException, InterruptedException {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        System.out.println(client.getFileDataAsync(s).get());
    }

    private static String getFileContents(String s) throws ExecutionException, InterruptedException {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        return client.getFileContentString(s);
    }

    private static void listModelsAsync() throws Exception {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        client.listModelsAsync().get().getData().forEach(modelData -> System.out.println(modelData.getId()));
    }

    private static void getModel(String s) {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        System.out.println(client.getModel(s));
    }

    private static void deleteFile(String s) {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        System.out.println(client.deleteFile(s));
    }


    private static void deleteFileAsync(String s) throws ExecutionException, InterruptedException {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        System.out.println(client.deleteFileAsync(s).get());
    }

    private static void getFileData(String s) {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        System.out.println(client.getFileData(s));
    }

    private static void listModels() throws Exception {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        client.listModels().getData().forEach(modelData -> System.out.println(modelData.getId()));
    }

    private static void listFiles() throws Exception {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        client.listFiles().getData().forEach(modelData -> System.out.println(modelData.getId()));
    }

    private static void listFilesAsync() throws Exception {
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        client.listFilesAsync().get().getData().forEach(modelData -> System.out.println(modelData.getId()));
    }

    private static void createVariationsImageAsync() throws ExecutionException, InterruptedException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final CreateImageVariationRequest request = CreateImageVariationRequest.builder()
                .imageFile(new File("super_hero.png"))
                .n(5)
                .responseFormat(ImageResponseFormat.URL)
                .build();

        final ClientResponse<CreateImageVariationRequest, ImageResponse> response = client.createImageVariationAsync(request).get();

        response.getResponse().ifPresent(r -> System.out.println(r.toString()));
        response.getResponse().ifPresent(r -> System.out.println(r.getCreated()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void editImageAsync() throws IOException, ExecutionException, InterruptedException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final EditImageRequest request = EditImageRequest.builder()
                .imageFile(new File("super_hero.png"))
                .n(5)
                .prompt("make the super hero have a cape and larger muscles")
                .responseFormat(ImageResponseFormat.URL)
                .build();

        final ClientResponse<EditImageRequest, ImageResponse> response = client.editImageAsync(request).get();

        response.getResponse().ifPresent(r -> System.out.println(r.toString()));
        response.getResponse().ifPresent(r -> System.out.println(r.getCreated()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void callCreateImageAsync() throws ExecutionException, InterruptedException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final CreateImageRequest request = CreateImageRequest.builder()
                .prompt("super hero").responseFormat(ImageResponseFormat.URL)
                .build();


        final ClientResponse<CreateImageRequest, ImageResponse> response = client.createImageAsync(request).get();

        response.getResponse().ifPresent(r -> System.out.println(r.toString()));
        response.getResponse().ifPresent(r -> System.out.println(r.getCreated()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void createVariationsImage() throws IOException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final CreateImageVariationRequest request = CreateImageVariationRequest.builder()
                .imageFile(new File("super_hero.png"))
                .n(5)
                .responseFormat(ImageResponseFormat.URL)
                .build();

        final ClientResponse<CreateImageVariationRequest, ImageResponse> response = client.createImageVariation(request);

        response.getResponse().ifPresent(r -> System.out.println(r.toString()));
        response.getResponse().ifPresent(r -> System.out.println(r.getCreated()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void editImage() throws IOException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final EditImageRequest request = EditImageRequest.builder()
                .imageFile(new File("super_hero.png"))
                .n(5)
                .prompt("make the super hero have a cape and larger muscles")
                .responseFormat(ImageResponseFormat.URL)
                .build();

        final ClientResponse<EditImageRequest, ImageResponse> response = client.editImage(request);

        response.getResponse().ifPresent(r -> System.out.println(r.toString()));
        response.getResponse().ifPresent(r -> System.out.println(r.getCreated()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void callCreateImage() throws IOException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final CreateImageRequest request = CreateImageRequest.builder()
                .prompt("super hero").responseFormat(ImageResponseFormat.URL)
                .build();

        final ClientResponse<CreateImageRequest, ImageResponse> response = client.createImage(request);

        response.getResponse().ifPresent(r -> System.out.println(r.toString()));
        response.getResponse().ifPresent(r -> System.out.println(r.getCreated()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }


    private static void callTranslate() throws IOException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final TranslateRequest request = TranslateRequest.builder()
                .model("whisper-1").prompt("translate from Spanish to English").file(new File("spanish.m4a")).responseFormat(AudioResponseFormat.VTT)
                .build();

        final ClientResponse<TranslateRequest, AudioResponse> response = client.translate(request);

        response.getResponse().ifPresent(r -> System.out.println(r.getText()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }

    private static void callTranscribe() throws IOException {
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        File file = new File("test.m4a");

        byte[] bytes = Files.readAllBytes(file.toPath());
        // Create the chat request
        final TranscriptionRequest request = TranscriptionRequest.builder()
                .model("whisper-1").prompt("Write up notes").language("en").file(bytes)
                .build();


        // Call Open AI API with chat message
        final ClientResponse<TranscriptionRequest, AudioResponse> response = client.transcribe(request);

        response.getResponse().ifPresent(r -> System.out.println(r.getBody()));

        response.getException().ifPresent(Throwable::printStackTrace);

        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

    }


    private static void callEmbeddingAsyncExample() throws Exception {


        final CountDownLatch latch = new CountDownLatch(1);
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();

        // Create the request
        final com.cloudurable.jai.model.text.embedding.EmbeddingRequest request = com.cloudurable.jai.model.text.embedding.EmbeddingRequest.builder()
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
        final com.cloudurable.jai.model.text.embedding.EmbeddingRequest request = com.cloudurable.jai.model.text.embedding.EmbeddingRequest.builder()
                .model("text-embedding-ada-002")
                .input("What is AI?")
                .build();

        // Call Open AI API with chat message
        final ClientResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> response = client.embedding(request);

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
