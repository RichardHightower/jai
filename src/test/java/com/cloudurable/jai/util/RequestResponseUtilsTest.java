package com.cloudurable.jai.util;

import com.cloudurable.jai.model.ClientErrorResponse;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.ClientSuccessResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;
import com.cloudurable.jai.model.audio.TranslateRequest;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.edit.EditRequest;
import com.cloudurable.jai.model.text.edit.EditResponse;
import com.cloudurable.jai.model.text.embedding.EmbeddingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSession;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RequestResponseUtilsTest {

    @Test
    public void testGetErrorResponseForChatRequest() {
        Throwable error = new RuntimeException("Some error");
        ChatRequest chatRequest = ChatRequest.builder().build();
        ClientResponse<ChatRequest, ChatResponse> response = RequestResponseUtils.getErrorResponseForChatRequest(error, chatRequest);

        assertTrue(response instanceof ClientErrorResponse);
        assertEquals(error, response.getException().get());
        assertEquals(chatRequest, response.getRequest());
    }

    @Test
    public void testGetErrorResponseForCompletionRequest() {
        Throwable error = new RuntimeException("Some error");
        CompletionRequest completionRequest = CompletionRequest.builder().build();
        ClientResponse<CompletionRequest, CompletionResponse> response = RequestResponseUtils.getErrorResponseForCompletionRequest(error, completionRequest);

        //TODO
        assertTrue(response instanceof ClientErrorResponse);
        //assertEquals(error, response.getException());
        assertEquals(completionRequest, response.getRequest());
    }

    @Test
    public void testGetCompletionResponseSuccess() {
        CompletionRequest completionRequest = CompletionRequest.builder().build();
        int statusCode = 200;
        CompletionResponse completionResponse = CompletionResponse.builder().build();
        ClientSuccessResponse<CompletionRequest, CompletionResponse> response = RequestResponseUtils.getCompletionResponseSuccess(completionRequest, statusCode, completionResponse);

        //TODO
        assertTrue(response != null);
        assertEquals(completionRequest, response.getRequest());
        //assertEquals(completionResponse, response.getResponse());
        //assertEquals(statusCode, response.getStatusCode());
    }

    @Test
    public void testGetCompletionResponseNotOk() {
        CompletionRequest completionRequest = CompletionRequest.builder().build();
        int statusCode = 400;
        String status = "Bad Request";
        ClientSuccessResponse<CompletionRequest, CompletionResponse> response = RequestResponseUtils.getCompletionResponseNotOk(completionRequest, statusCode, status);

        assertTrue(response instanceof ClientSuccessResponse);
        assertEquals(completionRequest, response.getRequest());
        //assertEquals(statusCode, response.getStatusCode());
        //assertEquals(status, response.getStatusMessage());
    }

    @Test
    public void testGetChatResponseSuccess() {
        ChatRequest chatRequest = ChatRequest.builder().build();
        int statusCode = 200;
        ChatResponse chatResponse = ChatResponse.builder().build();
        ClientSuccessResponse<ChatRequest, ChatResponse> response = RequestResponseUtils.getChatResponseSuccess(chatRequest, statusCode, chatResponse);

        //TODO
//        Assertions.assertTrue(response instanceof ClientSuccessResponse);
//        assertEquals(chatRequest, response.getRequest());
//        assertEquals(chatResponse, response.getResponse());
//        assertEquals(statusCode, response.getStatusCode());
    }

    @Test
    public void testIsOk() {
        assertTrue(RequestResponseUtils.isOk(200));
        assertTrue(RequestResponseUtils.isOk(290));
        Assertions.assertFalse(RequestResponseUtils.isOk(400));
        Assertions.assertFalse(RequestResponseUtils.isOk(500));
    }

    @Test
    public void testGetChatResponseNotOk() {
        ChatRequest chatRequest = ChatRequest.builder().build();
        int statusCode = 400;
        String status = "Bad Request";
        ClientSuccessResponse<ChatRequest, ChatResponse> response = RequestResponseUtils.getChatResponseNotOk(chatRequest, statusCode, status);

        //TODO
        //Assertions.assertTrue(response instanceof ClientSuccessResponse);
        assertEquals(chatRequest, response.getRequest());
        //assertEquals(statusCode, response.getStatusCode());
        //assertEquals(status, response.getStatusMessage());
    }

    @Test
    public void testGetChatResponse() {
        ChatRequest chatRequest = ChatRequest.builder().build();
        HttpResponse<String> response = createMockResponse(200, "{}");
        //TODO
        //ClientSuccessResponse<ChatRequest, ChatResponse> clientResponse = RequestResponseUtils.getChatResponse(chatRequest, response);

        //TOOD
        //Assertions.assertTrue(clientResponse instanceof ClientSuccessResponse);
        //assertEquals(chatRequest, clientResponse.getRequest());
        //assertEquals(200, clientResponse.getStatusCode());
    }

    //@Test TODO
    public void testGetCompletionResponse() {
        CompletionRequest completionRequest = CompletionRequest.builder().build();
        HttpResponse<String> response = createMockResponse(200, "{}");
        ClientSuccessResponse<CompletionRequest, CompletionResponse> clientResponse = RequestResponseUtils.getCompletionResponse(completionRequest, response);

        assertTrue(clientResponse instanceof ClientSuccessResponse);
        assertEquals(completionRequest, clientResponse.getRequest());
        assertEquals(200, clientResponse.getStatusCode());
    }

    private HttpResponse<String> createMockResponse(int statusCode, String body) {
        return new HttpResponse() {

            @Override
            public int statusCode() {
                return statusCode;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public Object body() {
                return body;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }

    @Test
    void testGetEmbeddingResponse() {
        com.cloudurable.jai.model.text.embedding.EmbeddingRequest embeddingRequest = com.cloudurable.jai.model.text.embedding.EmbeddingRequest.builder().model("gpt-3.5-turbo").input("Hello").build();
        HttpResponse<String> response = createMockResponse(200, "{\"object\": \"embedding\", " +
                "\"usage\": {\"prompt_tokens\": 10, \"total_tokens\": 20}, \"data\": []}");

        ClientSuccessResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> successResponse =
                RequestResponseUtils.getEmbeddingResponse(embeddingRequest, response);

        assertNotNull(successResponse);
        assertTrue(successResponse instanceof ClientSuccessResponse);
        assertEquals(embeddingRequest, successResponse.getRequest());
        assertNotNull(successResponse.getResponse());
        assertEquals(200, successResponse.getStatusCode().orElse(-666));
    }

    @Test
    void testGetEmbeddingResponseNotOk() {
        com.cloudurable.jai.model.text.embedding.EmbeddingRequest embeddingRequest = com.cloudurable.jai.model.text.embedding.EmbeddingRequest.builder().model("gpt-3.5-turbo").input("Hello").build();
        HttpResponse<String> response = createMockResponse(400, "NOPE");

        ClientSuccessResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> successResponse =
                RequestResponseUtils.getEmbeddingResponse(embeddingRequest, response);

        assertEquals(400, successResponse.getStatusCode().orElse(-666));
    }


    @Test
    void testGetEditResponseNotOk() {
        EditRequest edit = EditRequest.builder().model("gpt-3.5-turbo").input("Hello").build();
        HttpResponse<String> response = createMockResponse(400, "NOPE");

        ClientSuccessResponse<EditRequest, EditResponse> successResponse =
                RequestResponseUtils.getEditResponse(edit, response);

        assertEquals(400, successResponse.getStatusCode().orElse(-666));
    }

    @Test
    void testGetEditResponse() {
        EditRequest editRequest = EditRequest.builder().model("davinci").input("input").instruction("instruction").build();

        HttpResponse<String> response = createMockResponse(200, "{\"id\": \"123\", \"object\": \"edit\", \"choices\": [], \"created\": 1589478378, \"usage\": {\n" +
                "    \"prompt_tokens\": 25,\n" +
                "    \"completion_tokens\": 32,\n" +
                "    \"total_tokens\": 57\n" +
                "  }}");


        ClientSuccessResponse<EditRequest, EditResponse> successResponse =
                RequestResponseUtils.getEditResponse(editRequest, response);

        assertNotNull(successResponse);
        assertTrue(successResponse instanceof ClientSuccessResponse);
        assertEquals(editRequest, successResponse.getRequest());
        assertNotNull(successResponse.getResponse());
        assertEquals(200, successResponse.getStatusCode().orElse(-666));
    }

    @Test
    void testGetErrorResponseForEditRequest() {
        Throwable error = new RuntimeException("Error");
        EditRequest editRequest = EditRequest.builder().model("davinci").input("Hello")
                .instruction("instruction").build();


        ClientResponse<EditRequest, EditResponse> response =
                RequestResponseUtils.getErrorResponseForEditRequest(error, editRequest);

        assertNotNull(response);
        assertTrue(response instanceof ClientErrorResponse);
        assertEquals(error, response.getException().orElse(null));
        assertEquals(editRequest, response.getRequest());
    }

    @Test
    void testGetErrorResponseForEmbeddingRequest() {
        Throwable error = new RuntimeException("Error");
        com.cloudurable.jai.model.text.embedding.EmbeddingRequest embeddingRequest = com.cloudurable.jai.model.text.embedding.EmbeddingRequest.builder().model("gpt-3.5-turbo").input("Hello").build();

        ClientResponse<com.cloudurable.jai.model.text.embedding.EmbeddingRequest, EmbeddingResponse> response =
                RequestResponseUtils.getErrorResponseForEmbeddingRequest(error, embeddingRequest);

        assertNotNull(response);
        assertTrue(response instanceof ClientErrorResponse);
        assertEquals(error, response.getException().orElse(null));
        assertEquals(embeddingRequest, response.getRequest());
    }

    @Test
    public void testGetErrorResponseForTranslateRequest() {
        Throwable exception = new RuntimeException("Translate request failed.");
        TranslateRequest translateRequest = TranslateRequest.builder().file(new File("test.m4a")).build();

        ClientResponse<TranslateRequest, AudioResponse> response = RequestResponseUtils.getErrorResponseForTranslateRequest(exception, translateRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(exception, response.getException().orElse(null));
        Assertions.assertEquals(translateRequest, response.getRequest());
    }

    @Test
    public void testGetErrorResponseForTranscriptionRequest() {
        Throwable exception = new RuntimeException("Transcription request failed.");
        TranscriptionRequest transcriptionRequest = TranscriptionRequest.builder().file(new File("test.m4a")).build();

        ClientResponse<TranscriptionRequest, AudioResponse> response = RequestResponseUtils.getErrorResponseForTranscriptionRequest(exception, transcriptionRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(exception, response.getException().orElse(null));
        Assertions.assertEquals(transcriptionRequest, response.getRequest());
    }


    @Test
    public void testGetTranslateResponse_Success() {
        TranslateRequest translateRequest = TranslateRequest.builder().build();
        HttpResponse<String> response = createHttpResponse(200, "Translated response");

        ClientResponse<TranslateRequest, AudioResponse> clientResponse = RequestResponseUtils.getTranslateResponse(translateRequest, response);

        Assertions.assertNotNull(clientResponse);
        Assertions.assertTrue(clientResponse instanceof ClientSuccessResponse);
        Assertions.assertEquals(translateRequest, clientResponse.getRequest());

        ClientSuccessResponse<TranslateRequest, AudioResponse> successResponse = (ClientSuccessResponse<TranslateRequest, AudioResponse>) clientResponse;
        Assertions.assertEquals(200, successResponse.getStatusCode().orElse(-666));

        Assertions.assertEquals(translateRequest.getResponseFormat(), successResponse.getResponse().orElse(null).getResponseFormat());
    }

    @Test
    public void testGetTranslateResponse_Failure() {
        TranslateRequest translateRequest = TranslateRequest.builder().build();
        HttpResponse<String> response = createHttpResponse(500, "Internal Server Error");

        ClientResponse<TranslateRequest, AudioResponse> clientResponse = RequestResponseUtils.getTranslateResponse(translateRequest, response);

        Assertions.assertNotNull(clientResponse);
        Assertions.assertTrue(clientResponse instanceof ClientSuccessResponse);
        Assertions.assertEquals(translateRequest, clientResponse.getRequest());

        ClientSuccessResponse<TranslateRequest, AudioResponse> successResponse = (ClientSuccessResponse<TranslateRequest, AudioResponse>) clientResponse;
        Assertions.assertEquals(500, successResponse.getStatusCode().orElse(-666));

        Assertions.assertEquals("Internal Server Error", successResponse.getStatusMessage().orElse(""));

    }

    @Test
    public void testGetTranscriptionResponse_Success() {
        TranscriptionRequest transcriptionRequest = TranscriptionRequest.builder().build();
        HttpResponse<String> response = createHttpResponse(200, "Transcription result");

        ClientResponse<TranscriptionRequest, AudioResponse> clientResponse = RequestResponseUtils.getTranscriptionResponse(transcriptionRequest, response);

        Assertions.assertNotNull(clientResponse);
        Assertions.assertTrue(clientResponse instanceof ClientSuccessResponse);
        Assertions.assertEquals(transcriptionRequest, clientResponse.getRequest());

        ClientSuccessResponse<TranscriptionRequest, AudioResponse> successResponse = (ClientSuccessResponse<TranscriptionRequest, AudioResponse>) clientResponse;
        Assertions.assertEquals(200, successResponse.getStatusCode().orElse(-666));
    }

    @Test
    public void testGetTranscriptionResponse_Failure() {
        TranscriptionRequest transcriptionRequest = TranscriptionRequest.builder().build();
        HttpResponse<String> response = createHttpResponse(400, "Bad Request");

        ClientResponse<TranscriptionRequest, AudioResponse> clientResponse = RequestResponseUtils.getTranscriptionResponse(transcriptionRequest, response);

        Assertions.assertNotNull(clientResponse);
        Assertions.assertTrue(clientResponse instanceof ClientSuccessResponse);
        Assertions.assertEquals(transcriptionRequest, clientResponse.getRequest());

        ClientSuccessResponse<TranscriptionRequest, AudioResponse> successResponse = (ClientSuccessResponse<TranscriptionRequest, AudioResponse>) clientResponse;
        Assertions.assertEquals(400, successResponse.getStatusCode().orElse(-666));

        Assertions.assertEquals("Bad Request", successResponse.getStatusMessage().orElse(""));
    }

    // Additional helper method for creating HttpResponse

    private HttpResponse<String> createHttpResponse(int statusCode, String body) {
        return new HttpResponse<String>() {
            @Override
            public int statusCode() {
                return statusCode;
            }

            @Override
            public HttpRequest request() {
                return null;  // Not relevant for these tests
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();  // Not relevant for these tests
            }

            @Override
            public HttpHeaders headers() {
                return null;  // Not relevant for these tests
            }

            @Override
            public String body() {
                return body;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();  // Not relevant for these tests
            }

            @Override
            public URI uri() {
                return null;  // Not relevant for these tests
            }

            @Override
            public HttpClient.Version version() {
                return null;  // Not relevant for these tests
            }
        };
    }

}
