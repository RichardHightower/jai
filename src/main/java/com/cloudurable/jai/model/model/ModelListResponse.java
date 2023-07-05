package com.cloudurable.jai.model.model;

import com.cloudurable.jai.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModelListResponse implements Response {

    private final String object;

    private final List<ModelData> data;

    public ModelListResponse(String object, List<ModelData> data) {
        this.object = object;
        this.data = data;
    }

    public String getObject() {
        return object;
    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {


        private  String object;

        private  List<ModelData> data;

        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Builder data(List<ModelData> data) {
            this.data = data;
            return this;
        }

        public List<ModelData> getData() {
            if (data == null) {
                data = new ArrayList<>();
            }
            return data;
        }


        public Builder addData(ModelData data) {
            this.getData().add(data);
            return this;
        }


        public ModelListResponse build() {
            return new ModelListResponse(object, getData());
        }
    }
}
