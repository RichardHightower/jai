package com.cloudurable.jai.util;

import com.cloudurable.jai.model.ClientErrorResponse;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.ClientSuccessResponse;
import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestResponseUtilsTest {

    @Test
    public void testGetErrorResponseForChatRequest() {
        Throwable error = new RuntimeException("Some error");
        ChatRequest chatRequest = ChatRequest.builder().build();
        ClientResponse<ChatRequest, ChatResponse> response = RequestResponseUtils.getErrorResponseForChatRequest(error, chatRequest);

        Assertions.assertTrue(response instanceof ClientErrorResponse);
        assertEquals(error, response.getException().get());
        assertEquals(chatRequest, response.getRequest());
    }

    @Test
    public void testGetErrorResponseForCompletionRequest() {
        Throwable error = new RuntimeException("Some error");
        CompletionRequest completionRequest = CompletionRequest.builder().build();
        ClientResponse<CompletionRequest, CompletionResponse> response = RequestResponseUtils.getErrorResponseForCompletionRequest(error, completionRequest);

        //TODO
        Assertions.assertTrue(response instanceof ClientErrorResponse);
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
        Assertions.assertTrue(response != null);
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

        Assertions.assertTrue(response instanceof ClientSuccessResponse);
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
        Assertions.assertTrue(RequestResponseUtils.isOk(200));
        Assertions.assertTrue(RequestResponseUtils.isOk(290));
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

        Assertions.assertTrue(clientResponse instanceof ClientSuccessResponse);
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
}
