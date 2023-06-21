
# Jai - Open AI API Java Client 

Jai is a small, no frills, Open AI Java Client

# Goals


# License

Apache 2.


### Maven

JAI only depends on JParse for JSON parsing no other dependencies except the JVM itself.

```xml
<!-- See for latest version https://mvnrepository.com/artifact/io.nats/jparse -->
<dependency>
    <groupId>com.cloudurable</groupId>
    <artifactId>jai</artifactId>
    <version>1.0.0</version>
</dependency>

```

### Gradle

```gradle
//See for latest version  https://mvnrepository.com/artifact/io.nats/jparse
implementation group: 'com.cloudurable', name: 'jai', version: '1.0.0'

```


# What is JAI?

JAI is a minimalist Java lib for Open AI API.

## Using JAI


#### Create the client 

```java 
        // Create the client
        final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();
```


### Chat

#### Create a chat request 

```java 

        // Create the chat request
        final ChatRequest chatRequest = ChatRequest.builder().setModel("gpt-3.5-turbo")
                .addMessage(Message.builder().setContent("What is AI?").setRole(Role.USER).build())
                .build();

```

#### Invoke the client async for chat

```java

        // Call Open AI API with chat message
        client.chatAsync(chatRequest).thenAccept(response -> {
            response.getResponse().ifPresent(chatResponse -> chatResponse.getChoices().forEach(System.out::println));
            response.getException().ifPresent(Throwable::printStackTrace);
            response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));
            latch.countDown();
        });

```

#### Invoke the client synchronously for chat

```java
        // Call Open AI API with chat message
        final ClientResponse<ChatRequest, ChatResponse> response = client.chat(chatRequest);

        response.getResponse().ifPresent(chatResponse -> chatResponse.getChoices().forEach(System.out::println));
        response.getException().ifPresent(Throwable::printStackTrace);
        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)))
```



