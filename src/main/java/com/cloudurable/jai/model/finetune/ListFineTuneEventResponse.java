package com.cloudurable.jai.model.finetune;

import com.cloudurable.jai.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 The ListFineTuneEventResponse class represents a response containing a list of fine tune events.
 It implements the Response interface.
 */
public class ListFineTuneEventResponse implements Response {

    private final String object;
    private final List<FineTuneEvent> data;

    /**
     * Constructs a new instance of ListFineTuneEventResponse with the specified parameters.
     *
     * @param object The object associated with the response.
     * @param data   The list of fine tune events.
     */
    private ListFineTuneEventResponse(String object, List<FineTuneEvent> data) {
        this.object = object;
        this.data = data;
    }

    /**
     * Returns a new instance of the Builder for constructing ListFineTuneEventResponse objects.
     *
     * @return A new instance of the Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the object associated with the response.
     *
     * @return The object associated with the response.
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets the list of fine tune events.
     *
     * @return The list of fine tune events.
     */
    public List<FineTuneEvent> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ListFineTuneEventResponse{" +
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
     * The Builder class provides methods for constructing ListFineTuneEventResponse objects.
     */
    public static class Builder {
        private Builder(){}

        private String object;
        private List<FineTuneEvent> data;

        /**
         * Sets the object associated with the response.
         *
         * @param object The object associated with the response.
         * @return The builder instance.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the list of fine tune events.
         *
         * @param data The list of fine tune events.
         * @return The builder instance.
         */
        public Builder data(List<FineTuneEvent> data) {
            this.data = data;
            return this;
        }

        /**
         * Returns the list of fine tune events. If the data is null, it creates a new empty list.
         *
         * @return The list of fine tune events.
         */
        public List<FineTuneEvent> getData() {
            if (data == null) {
                data = new ArrayList<>();
            }
            return data;
        }

        /**
         * Adds the specified fine tune event to the list of fine tune events.
         *
         * @param event The fine tune event to add.
         * @return The builder instance.
         */
        public Builder addData(FineTuneEvent event) {
            this.getData().add(event);
            return this;
        }

        /**
         * Builds a new instance of ListFineTuneEventResponse using the configured values.
         *
         * @return A new instance of ListFineTuneEventResponse.
         */
        public ListFineTuneEventResponse build() {
            return new ListFineTuneEventResponse(object, getData());
        }
    }
}
