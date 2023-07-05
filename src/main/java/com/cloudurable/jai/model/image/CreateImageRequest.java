package com.cloudurable.jai.model.image;

import java.util.Objects;

public class CreateImageRequest extends ImageRequest{

    private final String prompt;

    public CreateImageRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user, String prompt) {
        super(n, size, responseFormat, user);
        this.prompt = prompt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateImageRequest)) return false;
        if (!super.equals(o)) return false;
        CreateImageRequest that = (CreateImageRequest) o;
        return Objects.equals(prompt, that.prompt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), prompt);
    }

    @Override
    public String toString() {
        return "CreateImageRequest{" +
                "prompt='" + prompt + "',\n" +
                "super='" + super.toString() + '\'' +
                '}';
    }

    public String getPrompt() {
        return prompt;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int n;
        private ImageSize size;
        private ImageResponseFormat responseFormat;
        private String user;
        private String prompt;

        private Builder(){}

        public Builder n(int n) {
            this.n = n;
            return this;
        }

        public Builder size(ImageSize size) {
            this.size = size;
            return this;
        }

        public Builder responseFormat(ImageResponseFormat responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public CreateImageRequest build() {
            return new CreateImageRequest(this.n, this.size, this.responseFormat, this.user, this.prompt);
        }
    }
}
