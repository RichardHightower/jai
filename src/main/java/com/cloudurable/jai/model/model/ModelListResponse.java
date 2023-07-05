package com.cloudurable.jai.model.model;

import com.cloudurable.jai.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The ModelListResponse class represents a response containing a list of model data.
 * It implements the Response interface.
 */
public class ModelListResponse implements Response {

    private final String object;
    private final List<ModelData> data;

    /**
     * Constructs a new ModelListResponse with the specified object and data.
     *
     * @param object the object associated with the response
     * @param data   the list of model data
     */
    public ModelListResponse(String object, List<ModelData> data) {
        this.object = object;
        this.data = data;
    }

    /**
     * Returns a new instance of the Builder for constructing ModelListResponse objects.
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
     * Returns the list of model data.
     *
     * @return the list of model data
     */
    public List<ModelData> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ModelListResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelListResponse)) return false;
        ModelListResponse that = (ModelListResponse) o;
        return Objects.equals(object, that.object) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, data);
    }

    /**
     * The Builder class provides methods for constructing ModelListResponse objects.
     */
    public static class Builder {

        private String object;
        private List<ModelData> data;

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
         * Sets the list of model data.
         *
         * @param data the list of model data
         * @return the builder instance
         */
        public Builder data(List<ModelData> data) {
            this.data = data;
            return this;
        }

        /**
         * Returns the list of model data. If the data is null, it creates a new empty list.
         *
         * @return the list of model data
         */
        public List<ModelData> getData() {
            if (data == null) {
                data = new ArrayList<>();
            }
            return data;
        }

        /**
         * Adds the specified model data to the list of model data.
         *
         * @param data the model data to add
         * @return the builder instance
         */
        public Builder addData(ModelData data) {
            this.getData().add(data);
            return this;
        }

        /**
         * Builds a new instance of ModelListResponse using the configured values.
         *
         * @return a new instance of ModelListResponse
         */
        public ModelListResponse build() {
            return new ModelListResponse(object, getData());
        }
    }
}
