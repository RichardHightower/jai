package com.cloudurable.jai.model.file;

import com.cloudurable.jai.model.Response;

import java.util.Objects;

/**
 * {
 *   "id": "file-XjGxS3KTG0uNmNOK362iJua3",
 *   "object": "file",
 *   "deleted": true
 * }
 */
public class FileDeleteResponse implements Response {
    private final String id;
    private final String object;
    private final boolean deleted;

    private FileDeleteResponse(String id, String object, boolean deleted) {
        this.id = id;
        this.object = object;
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileDeleteResponse)) return false;
        FileDeleteResponse that = (FileDeleteResponse) o;
        return deleted == that.deleted && Objects.equals(id, that.id) && Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, deleted);
    }

    @Override
    public String toString() {
        return "FileDeleteResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {

        private  String id;
        private  String object;
        private  boolean deleted;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        private Builder(){}


        public FileDeleteResponse build() {
            return new FileDeleteResponse(id, object, deleted);
        }
    }
}
