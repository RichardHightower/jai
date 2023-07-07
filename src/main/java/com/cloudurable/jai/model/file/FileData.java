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
    private final Instant createdAt;
    private final int bytes;

    private final String fileName;

    /**
     * Constructs a new instance of FileData.
     *
     * @param id        the ID of the file
     * @param object    the object type of the file
     * @param purpose   the intended purpose of the file
     * @param createdAt the creation timestamp of the file
     * @param bytes     the size of the file in bytes
     * @param fileName  name of file
     */
    private FileData(String id, String object, String purpose, Instant createdAt, int bytes, String fileName) {
        this.id = id;
        this.object = object;
        this.purpose = purpose;
        this.createdAt = createdAt;
        this.bytes = bytes;
        this.fileName = fileName;
    }

    /**
     * Creates a new instance of the Builder for constructing FileData instances.
     *
     * @return a new instance of the Builder
     */
    public static Builder builder() {
        return new Builder();
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
     * Gets the intended purpose of the file.
     *
     * @return the intended purpose of the file
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Gets the creation timestamp of the file.
     *
     * @return the creation timestamp of the file
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the size of the file in bytes.
     *
     * @return the size of the file in bytes
     */
    public int getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileData)) return false;
        FileData fileData = (FileData) o;
        return bytes == fileData.bytes && Objects.equals(id, fileData.id) && Objects.equals(object, fileData.object) && Objects.equals(purpose, fileData.purpose) && Objects.equals(createdAt, fileData.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, purpose, createdAt, bytes);
    }

    @Override
    public String toString() {
        return "FileData{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", purpose='" + purpose + '\'' +
                ", createdAt=" + createdAt +
                ", bytes=" + bytes +
                '}';
    }

    /**
     * Builder for constructing FileData instances.
     */
    public static class Builder {
        private String id;
        private String object;
        private String purpose;
        private Instant createdAt;
        private int bytes;
        private String fileName;

        private Builder() {
        }

        /**
         * File name
         * @param fileName file name
         * @return builder
         */
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * Sets the intended purpose of the file.
         *
         * @param purpose the intended purpose of the file
         * @return the builder instance
         */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /**
         * Sets the creation timestamp of the file.
         *
         * @param createdAt createdAt
         * @return the builder instance
         */
        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Sets the size of the file in bytes.
         *
         * @param bytes the size of the file in bytes
         * @return the builder instance
         */
        public Builder bytes(int bytes) {
            this.bytes = bytes;
            return this;
        }

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
         * Builds a new instance of FileData using the configured values.
         *
         * @return a new instance of FileData
         */
        public FileData build() {
            return new FileData(id, object, purpose, createdAt, bytes, fileName);
        }
    }
}
