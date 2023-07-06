package com.cloudurable.jai.model;

import java.util.Optional;

/**
 * Represents a client success response containing a request, response, status code, and status message.
 *
 * @param <Q> The type of the request.
 * @param <A> The type of the response.
 */
public class ClientSuccessResponse<Q, A> implements ClientResponse<Q, A> {

    private final Q request;
    private final A response;
    private final int statusCode;
    private final String statusMessage;

    /**
     * Constructs a ClientSuccessResponse object.
     *
     * @param request       The request associated with the client success response.
     * @param response      The response associated with the client success response.
     * @param statusCode    The status code associated with the client success response.
     * @param statusMessage The status message associated with the client success response.
     */
    public ClientSuccessResponse(Q request, A response, int statusCode, String statusMessage) {
        this.request = request;
        this.response = response;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    /**
     * Creates a new instance of the Builder for ClientSuccessResponse.
     *
     * @param <Q> The type of the request.
     * @param <A> The type of the response.
     * @return A new instance of the Builder for ClientSuccessResponse.
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
     * Returns the response associated with the client response, if available.
     *
     * @return An Optional containing the response, or an empty Optional if no response is available.
     */
    @Override
    public Optional<A> getResponse() {
        return Optional.ofNullable(response);
    }

    /**
     * Returns the status code associated with the client response, if available.
     *
     * @return An Optional containing the status code, or an empty Optional if no status code is available.
     */
    @Override
    public Optional<Integer> getStatusCode() {
        return Optional.ofNullable(statusCode == 0 ? null : statusCode);
    }

    /**
     * Returns the status message associated with the client response, if available.
     *
     * @return An Optional containing the status message, or an empty Optional if no status message is available.
     */
    @Override
    public Optional<String> getStatusMessage() {
        return Optional.ofNullable(statusMessage);
    }

    /**
     * Returns an empty Optional since there is no exception in a client success response.
     *
     * @return An empty Optional.
     */
    @Override
    public Optional<Throwable> getException() {
        return Optional.empty();
    }


    @Override
    public String toString() {
        return "ClientSuccessResponse{" +
                "request=" + request +
                ", response=" + response +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }

    /**
     * Builder pattern for constructing ClientSuccessResponse objects.
     *
     * @param <Q> The type of the request.
     * @param <A> The type of the response.
     */
    public static class Builder<Q, A> {
        private Q request;
        private A response;
        private int statusCode;
        private String statusMessage;

        private Builder() {
        }

        /**
         * Sets the request associated with the client success response.
         *
         * @param request The request associated with the client success response.
         * @return The Builder instance.
         */
        public Builder<Q, A> request(Q request) {
            this.request = request;
            return this;
        }

        /**
         * Sets the response associated with the client success response.
         *
         * @param response The response associated with the client success response.
         * @return The Builder instance.
         */
        public Builder<Q, A> response(A response) {
            this.response = response;
            return this;
        }

        /**
         * Sets the status code associated with the client success response.
         *
         * @param statusCode The status code associated with the client success response.
         * @return The Builder instance.
         */
        public Builder<Q, A> statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * Sets the status message associated with the client success response.
         *
         * @param statusMessage The status message associated with the client success response.
         * @return The Builder instance.
         */
        public Builder<Q, A> statusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        /**
         * Builds a ClientSuccessResponse object.
         *
         * @return The constructed ClientSuccessResponse object.
         */
        public ClientSuccessResponse<Q, A> build() {
            return new ClientSuccessResponse<>(request, response, statusCode, statusMessage);
        }
    }
}
