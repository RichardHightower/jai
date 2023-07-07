package com.cloudurable.jai.model.finetune;

import com.cloudurable.jai.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a response containing a list of fine tune events.
 * Implements the Response interface.
 */
public class ListFineTuneEventResponse implements Response {

    private final String object;
    private final List<FineTuneEvent> data;

    private ListFineTuneEventResponse(String object, List<FineTuneEvent> data) {
        this.object = object;
        this.data = data;
    }

    /**
     * Returns a new instance of the Builder for constructing ListFineTuneEventResponse objects.
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
    public List<FineTuneEvent> getData() {
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
        if (!(o instanceof ListFineTuneEventResponse)) return false;
        ListFineTuneEventResponse that = (ListFineTuneEventResponse) o;
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
        private List<FineTuneEvent> data;

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
        public Builder data(List<FineTuneEvent> data) {
            this.data = data;
            return this;
        }

        /**
         * Returns the list of file data. If the data is null, it creates a new empty list.
         *
         * @return the list of file data
         */
        public List<FineTuneEvent> getData() {
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
        public Builder addData(FineTuneEvent data) {
            this.getData().add(data);
            return this;
        }

        /**
         * Builds a new instance of ListFineTuneEventResponse using the configured values.
         *
         * @return a new instance of ListFineTuneEventResponse
         */
        public ListFineTuneEventResponse build() {
            return new ListFineTuneEventResponse(object, getData());
        }
    }
}
