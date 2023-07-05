package com.cloudurable.jai.model.image;

import com.cloudurable.jai.model.Request;

import java.util.Objects;

/**
 * Abstract class representing an image request.
 * Implements the Request interface.
 */
public abstract class ImageRequest implements Request {

    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    private final int n;

    /**
     * The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024.
     */
    private final ImageSize size;

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     */
    private final ImageResponseFormat responseFormat;

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    private final String user;

    /**
     * Constructs an ImageRequest object with the specified parameters.
     *
     * @param n              The number of images to generate.
     * @param size           The size of the generated images.
     * @param responseFormat The format in which the generated images are returned.
     * @param user           The user identifier.
     */
    public ImageRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user) {
        this.n = n;
        this.size = size;
        this.responseFormat = responseFormat;
        this.user = user;
    }

    /**
     * Checks if the chat request should be streamed.
     *
     * @return true if the chat request should be streamed, false otherwise
     */
    public int getN() {
        return n;
    }

    /**
     * Gets the size of the generated images.
     *
     * @return the size of the generated images
     */
    public ImageSize getSize() {
        return size;
    }

    /**
     * Gets the response format for the generated images.
     *
     * @return the response format for the generated images
     */
    public ImageResponseFormat getResponseFormat() {
        return responseFormat;
    }

    /**
     * Gets the user identifier associated with the request.
     *
     * @return the user identifier associated with the request
     */
    public String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageRequest)) return false;
        ImageRequest that = (ImageRequest) o;
        return n == that.n && size == that.size && responseFormat == that.responseFormat && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, size, responseFormat, user);
    }

    @Override
    public String toString() {
        return "ImageRequest{" +
                "n=" + n +
                ", size=" + size +
                ", responseFormat=" + responseFormat +
                ", user='" + user + '\'' +
                '}';
    }
}
