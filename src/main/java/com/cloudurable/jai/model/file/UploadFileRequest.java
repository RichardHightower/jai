package com.cloudurable.jai.model.file;

import com.cloudurable.jai.model.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

/**
 * Upload file
 * POST
 *
 * https://api.openai.com/v1/files
 *
 * Upload a file that contains document(s) to be used across various endpoints/features. Currently, the size of all the files uploaded by one organization can be up to 1 GB. Please contact us if you need to increase the storage limit.
 *
 * Request body
 * file
 * string
 * Required
 * Name of the JSON Lines file to be uploaded.
 *
 * If the purpose is set to "fine-tune", each line is a JSON record with "prompt" and "completion" fields representing your training examples.
 *
 * purpose
 * string
 * Required
 * The intended purpose of the uploaded documents.
 *
 * Use "fine-tune" for Fine-tuning. This allows us to validate the format of the uploaded file.
 */
public class UploadFileRequest implements Request {
    private final String fileName;
    private final byte[] fileContents;

    private final String purpose;

    public UploadFileRequest(String fileName, byte[] fileContents, String purpose) {
        this.fileName = fileName;
        this.fileContents = fileContents;
        this.purpose = purpose;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileContents() {
        return fileContents;
    }

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

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {

        private  String fileName;
        private  byte[] fileContents;

        private  String purpose;

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder file(byte[] fileContents) {
            this.fileContents = fileContents;
            return this;
        }

        public Builder file(File file) {
            try {
                this.fileContents = Files.readAllBytes(file.toPath());
                this.fileName = file.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        public UploadFileRequest build() {
            return new UploadFileRequest(fileName, fileContents, purpose);
        }
    }
}
