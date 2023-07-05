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
 *   "created": 1589478378,
 *   "data": [
 *     {
 *       "url": "https://..."
 *     },
 *     {
 *       "url": "https://..."
 *     }
 *   ]
 * }
 * </code>
 */
public class ImageResponse implements Response {

    private final Instant created;
    private final List<ImageResponseData> data;

    public ImageResponse(Instant created, List<ImageResponseData> data) {
        this.created = created;
        this.data = data;
    }

    public Instant getCreated() {
        return created;
    }

    public List<ImageResponseData> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageResponse)) return false;
        ImageResponse that = (ImageResponse) o;
        return Objects.equals(created, that.created) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, data);
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "created=" + created +
                ", data=" + data +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder(){}

        private  Instant created;
        private  List<ImageResponseData> data;

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }
        public Builder data(List<ImageResponseData> data) {
            this.data = data;
            return this;
        }
        public List<ImageResponseData> getData() {
            if (data == null) {
                data = new ArrayList<>();
            }
            return data;
        }
        public Builder add(ImageResponseData data) {
            this.getData().add(data);
            return this;
        }
        public ImageResponse build() {
            return new ImageResponse(created, getData());
        }
    }
}
