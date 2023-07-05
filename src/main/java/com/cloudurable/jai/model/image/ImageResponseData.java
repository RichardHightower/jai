package com.cloudurable.jai.model.image;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the data of an image response.
 */
public class ImageResponseData {
    private final URI url;

    /**
     * Constructs an ImageResponseData object with the specified URL.
     *
     * @param url The URL of the image.
     */
    public ImageResponseData(URI url) {
        this.url = url;
    }

    /**
     * Returns the URL of the image.
     *
     * @return The URL of the image wrapped in an Optional.
     */
    public Optional<URI> getUrl() {
        return Optional.ofNullable(url);
    }

    /**
     * Returns the string representation of the ImageResponseData object.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "ImageResponseData{" +
                "url=" + url +
                '}';
    }

    /**
     * Checks if the ImageResponseData object is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageResponseData)) return false;
        ImageResponseData that = (ImageResponseData) o;
        return Objects.equals(url, that.url);
    }

    /**
     * Returns the hash code of the ImageResponseData object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
