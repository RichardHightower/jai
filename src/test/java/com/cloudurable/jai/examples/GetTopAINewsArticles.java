package com.cloudurable.jai.examples;

import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;
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
import java.util.List;
import java.util.stream.Collectors;

public class GetTopAINewsArticles {

    private static final String QUERIES_INPUT =  "Generate an array of search queries that are relevant to this question.\n" +
            "Use a variation of related keywords for the queries, trying to be as general as possible.\n" +
            "Include as many queries as you can think of, including and excluding terms.\n" +
            "For example, include queries like ['keyword_1 keyword_2', 'keyword_1', 'keyword_2'].\n" +
            "Be creative. The more queries you include, the more likely you are to find relevant results.\n" +
            "\n" +
            "User question: {USER_QUESTION}\n" +
            "\n" +
            "Format: {{\"queries\": [\"query_1\", \"query_2\", \"query_3\"]}}";
    public static void main(String... args) {
        try {

            final var client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

            final var chatRequest = ChatRequest.builder()
                    .addMessage(Message.builder().role(Role.SYSTEM).content("All output shall be JSON").build())
                    .addMessage(Message.builder().role(Role.USER).content(QUERIES_INPUT.replace("{USER_QUESTION}",
                            "What is Tesla new AI initiative?")).build())
                    .build();

            final var chat = client.chat(chatRequest);

            if (chat.getResponse().isPresent()) {
                String queriesJson = chat.getResponse().get().getChoices().get(0).getMessage().getContent();
                System.out.println(queriesJson);
                List<String> queries = JsonParserBuilder.builder().build().parse(queriesJson).getObjectNode().getArrayNode("queries")
                        .filter(node -> node instanceof StringNode).stream().map(Object::toString).collect(Collectors.toList());
                List<ArrayNode> results = queries.subList(0, 10).stream().map(GetTopAINewsArticles::searchNews).collect(Collectors.toList());

                results.forEach(arrayNode -> {
                    arrayNode.forEach(on -> {
                        final var node = on.asCollection().asObject();
                        handleArticle(node);
                    });
                });
            } else {
                System.out.println("" + chat.getStatusCode().orElse(666) + "" + chat.getStatusMessage().orElse(""));
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void handleArticle(ObjectNode node) {
        System.out.println("# " + node.getString("title"));

        if (node.get("author") != null) {
            System.out.println("#### author: " + node.getString("author"));
        }

        final var client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();


        final var chatRequest = ChatRequest.builder()
                .addMessage(Message.builder().role(Role.SYSTEM).content("All output shall be Markdown").build())
                .addMessage(Message.builder().role(Role.USER).content("First Summarize this article to less than 500 words while " +
                        "getting all of the main points. Also Extract the author, publication, publication date, and link to the article ||| " + node).build())
                .addMessage(Message.builder().role(Role.USER).content("Then create 10 high level bullet points from the article").build())
                .build();

        ClientResponse<ChatRequest, ChatResponse> chat = client.chat(chatRequest);
        if (chat.getResponse().isPresent()) {


            System.out.println(chat.getResponse().get().getChoices().get(0).getMessage().getContent());
        } else {
            System.out.println("" + chat.getStatusCode().orElse(666) + "" + chat.getStatusMessage().orElse(""));
        }

        System.out.println("------");


    }

    public static String dateStr(Instant instant) {
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'yyyy-MM-dd'");
        return localDate.format(formatter);
    }

    public static ArrayNode searchNews(final String query) {
        final var end = Instant.now();
        final var start = end.minus(java.time.Duration.ofDays(5));
        return searchNews(query, start, end, 5);
    }

    public static ArrayNode searchNews(final String query, final Instant start, final Instant end, final int pageSize) {
        System.out.println(query);
        try {

            String url = "https://newsapi.org/v2/everything?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                    + "&apiKey=" + System.getenv("NEWS_API_KEY") + "&language=en" + "&sortBy=relevancy"
                    + "&from=" + dateStr(start) + "&to=" + dateStr(end) + "&pageSize=" + pageSize;

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().uri(URI.create(url))
                    .GET().setHeader("Content-Type", "application/json").build(), HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 299) {
                return JsonParserBuilder.builder().build().parse(response.body()).atPath("articles").asCollection().asArray();
            } else {
                throw new IllegalStateException(" status code " + response.statusCode() + " " + response.body());
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

    }


}
