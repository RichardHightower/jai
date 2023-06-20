package com.cloudurable.impl;

import com.cloudurable.ClientResponse;

import java.util.Optional;

/**
 * Represents a client error response containing a request and an exception.
 *
 * @param <Q> The type of the request.
 * @param <A> The type of the response.
 */
public class ClientErrorResponse<Q, A> implements ClientResponse<Q, A> {

    private final Q request;
    private final Exception exception;

    /**
     * Constructs a ClientErrorResponse object.
     *
     * @param request   The request associated with the client error response.
     * @param exception The exception associated with the client error response.
     */
    public ClientErrorResponse(Q request, Exception exception) {
        this.request = request;
        this.exception = exception;
    }

    /**
     * Creates a new instance of the Builder for ClientErrorResponse.
     *
     * @param <Q> The type of the request.
     * @param <A> The type of the response.
     * @return A new instance of the Builder for ClientErrorResponse.
     */
    public static <Q, A> Builder<Q, A> builder() {
        return new Builder<>();
    }

    /**
     * Returns the request associated with the client response.
     *
     * @return The request associated with the client response.
     */
    @Override
    public Q getRequest() {
        return request;
    }

    /**
     * Returns an empty Optional since there is no response in a client error response.
     *
     * @return An empty Optional.
     */
    @Override
    public Optional<A> getResponse() {
        return Optional.empty();
    }

    /**
     * Returns an empty Optional since there is no status code in a client error response.
     *
     * @return An empty Optional.
     */
    @Override
    public Optional<Integer> getStatusCode() {
        return Optional.empty();
    }

    /**
     * Returns an empty Optional since there is no status message in a client error response.
     *
     * @return An empty Optional.
     */
    @Override
    public Optional<String> getStatusMessage() {
        return Optional.empty();
    }

    /**
     * Returns the exception associated with the client error response.
     *
     * @return An Optional containing the exception.
     */
    @Override
    public Optional<Exception> getException() {
        return Optional.of(exception);
    }

    /**
     * Builder pattern for constructing ClientErrorResponse objects.
     *
     * @param <Q> The type of the request.
     * @param <A> The type of the response.
     */
    public static class Builder<Q, A> {
        private Q request;
        private Exception exception;

        private Builder() {
        }

        /**
         * Sets the request associated with the client error response.
         *
         * @param request The request associated with the client error response.
         * @return The Builder instance.
         */
        public Builder<Q, A> setRequest(Q request) {
            this.request = request;
            return this;
        }

        /**
         * Sets the exception associated with the client error response.
         *
         * @param exception The exception associated with the client error response.
         * @return The Builder instance.
         */
        public Builder<Q, A> setException(Exception exception) {
            this.exception = exception;
            return this;
        }

        /**
         * Builds a ClientErrorResponse object.
         *
         * @return The constructed ClientErrorResponse object.
         */
        public ClientResponse<Q, A> build() {
            return new ClientErrorResponse<>(request, exception);
        }
    }
}
