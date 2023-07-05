package com.cloudurable.jai.model.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class CreateImageVariationRequest extends ImageRequest{


    private final byte[] imageBody;
    private final String imageFileName;


    public CreateImageVariationRequest(int n, ImageSize size, ImageResponseFormat responseFormat, String user, byte[] imageBody, String imageFileName) {
        super(n, size, responseFormat, user);
        this.imageBody = imageBody;
        this.imageFileName = imageFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateImageVariationRequest)) return false;
        if (!super.equals(o)) return false;
        CreateImageVariationRequest that = (CreateImageVariationRequest) o;
        return Objects.equals(imageFileName, that.imageFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),  imageFileName);
    }

    @Override
    public String toString() {
        return "CreateImageVariationRequest{" +

                "image='" + imageFileName + "',\n" +
                "super='" + super.toString() + '\'' +
                '}';
    }


    public byte[] getImageBody() {
        return imageBody;
    }

    public String getImageFileName() {
        return imageFileName;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int n;
        private ImageSize size;
        private ImageResponseFormat responseFormat;
        private String user;

        private byte[] imageBody;
        private String imageFileName;


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

        public CreateImageVariationRequest build() {
            return new CreateImageVariationRequest(this.n, this.size, this.responseFormat, this.user, imageBody, imageFileName);
        }
    }
}
