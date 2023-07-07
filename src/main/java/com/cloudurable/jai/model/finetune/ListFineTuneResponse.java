package com.cloudurable.jai.model.finetune;

import com.cloudurable.jai.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a response containing a list of files.
 * Implements the Response interface.
 */
public class ListFineTuneResponse implements Response {

    private final String object;
    private final List<FineTuneData> data;

    /**
     * Constructs a new FileListResponse with the specified object and data.
     *
     * @param object the object associated with the response
     * @param data   the list of file data
     */
    public ListFineTuneResponse(String object, List<FineTuneData> data) {
        this.object = object;
        this.data = data;
    }

    /**
     * Returns a new instance of the Builder for constructing FileListResponse objects.
     *
     * @return a new instance of the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the object associated with the response.
     *
     * @return the object associated with the response
     */
    public String getObject() {
        return object;
    }

    /**
     * Returns the list of file data.
     *
     * @return the list of file data
     */
    public List<FineTuneData> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "FileListResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListFineTuneResponse)) return false;
        ListFineTuneResponse that = (ListFineTuneResponse) o;
        return Objects.equals(object, that.object) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, data);
    }

    /**
     * The Builder class provides methods for constructing FileListResponse objects.
     */
    public static class Builder {

        private String object;
        private List<FineTuneData> data;

        /**
         * Sets the object associated with the response.
         *
         * @param object the object associated with the response
         * @return the builder instance
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the list of file data.
         *
         * @param data the list of file data
         * @return the builder instance
         */
        public Builder data(List<FineTuneData> data) {
            this.data = data;
            return this;
        }

        /**
         * Returns the list of file data. If the data is null, it creates a new empty list.
         *
         * @return the list of file data
         */
        public List<FineTuneData> getData() {
            if (data == null) {
                data = new ArrayList<>();
            }
            return data;
        }

        /**
         * Adds the specified file data to the list of file data.
         *
         * @param data the file data to add
         * @return the builder instance
         */
        public Builder addData(FineTuneData data) {
            this.getData().add(data);
            return this;
        }

        /**
         * Builds a new instance of FileListResponse using the configured values.
         *
         * @return a new instance of FileListResponse
         */
        public ListFineTuneResponse build() {
            return new ListFineTuneResponse(object, getData());
        }
    }
}
