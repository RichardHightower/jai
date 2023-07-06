package com.cloudurable.jai.model.file;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents file data.
 */
public class FileData {
    private final String id;
    private final String object;
    private final String purpose;
    private final Instant createAt;
    private final int bytes;


    /**
     * Constructs a new instance of FileData.
     *
     * @param id       the ID of the model
     * @param object   the object of the model
     * @param purpose
     * @param createAt
     * @param bytes
     */
    private FileData(String id, String object, String purpose, Instant createAt, int bytes) {
        this.id = id;
        this.object = object;
        this.purpose = purpose;
        this.createAt = createAt;
        this.bytes = bytes;
    }

    /**
     * Creates a new instance of the Builder
     *
     * @return a new instance of the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the ID of the model.
     *
     * @return the ID of the model
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the object of the model.
     *
     * @return the object of the model
     */
    public String getObject() {
        return object;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileData)) return false;
        FileData fileData = (FileData) o;
        return bytes == fileData.bytes && Objects.equals(id, fileData.id) && Objects.equals(object, fileData.object) && Objects.equals(purpose, fileData.purpose) && Objects.equals(createAt, fileData.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, purpose, createAt, bytes);
    }

    @Override
    public String toString() {
        return "FileData{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", purpose='" + purpose + '\'' +
                ", createAt=" + createAt +
                ", bytes=" + bytes +
                '}';
    }

    /**
     * Builder for constructing FileData instances.
     */
    public static class Builder {
        private String id;
        private String object;

        private  String purpose;
        private  Instant createAt;
        private  int bytes;


        private Builder() {
        }

        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        public Builder createAt(Instant createAt) {
            this.createAt = createAt;
            return this;
        }

        public Builder bytes(int bytes) {
            this.bytes = bytes;
            return this;
        }

        /**
         * Sets the ID of the model.
         *
         * @param id the ID of the model
         * @return the builder instance
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object of the model.
         *
         * @param object the object of the model
         * @return the builder instance
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Builds a new instance of ModelData.
         *
         * @return a new instance of ModelData
         */
        public FileData build() {
            return new FileData(id, object, purpose, createAt, bytes);
        }
    }
}
