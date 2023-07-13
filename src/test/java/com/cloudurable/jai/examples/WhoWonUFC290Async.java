package com.cloudurable.jai.examples;

import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;
import com.cloudurable.jai.model.text.embedding.Embedding;
import com.cloudurable.jai.model.text.embedding.EmbeddingRequest;
import io.nats.jparse.Json;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.node.StringNode;
import io.nats.jparse.parser.JsonParserBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WhoWonUFC290Async {

    private static final String QUERIES_INPUT = "Generate an array of search queries that are "
            + "relevant to this question.\n" +
            "Use a variation of related keywords for the queries, trying to be as general as possible.\n"
            + "Include as many queries as you can think of, including and excluding terms.\n"
            + "For example, include queries like ['keyword_1 keyword_2', 'keyword_1', 'keyword_2'].\n"
            + "Be creative. The more queries you include, the more likely you are to find relevant results.\n"
            + "\n" + "User question: {USER_QUESTION}\n" + "\n" + "Format: {{\"queries\": [\"query_1\", \"query_2\", \"query_3\"]}}";
    private static final String HA_INPUT = "Generate a hypothetical answer to the user's question. "
            + "This answer will be used to rank search results. \n"
            + "Pretend you have all the information you need to answer, but don't use any actual facts. "
            + "Instead, use placeholders\n" + "like NAME did something, or NAME said something at PLACE. \n"
            + "\n" + "User question: {USER_QUESTION}\n" + "\n"
            + "Format: {{\"hypotheticalAnswer\": \"hypothetical answer text\"}}";
    private static final String USER_QUESTION = "Who won Main card fights in UFC 290? Tell us who won. "
            + "Are there any new champions? Where are they from?";
    private static final String ANSWER_INPUT = "Generate an answer to the user's question "
            + "based on the given search results. \n" + "TOP_RESULTS: {formatted_top_results}\n"
            + "USER_QUESTION: {USER_QUESTION}\n" + "\n"
            + "Include as much information as possible in the answer. Reference the "
            + "relevant search result urls as markdown links";

    public static CompletableFuture<String> jsonGPT(String input) {
        return jsonGPT(input, "All output shall be JSON");
    }

    public static CompletableFuture<String> jsonGPT(String input, String system) {

        final var client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final var chatRequest = ChatRequest.builder().addMessage(Message.builder().role(Role.SYSTEM).content(system).build()).addMessage(Message.builder().role(Role.USER).content(input).build()).build();

        return client.chatAsync(chatRequest).thenApply(chat -> {
            if (chat.getResponse().isPresent()) {
                return chat.getResponse().get().getChoices().get(0).getMessage().getContent();
            } else {
                System.out.println(chat.getStatusCode().orElse(666) + " " + chat.getStatusMessage().orElse(""));
                throw new IllegalStateException();
            }
        });
    }

    public static CompletableFuture<String> hypotheticalAnswer() {
        final var input = HA_INPUT.replace("{USER_QUESTION}", USER_QUESTION);
        return jsonGPT(input).thenApply(response -> JsonParserBuilder.builder().build().parse(response).getObjectNode().getString("hypotheticalAnswer"));
    }

    public static CompletableFuture<float[]> embeddingsAsync(String input) {
        System.out.println("INPUT " + input);
        return embeddingsAsync(List.of(input)).thenApply(embeddings -> embeddings.get(0));
    }

    public static CompletableFuture<List<float[]>> embeddingsAsync(List<String> input) {
        System.out.println("INPUT " + input);

        if (input == null || input.size() == 0) {
            return CompletableFuture.completedFuture(Collections.singletonList(new float[0]));
        }

        final var client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
        return client.embeddingAsync(EmbeddingRequest.builder().model("text-embedding-ada-002").input(input).build()).thenApply(embedding -> {
            if (embedding.getResponse().isPresent()) {
                return embedding.getResponse().get().getData().stream().map(Embedding::getEmbedding).collect(Collectors.toList());
            } else {
                System.out.println(embedding.getStatusCode().orElse(666) + " " + embedding.getStatusMessage().orElse(""));
                throw new IllegalStateException(embedding.getStatusCode().orElse(666) + " " + embedding.getStatusMessage().orElse(""));
            }
        });

    }

    private static Object getArticleContent(ObjectNode article) {
        String content = article.getString("content");
        if (content.length() < 250) {
            return content;
        } else {
            return content.substring(0, 250);
        }
    }

    public static float dot(float[] v1, float[] v2) {
        assert v1.length == v2.length;
        float result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += v1[i] * v2[i];
        }
        return result;
    }

    public static String dateStr(Instant instant) {
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'yyyy-MM-dd'");
        return localDate.format(formatter);
    }

    public static CompletableFuture<ArrayNode> searchNews(final String query) {
        System.out.println("searchNews " + query);
        final var end = Instant.now();
        final var start = end.minus(java.time.Duration.ofDays(5));
        return searchNewsFunc(query, start, end, 5);
    }

    public static CompletableFuture<ArrayNode> searchNewsFunc(final String query, final Instant start, final Instant end, final int pageSize) {
        System.out.println(query);
        try {

            String url = "https://newsapi.org/v2/everything?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                    + "&apiKey=" + System.getenv("NEWS_API_KEY") + "&language=en" + "&sortBy=relevancy"
                    + "&from=" + dateStr(start) + "&to=" + dateStr(end) + "&pageSize=" + pageSize;

            HttpClient httpClient = HttpClient.newHttpClient();
            return httpClient.sendAsync(HttpRequest.newBuilder().uri(URI.create(url))
                            .GET().setHeader("Content-Type", "application/json").build(),
                    HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
                if (response.statusCode() >= 200 && response.statusCode() < 299) {
                    return JsonParserBuilder.builder().build().parse(response.body()).atPath("articles").asCollection().asArray();
                } else {
                    throw new IllegalStateException(" status code " + response.statusCode() + " " + response.body());
                }
            });

        } catch (Exception ex) {
            return CompletableFuture.failedFuture(ex);
        }
    }

    public static void main(String... args)  {
        try {

            long startTime = System.currentTimeMillis();
            final CountDownLatch countDownLatch = new CountDownLatch(2);

            // Generating a hypothetical answer and its embedding
            final var hypotheticalAnswerEmbeddingFuture = hypotheticalAnswer()
                    .thenCompose(WhoWonUFC290Async::embeddingsAsync).thenApply(floats -> {
                        countDownLatch.countDown();
                        return floats;
                    });

            // Generate a list of queries and use them to look up articles.
            final var queriesFuture = jsonGPT(QUERIES_INPUT.replace("{USER_QUESTION}", USER_QUESTION))
                    .thenApply(queriesJson ->
                            JsonParserBuilder.builder().build().parse(queriesJson)
                                    .getObjectNode().getArrayNode("queries")
                                    .filter(node -> node instanceof StringNode)
                                    .stream().map(Object::toString).collect(Collectors.toList())
                    ).thenCompose(WhoWonUFC290Async::getArticles
                    ).thenApply(objectNodes -> {
                        countDownLatch.countDown();
                        return objectNodes;
                    });

            if (!countDownLatch.await(30, TimeUnit.SECONDS))
                throw new TimeoutException("Timed out waiting for hypotheticalAnswerEmbedding and " +
                        " articles ");

            final var articles = queriesFuture.get();

            final var hypotheticalAnswerEmbedding = hypotheticalAnswerEmbeddingFuture.get();


            // Extracting article content and generating embeddings for each article
            final var articleContent = articles.stream().map(article ->
                            String.format("%s %s %s", article.getString("title"),
                                    article.getString("description"), article.getString("content").substring(0, 100)))
                    .collect(Collectors.toList());
            final var articleEmbeddings = embeddingsAsync(articleContent).get();

            // Calculating cosine similarities between the hypothetical answer embedding and article embeddings
           final var cosineSimilarities = articleEmbeddings.stream()
                    .map(articleEmbedding -> dot(hypotheticalAnswerEmbedding, articleEmbedding))
                    .collect(Collectors.toList());

            // Creating a set of scored articles based on cosine similarities
            final var articleSet = IntStream.range(0,
                            Math.min(cosineSimilarities.size(), articleContent.size()))
                    .mapToObj(i -> new ScoredArticle(articles.get(i), cosineSimilarities.get(i)))
                    .collect(Collectors.toSet());


            final var sortedArticles = new ArrayList<>(articleSet);
            sortedArticles.sort((o1, o2) -> Float.compare(o2.getScore(), o1.getScore()));

            // Printing the top 5 scored articles
            sortedArticles.subList(0, 5).forEach(System.out::println);

            // Formatting the top results as JSON strings
            final var formattedTopResults = String.join(",\n", sortedArticles.stream()
                    .map(ScoredArticle::getContent)
                    .map(article -> String.format(Json.niceJson("{'title':'%s', 'url':'%s'," +
                                    " 'description':'%s', 'content':'%s'}\n"),
                            article.getString("title"), article.getString("url"),
                            article.getString("description"),
                            getArticleContent(article))).collect(Collectors.toList()).subList(0, 10));

            System.out.println(formattedTopResults);

            // Generating the final answer with the formatted top results
            final var finalAnswer = jsonGPT(ANSWER_INPUT.replace("{USER_QUESTION}", USER_QUESTION)
                    .replace("{formatted_top_results}", formattedTopResults), "Output format is markdown").get();
            System.out.println(finalAnswer);

            long endTime = System.currentTimeMillis();

            System.out.println(endTime - startTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static CompletableFuture<List<ObjectNode>> getArticles(List<String> queries) {
        final CompletableFuture<List<ObjectNode>> completableFuture = new CompletableFuture<>();
        final CountDownLatch countDownLatch = new CountDownLatch(queries.size());
        final LinkedTransferQueue<ObjectNode> results = new LinkedTransferQueue<>();

        final List<CompletableFuture<ArrayNode>> queryFutures = queries.stream()
                .map(WhoWonUFC290Async::searchNews).collect(Collectors.toList());

        final ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            for (CompletableFuture<ArrayNode> future : queryFutures) {
                try {
                    ArrayNode arrayNode = future.get();
                    arrayNode.forEach(node -> results.add((ObjectNode) node));
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                    countDownLatch.countDown();
                }
            }
        });

        executorService.submit(() -> {
            try {
                if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
                    throw new TimeoutException("Timed out waiting for articles");
                }

                final var list = new ArrayList<ObjectNode>();
                if (list.addAll(results)) {
                    completableFuture.complete(list);
                } else {
                    completableFuture.complete(list);
                }

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);

            } finally {
                executorService.shutdown();
            }
        });
        return completableFuture;
    }


    public static class ScoredArticle {
        private final ObjectNode content;
        private final float score;

        public ScoredArticle(ObjectNode content, float score) {
            this.content = content;
            this.score = score;
        }

        public ObjectNode getContent() {
            return content;
        }

        public float getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "ScoredArticle{" + "content='" + content.getString("title") + '\'' + ", score=" + score + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ScoredArticle)) return false;
            ScoredArticle that = (ScoredArticle) o;
            return Float.compare(that.score, score) == 0 && Objects.equals(content.getString("title"), that.content.getString("title"));
        }

        @Override
        public int hashCode() {
            return Objects.hash(content.getString("title"), score);
        }
    }
}







