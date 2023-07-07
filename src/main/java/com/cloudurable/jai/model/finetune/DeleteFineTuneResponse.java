package com.cloudurable.jai.model.finetune;

import com.cloudurable.jai.model.Response;

import java.util.Objects;


/**
 * The DeleteFineTuneResponse class represents a file delete response.
 * It implements the Response interface.
 * Represents a file delete response.
 * <p>
 * {
 * "id": "file-XjGxS3KTG0uNmNOK362iJua3",
 * "object": "file",
 * "deleted": true
 * }
 */
public class DeleteFineTuneResponse implements Response {
    private final String id;
    private final String object;
    private final boolean deleted;

    /**
     * Constructs a new instance of DeleteFineTuneResponse with the specified parameters.
     *
     * @param id      The ID of the file to be deleted.
     * @param object  The object type of the file.
     * @param deleted Whether the file is deleted or not.
     */
    private DeleteFineTuneResponse(String id, String object, boolean deleted) {
        this.id = id;
        this.object = object;
        this.deleted = deleted;
    }

    /**
     * Returns a new instance of the Builder for constructing DeleteFineTuneResponse objects.
     *
     * @return A new instance of the Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the ID of the file.
     *
     * @return The ID of the file.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the object type of the file.
     *
     * @return The object type of the file.
     */
    public String getObject() {
        return object;
    }

    /**
     * Checks if the file is deleted.
     *
     * @return True if the file is deleted, false otherwise.
     */
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteFineTuneResponse)) return false;
        DeleteFineTuneResponse that = (DeleteFineTuneResponse) o;
        return deleted == that.deleted && Objects.equals(id, that.id) && Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, deleted);
    }

    @Override
    public String toString() {
        return "DeleteFineTuneResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    /**
     * The Builder class for constructing DeleteFineTuneResponse instances.
     */
    public static class Builder {
        private String id;
        private String object;
        private boolean deleted;

        private Builder() {
            // Private constructor to prevent instantiation
        }

        /**
         * Sets the ID of the file.
         *
         * @param id The ID of the file.
         * @return The builder instance.
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object type of the file.
         *
         * @param object The object type of the file.
         * @return The builder instance.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets whether the file is deleted or not.
         *
         * @param deleted True if the file is deleted, false otherwise.
         * @return The builder instance.
         */
        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        /**
         * Builds a new instance of DeleteFineTuneResponse using the configured values.
         *
         * @return A new instance of DeleteFineTuneResponse.
         */
        public DeleteFineTuneResponse build() {
            return new DeleteFineTuneResponse(id, object, deleted);
        }
    }
}
