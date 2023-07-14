# JAI

Jai - Open AI API Java Client

Introducing Jai - a simple and efficient Java client powered by Open AI. We developed 
this client to provide a lightweight and uncomplicated option for accessing the Open AI API. With Jai, you can 
effortlessly send chat requests and receive accurate responses without unnecessary features.

# Goals

Goals for JAI include providing a simple and easy-to-use Java client for the Open AI API, without any unnecessary
extras dependencies that could make it more challenging to use. And with an as little learning curve as possible so 
no fancy new framework to learn. Additionally, JAI aims to be lightweight and fast, allowing developers to quickly 
and easily integrate it into their projects.

Here is a list of goals for this project

- minimal dependencies
- no surprises
    - Works sync or async but does not force developers either way
    - It does not force you to learn to use a new framework
    - It should work well in any Java environment
    - Easy to use idiomatic
- light-weight and small
- easy to learn

# License

Apache 2.

# Status

<aside>
👉 This is a new project and is still a work in progress.

</aside>

JAI is a new project that is still a work in progress. This means that the project is in its early stages of development 
and may not have all the features the creators are planning to implement. It also implies that there could be some bugs 
or issues that the developers are still working on fixing. Nonetheless, JAI has clear goals and objectives for the 
project, which include providing a simple and easy-to-use Java client for the Open AI API with as little learning 
curve as possible. We also aim to make it lightweight and fast, allowing developers to quickly and easily integrate
it into their projects.

JAI aims to be as straightforward as possible, with no surprises and no esoteric framework to learn. It is a minimalist 
Java library that is easy to use and requires no extra dependencies other than the JDK itself. This makes it simple to 
integrate into your projects without worrying about complex configurations or additional libraries.

### Maven

JAI only depends on JParse for JSON parsing, with no other dependencies except the JVM itself.

Here are instructions on how to include your project with Maven.

```xml
<!-- See for latest version https://mvnrepository.com/artifact/cloudurable/jparse -->
<dependency>
    <groupId>com.cloudurable</groupId>
    <artifactId>jai</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

Here are instructions on how to include your project with Gradle.

```java
//See for latest version  https://mvnrepository.com/artifact/cloudurable/jai
implementation group: 'com.cloudurable', name: 'jai', version: '1.0.0'
```

<aside>
💡 JAI has not been released yet, so it is not yet in any public maven repos.

</aside>

# What is JAI?

JAI simplifies the use of Open AI API in Java. It has no unnecessary dependencies and can be easily integrated into 
projects using Maven or Gradle. With JAI, you can make chat requests synchronously or asynchronously.

## Using JAI

### Create the client

```java
// Create the client
final OpenAIClient client = OpenAIClient.builder().setApiKey(System.getenv("OPEN_AI_KEY")).build();
```

### Chat

### Create a chat request

```java
        // Create the chat request
        final ChatRequest chatRequest = ChatRequest.builder().setModel("gpt-3.5-turbo")
                .addMessage(Message.builder().setContent("What is AI?").setRole(Role.USER).build())
                .build();
```

### Invoke the client async for chat

```java
        // Call Open AI API with chat message
client.chatAsync(chatRequest).thenAccept(response -> {
      response.getResponse().ifPresent(chatResponse -> 
                    chatResponse.getChoices().forEach(System.out::println));
      response.getException().ifPresent(Throwable::printStackTrace);
      response.getStatusMessage().ifPresent(error -> 
                  System.out.printf("status message %s %d \n", error, 
                           response.getStatusCode().orElse(0)));
});
```

### Invoke the client synchronously for chat

```java
// Call Open AI API with chat message
final ClientResponse<ChatRequest, ChatResponse> response = 
                                                      client.chat(chatRequest);

response.getResponse().ifPresent(
				chatResponse -> chatResponse.getChoices().forEach(System.out::println));
response.getException().ifPresent(Throwable::printStackTrace);
response.getStatusMessage().ifPresent(
		error -> 
			System.out.printf("status message %s %d \\n", error, 
                                      response.getStatusCode().orElse(0)))

