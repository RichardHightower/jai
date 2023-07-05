package com.cloudurable.jai.model.image;

import com.cloudurable.jai.model.Request;

import java.util.Objects;

public abstract class ImageRequest implements Request {

    /**
     *
     n integer
     Optional
     Defaults to 1
     The number of images to generate. Must be between 1 and 10.
     */
    private final int n;

    /**
     *     size string
     *     Optional
     *     Defaults to 1024x1024
     *     The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024.
     */
    private final ImageSize size;

    /**
     * response_format string
     *
     *      Optional
     *     Defaults to url
     *     The format in which the generated images are returned. Must be one of url or b64_json.
     */
    private final ImageResponseFormat responseFormat;

    /**
     *
     user
     string
     Optional
     A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. Learn more.
     */
    private final String user;

    public ImageRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user) {
        this.n = n;
        this.size = size;
        this.responseFormat = responseFormat;
        this.user = user;
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

    public int getN() {
        return n;
    }

    public ImageSize getSize() {
        return size;
    }

    public ImageResponseFormat getResponseFormat() {
        return responseFormat;
    }

    public String getUser() {
        return user;
    }

}
