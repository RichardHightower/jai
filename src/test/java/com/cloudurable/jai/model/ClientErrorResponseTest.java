package com.cloudurable.jai.model;


import com.cloudurable.jai.model.text.completion.CompletionRequest;
import com.cloudurable.jai.model.text.completion.CompletionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientErrorResponseTest {

    @Test
    public void testClientErrorResponse() {
        // Create a sample request and exception
        CompletionRequest request = CompletionRequest.builder().build();
        Throwable exception = new RuntimeException("Sample Exception");

        // Create a ClientErrorResponse using the builder
        ClientErrorResponse.Builder<CompletionRequest, CompletionResponse> builder = ClientErrorResponse.builder();

        ClientResponse<CompletionRequest, CompletionResponse> errorResponse = builder.

                exception(exception).request(request)
                .build();

        // Verify the attributes of the ClientErrorResponse
        Assertions.assertEquals(request, errorResponse.getRequest());
        Assertions.assertEquals(exception, errorResponse.getException().orElse(null));
        Assertions.assertFalse(errorResponse.getResponse().isPresent());
        Assertions.assertFalse(errorResponse.getStatusCode().isPresent());
        Assertions.assertFalse(errorResponse.getStatusMessage().isPresent());
    }
}