```

### Full Chat with Function Callback example. 

As artificial intelligence and chatbots continue to gain popularity, integrating functions into chat conversations is 
becoming increasingly important. Functions are small units of code that can be reused and embedded into larger programs 
to perform a specific task. In this blog post, we will discuss how to implement and integrate functions 
into ChatGPT conversations using JAI, a Java Open AI API client. This guide will define a function, 
handle function callbacks, and mix function results with content/context returned from the function to enhance ChatGPTs’ 
response with your domain. Additionally, we will provide an example implementation of a weather-related function and its 
integration into a larger program using a function map.

This developer notebook guides implementing and integrating functions into a ChatGPT conversation with JAI, a Java Open AI API client. It covers everything from defining a function to handling function callbacks and mixing the results with the content/context returned. In addition, the guide includes an example implementation of a weather-related function and its integration into a larger program using a function map.

Checkout this [full example with Function callbacks](https://github.com/RichardHightower/jai/wiki/Using-ChatGPT-FunctionCall-from-Java). 


### Full example enhancing search with Embeddings.

As CTOs, CIOs, engineering managers, and software engineers, you have much to gain by implementing this approach 
and making your search engine more efficient.
Combining ChatGPT with retrieval and re-ranking methods, you can achieve accurate, relevant, and fast search results that will set 
you apart from your competitors. This approach also facilitates seamless integration with existing search engines, 
making it an ideal way to improve search engine performance for businesses of all sizes.

Checkout this full example [Using ChatGPT, Embeddings, and HyDE to Improve Search Results](https://github.com/RichardHightower/jai/wiki/Using-ChatGPT,-Embeddings,-and-HyDE-to-Improve-Search-Results).


Our previous developer notebook discussed how combining ChatGPT with retrieval and re-ranking methods can improve search accuracy. 
You can obtain a fast and efficient search function place by retrieving the most related content through cosine similarity
to a hypothetical answer.  By using async calls, you can even decrease the search time further, resulting in a 50% 
to 200% increase in speed! Steps 1 and 2 can be done in parallel with steps 3 and 4 using the async interface for 
Open AI API provided by JAI. In this blog, we will dive into how you can speed up your search with 
[JAI Async methods](https://github.com/RichardHightower/jai).

Checkout the full example [Speeding Up Your AI‐Powered Search with JAI Async](https://github.com/RichardHightower/jai/wiki/Speeding-Up-Your-AI%E2%80%90Powered-Search-with-JAI-Async).


---


### Next steps

| No. | Feature                             | Status | endPoint |
|-----|-------------------------------------|--------|----------|
| 0.  | Add support for chat                | DONE   | 1        |
| 1.  | Add support for completions         | DONE   | 1        |
| 2.  | Add support for edits               | DONE   | 1        |
| 3.  | Add support for embedding           | DONE   | 1        | 
| 4.  | Add support for audio to text       | DONE   | 2        | 
| 4.1 | Transcribe                          | DONE   |          | 
| 4.2 | Translate                           | DONE   |          | 
| 5.  | Add support for images              | DONE   | 3        | 
| 5.1 | Create image                        | DONE   |          | 
| 5.2 | Edit image                          | DONE   |          | 
| 5.3 | Variations image                    | DONE   |          | 
| 6.  | Add support for listing models      | DONE   | 2        | 
| 6.1 | Add support for get models          | DONE   |          | 
| 6.2 | Add support for list models         | DONE   |          | 
| 7.  | Add support for file uploads        | DONE   | 5        | 
| 7.1 | Create File                         | DONE   |          | 
| 7.2 | Delete File                         | DONE   |          | 
| 7.3 | List Files                          | DONE   |          | 
| 7.4 | Get File                            | DONE   |          | 
| 7.5 | Get File Content                    | DONE   |          | 
| 8.  | Add support for fine tunings        | DONE   | 6        | 
| 8.1 | Create Fine Tuning                  | DONE   |          | 
| 8.2 | Delete Fine Tuning                  | DONE   |          | 
| 8.3 | List Fine Tuning                    | DONE   |          | 
| 8.4 | Get Fine Tuning                     | DONE   |          | 
| 8.5 | Cancel Fine Tuning                  | DONE   |          | 
| 8.6 | List Fine Tuning Events             | DONE   |          | 
| 9.  | Add support for creating moderation | DONE   | 1        | 
| 10. | Create examples with chat streaming |        |          | 
| 11. | Create examples with chat functions | DONE   |          | 



We are at the start of this journey.



### Conclusion

JAI is a minimalist Java library for the Open AI API, designed to be simple and easy to use without any unnecessary dependencies. 
It aims to be lightweight and fast, allowing developers to quickly and easily integrate it into their projects. 
To use JAI, you can create a client and make chat requests to the Open AI API. You can include JAI in your project using 
Maven or Gradle, and use it to create and invoke chat requests synchronously or asynchronously.



## Follow up links
* [Java Open AI Client](https://www.linkedin.com/pulse/jai-java-ai-open-api-client-rick-hightower)
* [Using ChatGpt embeddings and hyde to improve search results](https://www.linkedin.com/pulse/using-chatgpt-embeddings-hyde-improve-search-results-rick-hightower)
* [Anthropics Claude Chatbot Gets Upgrade](https://www.linkedin.com/pulse/anthropics-claude-chatbot-gets-upgrade-meet-2-rick-hightower)
* [Elon Musks XAi new frontier for artificial intelligence](https://www.linkedin.com/pulse/elon-musks-xai-new-frontier-artificial-intelligence-rick-hightower)
* [Using Mockito to test JAI Java Open AI Client](https://www.linkedin.com/pulse/dev-notebook-mockito-witchcraft-jai-java-ai-open-api-client-rick)
* [Fine-tuning journey with Open AI API](https://www.linkedin.com/pulse/redefining-ai-training-unanticipated-impacts-openais-api-hightower)
* [Using Open AI to create callback functions, the basis for plugins](https://www.linkedin.com/pulse/using-chatgpt-chat-functioncall-from-java-rick-hightower)
* [Using Java Open AI Client Async](https://www.linkedin.com/pulse/speeding-up-your-ai-powered-search-jai-async-rick-hightower)
* [Fastest Java JSON Parser](https://github.com/nats-io/jparse)
* [Java Open AI API Client on GitHub](https://github.com/RichardHightower/jai)
* [Medium: Introducing Java Open AI Client](https://medium.com/@richardhightower/jai-open-ai-api-java-clientintroducing-jai-a-simple-and-efficient-java-client-powered-by-open-3b2e8a00e1a)
* [Medium: Using ChatGPT, Embeddings, and HyDE to Improve Search Results](https://medium.com/@richardhightower/using-chatgpt-embeddings-and-hyde-to-improve-search-results-5e518890026)
