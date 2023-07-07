package com.cloudurable.jai.model.file;

import com.cloudurable.jai.model.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a request to upload a file.
 * Implements the Request interface.
 */
public class UploadFileRequest implements Request {
    private final String fileName;
    private final byte[] fileContents;
    private final String purpose;

    /**
     * Constructs a new UploadFileRequest with the specified file name, file contents, and purpose.
     *
     * @param fileName     the name of the file to be uploaded
     * @param fileContents the contents of the file to be uploaded
     * @param purpose      the intended purpose of the uploaded file
     */
    public UploadFileRequest(String fileName, byte[] fileContents, String purpose) {
        this.fileName = fileName;
        this.fileContents = fileContents;
        this.purpose = purpose;
    }

    /**
     * Returns a new instance of the Builder for constructing UploadFileRequest objects.
     *
     * @return a new instance of the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the name of the file.
     *
     * @return the name of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the contents of the file.
     *
     * @return the contents of the file
     */
    public byte[] getFileContents() {
        return fileContents;
    }

    /**
     * Returns the purpose of the uploaded file.
     *
     * @return the purpose of the uploaded file
     */
    public String getPurpose() {
        return purpose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadFileRequest)) return false;
        UploadFileRequest that = (UploadFileRequest) o;
        return Objects.equals(fileName, that.fileName) && Arrays.equals(fileContents, that.fileContents) && Objects.equals(purpose, that.purpose);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileName, purpose);
        result = 31 * result + Arrays.hashCode(fileContents);
        return result;
    }

    @Override
    public String toString() {
        return "UploadFileRequest{" +
                "fileName='" + fileName + '\'' +
                ", fileContents=" + Arrays.toString(fileContents) +
                ", purpose='" + purpose + '\'' +
                '}';
    }

    /**
     * Builder for constructing UploadFileRequest instances.
     */
    public static class Builder {
        private String fileName;
        private byte[] fileContents;
        private String purpose;

        /**
         * Sets the name of the file to be uploaded.
         *
         * @param fileName the name of the file
         * @return the builder instance
         */
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * Sets the contents of the file to be uploaded.
         *
         * @param fileContents the contents of the file
         * @return the builder instance
         */
        public Builder file(byte[] fileContents) {
            this.fileContents = fileContents;
            return this;
        }

        /**
         * Sets the contents of the file to be uploaded from a File object.
         *
         * @param file the File object representing the file to be uploaded
         * @return the builder instance
         */
        public Builder file(File file) {
            try {
                this.fileContents = Files.readAllBytes(file.toPath());
                this.fileName = file.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        /**
         * Sets the purpose of the uploaded file.
         *
         * @param purpose the purpose of the uploaded file
         * @return the builder instance
         */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /**
         * Builds a new instance of UploadFileRequest using the configured values.
         *
         * @return a new instance of UploadFileRequest
         */
        public UploadFileRequest build() {
            return new UploadFileRequest(fileName, fileContents, purpose);
        }
    }
}
