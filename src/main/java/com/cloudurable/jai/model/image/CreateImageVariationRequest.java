package com.cloudurable.jai.model.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * A request for creating an image variation.
 */
public class CreateImageVariationRequest extends ImageRequest {

    private final byte[] imageBody;
    private final String imageFileName;

    /**
     * Constructs a CreateImageVariationRequest object with the specified parameters.
     *
     * @param n              the value of n
     * @param size           the image size
     * @param responseFormat the image response format
     * @param user           the user
     * @param imageBody      the image body
     * @param imageFileName  the image file name
     */
    public CreateImageVariationRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user, byte[] imageBody, String imageFileName) {
        super(n, size, responseFormat, user);
        this.imageBody = imageBody;
        this.imageFileName = imageFileName;
    }

    /**
     * Returns a new Builder instance to build a CreateImageVariationRequest object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the image body of the image variation request.
     *
     * @return the image body
     */
    public byte[] getImageBody() {
        return imageBody;
    }

    /**
     * Returns the image file name of the image variation request.
     *
     * @return the image file name
     */
    public String getImageFileName() {
        return imageFileName;
    }

    /**
     * Compares this CreateImageVariationRequest to the specified object. The result is true if and only if the argument
     * is not null and is a CreateImageVariationRequest object that has the same image file name, n, size, responseFormat, and user as this object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateImageVariationRequest)) return false;
        if (!super.equals(o)) return false;
        CreateImageVariationRequest that = (CreateImageVariationRequest) o;
        return Objects.equals(imageFileName, that.imageFileName);
    }

    /**
     * Returns the hash code value for this CreateImageVariationRequest.
     *
     * @return the hash code value for this CreateImageVariationRequest
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), imageFileName);
    }

    /**
     * Returns a string representation of the CreateImageVariationRequest object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "CreateImageVariationRequest{" +
                "image='" + imageFileName + "',\n" +
                "super='" + super.toString() + '\'' +
                '}';
    }

    /**
     * A builder class for CreateImageVariationRequest.
     */
    public static class Builder {
        private int n;
        private ImageSize size;
        private ImageResponseFormat responseFormat;
        private String user;

        private byte[] imageBody;
        private String imageFileName;

        private Builder() {
        }

        /**
         * Sets the image body for the CreateImageVariationRequest being built.
         *
         * @param imageBody the image body
         * @return the Builder instance
         */
        public Builder imageBody(byte[] imageBody) {
            this.imageBody = imageBody;
            return this;
        }

        /**
         * Sets the image file name for the CreateImageVariationRequest being built.
         *
         * @param imageFileName the image file name
         * @return the Builder instance
         */
        public Builder imageFileName(String imageFileName) {
            this.imageFileName = imageFileName;
            return this;
        }

        /**
         * Sets the image file from the specified file for the CreateImageVariationRequest being built.
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
         * Sets the value of n for the CreateImageVariationRequest being built.
         *
         * @param n the value of n
         * @return the Builder instance
         */
        public Builder n(int n) {
            this.n = n;
            return this;
        }

        /**
         * Sets the image size for the CreateImageVariationRequest being built.
         *
         * @param size the image size
         * @return the Builder instance
         */
        public Builder size(ImageSize size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the image response format for the CreateImageVariationRequest being built.
         *
         * @param responseFormat the image response format
         * @return the Builder instance
         */
        public Builder responseFormat(ImageResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        /**
         * Sets the user for the CreateImageVariationRequest being built.
         *
         * @param user the user
         * @return the Builder instance
         */
        public Builder user(String user) {
            this.user = user;
            return this;
        }

        /**
         * Builds a new CreateImageVariationRequest object with the configured parameters.
         *
         * @return a new CreateImageVariationRequest object
         */
        public CreateImageVariationRequest build() {
            return new CreateImageVariationRequest(this.n, this.size, this.responseFormat, this.user, imageBody, imageFileName);
        }
    }
}
