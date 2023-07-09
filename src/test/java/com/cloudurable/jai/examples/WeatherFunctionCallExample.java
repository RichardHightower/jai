package com.cloudurable.jai.examples;

import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;
import com.cloudurable.jai.model.text.completion.chat.function.*;
import com.cloudurable.jai.util.JsonSerializer;
import io.nats.jparse.node.ObjectNode;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class WeatherFunctionCallExample {

    private final OpenAIClient client;

    /**
     * Holds the function mappings.
     */
    private final Map<String, Function<ObjectNode, String>> functionMap = new HashMap<>();

    public WeatherFunctionCallExample(OpenAIClient client) {
        this.client = client;
        functionMap.put("get_current_weather", this::getCurrentWeather);
    }

    /**
     *
     # Example dummy function hard coded to return the same weather for two cities and a default for other cities.
     # In production, this could be your backend API or an external API
     * @param objectNode arguments from chat GPT.
     * @return JSON string
     */
    public  String getCurrentWeather(final ObjectNode objectNode) {
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
    }


    public static void main(final String... args) {
        try {
            final var client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();
            new WeatherFunctionCallExample(client).runConversation();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void runConversation() {




        // Call the ChatGPT chat with the user query.
        final var message = Message.builder().role(Role.USER)
                .content("What's the weather like in Boston in fahrenheit?").build();


        // And include the set of functions defined in the functions parameter.
        final var getCurrentWeatherFunc = getFunctionDefinition();
        final var chatBuilder = ChatRequest.builder()
                .model("gpt-3.5-turbo-0613")
                .addMessage(message) // user query
                .addFunction(getCurrentWeatherFunc) //function definitions
                .functionalCall(ChatRequest.AUTO);


        final var chatRequest = chatBuilder.build();
        final var chatResponse = client.chat(chatRequest);

        chatResponse.getResponse().ifPresent(response -> handleFunctionCallback(chatBuilder, response));

        System.out.println(chatResponse.getStatusCode().orElse(666));
        System.out.println(chatResponse.getStatusMessage().orElse(""));
        chatResponse.getException().ifPresent(e-> e.printStackTrace());
    }

    private void handleFunctionCallback(final ChatRequest.Builder chatBuilder, final ChatResponse chatResponse) {
        var responseMessage = chatResponse.getChoices().get(0).getMessage();
        var functionCall = responseMessage.getFunctionCall();

        var functionResponse = getFunctionResponse(functionCall);

        chatBuilder.addMessage(Message.builder().name(functionCall.getName()).role(Role.FUNCTION)
                    .content(functionResponse)
                    .build());

       var response = client.chat(chatBuilder.build());

        response.getResponse().ifPresent(chatResponse1 ->
                System.out.println(chatResponse1.getChoices().get(0).getMessage().getContent()));

        if (response.getStatusMessage().isPresent()) {
            System.out.println(response.getStatusMessage().orElse(""));
        }
    }


    private String getFunctionResponse(FunctionalCall functionCall) {
        String functionResponse = "";
        if (functionCall !=null && functionMap.containsKey(functionCall.getName())) {
            functionResponse = functionMap.get(functionCall.getName()).apply(functionCall.getArguments());
        }
        return functionResponse;
    }

    private static FunctionDef getFunctionDefinition() {
        return FunctionDef.builder().name("get_current_weather")
                .description("Get the current weather in a given location")
                .setParameters(
                    ObjectParameter.objectParamBuilder()
                        .addParameter(Parameter.builder().name("location")
                                .description("The city and state, e.g. Austin, TX").build())
                        .addParameter(
                                EnumParameter.enumBuilder()
                                        .name("unit").enumValues("celsius", "fahrenheit").build())
                            .required("location")
                        .build()
        ).build();
    }
}
