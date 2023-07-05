package com.cloudurable.jai.model.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * A request for editing an image.
 */
public class EditImageRequest extends ImageRequest {

    private final String prompt;

    private final byte[] imageBody;
    private final String imageFileName;

    private final byte[] maskBody;
    private final String maskImageFileName;

    /**
     * Constructs an EditImageRequest object with the specified parameters.
     *
     * @param n                 the value of n
     * @param size              the image size
     * @param responseFormat    the image response format
     * @param user              the user
     * @param prompt            the prompt
     * @param imageBody         the image body
     * @param imageFileName     the image file name
     * @param maskBody          the mask body
     * @param maskImageFileName the mask image file name
     */
    public EditImageRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user, String prompt, byte[] imageBody, String imageFileName, byte[] maskBody, String maskImageFileName) {
        super(n, size, responseFormat, user);
        this.prompt = prompt;
        this.imageBody = imageBody;
        this.imageFileName = imageFileName;
        this.maskBody = maskBody;
        this.maskImageFileName = maskImageFileName;
    }

    /**
     * Returns a new instance of the Builder for creating EditImageRequest objects.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the prompt of the image edit request.
     *
     * @return the prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Returns the image body of the image edit request.
     *
     * @return the image body
     */
    public byte[] getImageBody() {
        return imageBody;
    }

    /**
     * Returns the image file name of the image edit request.
     *
     * @return the image file name
     */
    public String getImageFileName() {
        return imageFileName;
    }

    /**
     * Returns the mask body of the image edit request.
     *
     * @return the mask body
     */
    public byte[] getMaskBody() {
        return maskBody;
    }

    /**
     * Returns the mask image file name of the image edit request.
     *
     * @return the mask image file name
     */
    public String getMaskImageFileName() {
        return maskImageFileName;
    }

    /**
     * Compares this EditImageRequest to the specified object. The result is true if and only if the argument
     * is not null and is an EditImageRequest object that has the same prompt, imageFileName, n, size, responseFormat, and user as this object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditImageRequest)) return false;
        if (!super.equals(o)) return false;
        EditImageRequest that = (EditImageRequest) o;
        return Objects.equals(prompt, that.prompt) &&
                Objects.equals(imageFileName, that.imageFileName);
    }

    /**
     * Returns the hash code value for this EditImageRequest.
     *
     * @return the hash code value for this EditImageRequest
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), prompt, imageFileName);
    }

    /**
     * Returns a string representation of the EditImageRequest object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "EditImageRequest{" +
                "prompt='" + prompt + "',\n" +
                "image='" + imageFileName + "',\n" +
                "mask='" + maskImageFileName + "',\n" +
                "super='" + super.toString() + '\'' +
                '}';
    }

    /**
     * A builder class for creating EditImageRequest objects.
     */
    public static class Builder {
        private int n;
        private ImageSize size;
        private ImageResponseFormat responseFormat;
        private String user;
        private String prompt;

        private byte[] imageBody;
        private String imageFileName;

        private byte[] maskBody;
        private String maskImageFileName;

        private Builder() {
        }

        /**
         * Sets the image body for the EditImageRequest being built.
         *
         * @param imageBody the image body
         * @return the Builder instance
         */
        public Builder imageBody(byte[] imageBody) {
            this.imageBody = imageBody;
            return this;
        }

        /**
         * Sets the image file name for the EditImageRequest being built.
         *
         * @param imageFileName the image file name
         * @return the Builder instance
         */
        public Builder imageFileName(String imageFileName) {
            this.imageFileName = imageFileName;
            return this;
        }

        /**
         * Sets the image file from the specified file for the EditImageRequest being built.
         *
         * @param imageFile the image file
         * @return the Builder instance
         */
        public Builder imageFile(final File imageFile) {
            try {
                this.imageBody(Files.readAllBytes(imageFile.toPath()));
                this.imageFileName(imageFile.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        /**
         * Sets the mask body for the EditImageRequest being built.
         *
         * @param maskBody the mask body
         * @return the Builder instance
         */
        public Builder maskBody(byte[] maskBody) {
            this.maskBody = maskBody;
            return this;
        }

        /**
         * Sets the mask image file name for the EditImageRequest being built.
         *
         * @param maskImageFileName the mask image file name
         * @return the Builder instance
         */
        public Builder maskImageFileName(String maskImageFileName) {
            this.maskImageFileName = maskImageFileName;
            return this;
        }

        /**
         * Sets the mask file from the specified file for the EditImageRequest being built.
         *
         * @param maskFile the mask file
         * @return the Builder instance
         */
        public Builder maskFile(final File maskFile) {
            try {
                this.maskBody(Files.readAllBytes(maskFile.toPath()));
                this.maskImageFileName(maskFile.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        /**
         * Sets the value of n for the EditImageRequest being built.
         *
         * @param n the value of n
         * @return the Builder instance
         */
        public Builder n(int n) {
            this.n = n;
            return this;
        }

        /**
         * Sets the image size for the EditImageRequest being built.
         *
         * @param size the image size
         * @return the Builder instance
         */
        public Builder size(ImageSize size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the image response format for the EditImageRequest being built.
         *
         * @param responseFormat the image response format
         * @return the Builder instance
         */
        public Builder responseFormat(ImageResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        /**
         * Sets the user for the EditImageRequest being built.
         *
         * @param user the user
         * @return the Builder instance
         */
        public Builder user(String user) {
            this.user = user;
            return this;
        }

        /**
         * Sets the prompt for the EditImageRequest being built.
         *
         * @param prompt the prompt
         * @return the Builder instance
         */
        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        /**
         * Builds a new EditImageRequest instance with the set parameters.
         *
         * @return a new EditImageRequest instance
         */
        public EditImageRequest build() {
            return new EditImageRequest(this.n, this.size, this.responseFormat, this.user, this.prompt, imageBody, imageFileName, maskBody, maskImageFileName);
        }
    }
}
