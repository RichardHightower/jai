package com.cloudurable.jai.model.file;

import com.cloudurable.jai.model.Response;
import java.util.Objects;

/**
 * Represents a file delete response.
 *
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

    /**
     * Constructs a new instance of FileDeleteResponse with the specified parameters.
     *
     * @param id      the ID of the file to be deleted
     * @param object  the object type of the file
     * @param deleted whether the file is deleted or not
     */
    private FileDeleteResponse(String id, String object, boolean deleted) {
        this.id = id;
        this.object = object;
        this.deleted = deleted;
    }

    /**
     * Gets the ID of the file.
     *
     * @return the ID of the file
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the object type of the file.
     *
     * @return the object type of the file
     */
    public String getObject() {
        return object;
    }

    /**
     * Checks if the file is deleted.
     *
     * @return true if the file is deleted, false otherwise
     */
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

    /**
     * Builder for constructing FileDeleteResponse instances.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for constructing FileDeleteResponse instances.
     */
    public static class Builder {
        private String id;
        private String object;
        private boolean deleted;

        /**
         * Sets the ID of the file.
         *
         * @param id the ID of the file
         * @return the builder instance
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object type of the file.
         *
         * @param object the object type of the file
         * @return the builder instance
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets whether the file is deleted or not.
         *
         * @param deleted true if the file is deleted, false otherwise
         * @return the builder instance
         */
        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        private Builder() {}

        /**
         * Builds a new instance of FileDeleteResponse using the configured values.
         *
         * @return a new instance of FileDeleteResponse
         */
        public FileDeleteResponse build() {
            return new FileDeleteResponse(id, object, deleted);
        }
    }
}
