package com.cloudurable.jai.model.image;

import java.util.Objects;

/**
 * A request for creating an image.
 */
public class CreateImageRequest extends ImageRequest {

    private final String prompt;

    /**
     * Constructs a CreateImageRequest object with the specified parameters.
     *
     * @param n              the value of n
     * @param size           the image size
     * @param responseFormat the image response format
     * @param user           the user
     * @param prompt         the prompt
     */
    public CreateImageRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user, String prompt) {
        super(n, size, responseFormat, user);
        this.prompt = prompt;
    }

    /**
     * Returns a new Builder instance to build a CreateImageRequest object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the prompt of the image request.
     *
     * @return the prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Compares this CreateImageRequest to the specified object. The result is true if and only if the argument
     * is not null and is a CreateImageRequest object that has the same prompt, n, size, responseFormat, and user as this object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateImageRequest)) return false;
        if (!super.equals(o)) return false;
        CreateImageRequest that = (CreateImageRequest) o;
        return Objects.equals(prompt, that.prompt);
    }

    /**
     * Returns the hash code value for this CreateImageRequest.
     *
     * @return the hash code value for this CreateImageRequest
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), prompt);
    }

    /**
     * Returns a string representation of the CreateImageRequest object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "CreateImageRequest{" +
                "prompt='" + prompt + "',\n" +
                "super='" + super.toString() + '\'' +
                '}';
    }

    /**
     * A builder class for CreateImageRequest.
     */
    public static class Builder {
        private int n;
        private ImageSize size;
        private ImageResponseFormat responseFormat;
        private String user;
        private String prompt;

        private Builder() {
        }

        /**
         * Sets the value of n for the CreateImageRequest being built.
         *
         * @param n the value of n
         * @return the Builder instance
         */
        public Builder n(int n) {
            this.n = n;
            return this;
        }

        /**
         * Sets the image size for the CreateImageRequest being built.
         *
         * @param size the image size
         * @return the Builder instance
         */
        public Builder size(ImageSize size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the image response format for the CreateImageRequest being built.
         *
         * @param responseFormat the image response format
         * @return the Builder instance
         */
        public Builder responseFormat(ImageResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        /**
         * Sets the user for the CreateImageRequest being built.
         *
         * @param user the user
         * @return the Builder instance
         */
        public Builder user(String user) {
            this.user = user;
            return this;
        }

        /**
         * Sets the prompt for the CreateImageRequest being built.
         *
         * @param prompt the prompt
         * @return the Builder instance
         */
        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        /**
         * Builds a new CreateImageRequest object with the configured parameters.
         *
         * @return a new CreateImageRequest object
         */
        public CreateImageRequest build() {
            return new CreateImageRequest(this.n, this.size, this.responseFormat, this.user, this.prompt);
        }
    }
}
