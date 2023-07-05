package com.cloudurable.jai.model.image;

import java.net.URI;

public class ImageResponseData {
    private final URI url;

    public ImageResponseData(URI url) {
        this.url = url;
    }

    public URI getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ImageResponseData{" +
                "url=" + url +
                '}';
    }
}
