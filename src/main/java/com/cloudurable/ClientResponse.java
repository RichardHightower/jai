/**
 * This package contains classes and interfaces related to the Cloudurable library.
 */
package com.cloudurable;

import java.util.Optional;

/**
 * Represents a client response containing a request, response, status code, status message, and exception (if any).
 *
 * @param <Q> The type of the request.
 * @param <A> The type of the response.
 */
public interface ClientResponse<Q, A> {
    /**
     * Returns the request associated with the client response.
     *
     * @return The request associated with the client response.
     */
    Q getRequest();

    /**
     * Returns the response associated with the client response, if available.
     *
     * @return An Optional containing the response, or an empty Optional if no response is available.
     */
    Optional<A> getResponse();

    /**
     * Returns the status code associated with the client response, if available.
     *
     * @return An Optional containing the status code, or an empty Optional if no status code is available.
     */
    Optional<Integer> getStatusCode();

    /**
     * Returns the status message associated with the client response, if available.
     *
     * @return An Optional containing the status message, or an empty Optional if no status message is available.
     */
    Optional<String> getStatusMessage();

    /**
     * Returns the exception associated with the client response, if any.
     *
     * @return An Optional containing the exception, or an empty Optional if no exception is available.
     */
    Optional<Exception> getException();
}
