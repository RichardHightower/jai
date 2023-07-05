package com.cloudurable.jai.model.image;

import com.cloudurable.jai.model.Response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Image response.
 * <code>
 * {
 * "created": 1589478378,
 * "data": [
 * {
 * "url": "https://..."
 * },
 * {
 * "url": "https://..."
 * }
 * ]
 * }
 * </code>
 */
public class ImageResponse implements Response {

    private final Instant created;
    private final List<ImageResponseData> data;

    /**
     * Constructs an ImageResponse object with the specified created timestamp and data list.
     *
     * @param created the timestamp when the image response was created
     * @param data    the list of image response data
     */
    public ImageResponse(Instant created, List<ImageResponseData> data) {
        this.created = created;
        this.data = data;
    }

    /**
     * Returns a new Builder instance to build an ImageResponse object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the created timestamp of the image response.
     *
     * @return the created timestamp
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Returns the list of image response data.
     *
     * @return the list of image response data
     */
    public List<ImageResponseData> getData() {
        return data;
    }

    /**
     * Compares this ImageResponse to the specified object. The result is true if and only if the argument
     * is not null and is an ImageResponse object that has the same created timestamp and data as this object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageResponse)) return false;
        ImageResponse that = (ImageResponse) o;
        return Objects.equals(created, that.created) && Objects.equals(data, that.data);
    }

    /**
     * Returns the hash code value for this ImageResponse.
     *
     * @return the hash code value for this ImageResponse
     */
    @Override
    public int hashCode() {
        return Objects.hash(created, data);
    }

    /**
     * Returns a string representation of the ImageResponse object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "ImageResponse{" +
                "created=" + created +
                ", data=" + data +
                '}';
    }

    /**
     * A builder class for ImageResponse.
     */
    public static class Builder {
        private Instant created;
        private List<ImageResponseData> data;

        private Builder() {
        }

        /**
         * Sets the created timestamp for the ImageResponse being built.
         *
         * @param created the created timestamp
         * @return the Builder instance
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets the list of image response data for the ImageResponse being built.
         *
         * @param data the list of image response data
         * @return the Builder instance
         */
        public Builder data(List<ImageResponseData> data) {
            this.data = data;
            return this;
        }

        /**
         * Returns the list of image response data. If the listis null, it creates a new empty list before returning it.
         *
         * @return the list of image response data
         */
        public List<ImageResponseData> getData() {
            if (data == null) {
                data = new ArrayList<>();
            }
            return data;
        }

        /**
         * Adds an image response data to the list of image response data being built.
         *
         * @param data the image response data to add
         * @return the Builder instance
         */
        public Builder add(ImageResponseData data) {
            this.getData().add(data);
            return this;
        }

        /**
         * Builds a new ImageResponse object with the configured parameters.
         *
         * @return a new ImageResponse object
         */
        public ImageResponse build() {
            return new ImageResponse(created, getData());
        }
    }
}
