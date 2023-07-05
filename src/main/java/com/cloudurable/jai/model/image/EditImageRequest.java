package com.cloudurable.jai.model.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class EditImageRequest extends ImageRequest{

    private final String prompt;

    private final byte[] imageBody;
    private final String imageFileName;

    private final byte[] maskBody;
    private final String maskImageFileName;

    public EditImageRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user, String prompt, byte[] imageBody, String imageFileName, byte[] maskBody, String maskImageFileName) {
        super(n, size, responseFormat, user);
        this.prompt = prompt;
        this.imageBody = imageBody;
        this.imageFileName = imageFileName;
        this.maskBody = maskBody;
        this.maskImageFileName = maskImageFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditImageRequest)) return false;
        if (!super.equals(o)) return false;
        EditImageRequest that = (EditImageRequest) o;
        return Objects.equals(prompt, that.prompt)
                && Objects.equals(imageFileName, that.imageFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), prompt, imageFileName);
    }

    @Override
    public String toString() {
        return "EditImageRequest{" +
                "prompt='" + prompt + "',\n" +
                "image='" + imageFileName + "',\n" +
                "mask='" + maskImageFileName + "',\n" +
                "super='" + super.toString() + '\'' +
                '}';
    }

    public String getPrompt() {
        return prompt;
    }

    public byte[] getImageBody() {
        return imageBody;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public byte[] getMaskBody() {
        return maskBody;
    }

    public String getMaskImageFileName() {
        return maskImageFileName;
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

        private byte[] imageBody;
        private String imageFileName;

        private byte[] maskBody;
        private String maskImageFileName;

        private Builder(){}


        public Builder imageBody(byte[] imageBody) {
            this.imageBody = imageBody;
            return this;
        }

        public Builder imageFileName(String imageFileName) {
            this.imageFileName = imageFileName;
            return this;
        }

        public Builder imageFile(final File imageFile) {
            try {
                this.imageBody(Files.readAllBytes(imageFile.toPath()));
                this.imageFileName(imageFile.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder maskBody(byte[] maskBody) {
            this.maskBody = maskBody;
            return this;
        }

        public Builder maskImageFileName(String maskImageFileName) {
            this.maskImageFileName = maskImageFileName;
            return this;
        }

        public Builder maskFile(final File maskFile) {
            try {
                this.maskBody(Files.readAllBytes(maskFile.toPath()));
                this.maskImageFileName(maskFile.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

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

        public EditImageRequest build() {
            return new EditImageRequest(this.n, this.size, this.responseFormat, this.user, this.prompt, imageBody, imageFileName, maskBody, maskImageFileName);
        }
    }
}
