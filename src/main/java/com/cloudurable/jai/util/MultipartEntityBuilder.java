package com.cloudurable.jai.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MultipartEntityBuilder {

    private static final String BOUNDARY_PREFIX = "boundary";
    private static final String LINE_BREAK = "\r\n";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FORM_DATA = "form-data";
    private static final String BINARY = "binary";

    private final List<Part> parts;
    private final String boundary;

    private MultipartEntityBuilder() {
        this.parts = new ArrayList<>();
        this.boundary = BOUNDARY_PREFIX + UUID.randomUUID().toString();
    }

    public static MultipartEntityBuilder create() {
        return new MultipartEntityBuilder();
    }

    public MultipartEntityBuilder addTextBody(String name, String value) {
        parts.add(new TextPart(name, value));
        return this;
    }

    public MultipartEntityBuilder addBinaryBody(String name, byte[] data, String contentType, String filename) {
        parts.add(new BinaryPart(name, data, contentType, filename));
        return this;
    }

    public byte[] build() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (Part part : parts) {
            outputStream.write(getBoundaryLine().getBytes(StandardCharsets.UTF_8));
            outputStream.write(LINE_BREAK.getBytes(StandardCharsets.UTF_8));
            outputStream.write(part.buildHeader().getBytes(StandardCharsets.UTF_8));
            outputStream.write(LINE_BREAK.getBytes(StandardCharsets.UTF_8));
            outputStream.write(LINE_BREAK.getBytes(StandardCharsets.UTF_8));
            outputStream.write(part.buildBody());
            outputStream.write(LINE_BREAK.getBytes(StandardCharsets.UTF_8));
        }
        outputStream.write(getFinalBoundaryLine().getBytes(StandardCharsets.UTF_8));
        outputStream.write(LINE_BREAK.getBytes(StandardCharsets.UTF_8));
        return outputStream.toByteArray();
    }

    public String getBoundaryLine() {
        return "--" + boundary;
    }

    public String getBoundary() {
        return boundary;
    }

    private String getFinalBoundaryLine() {
        return "--" + boundary + "--";
    }

    private abstract static class Part {

        protected final String name;

        protected Part(String name) {
            this.name = name;
        }

        public abstract String buildHeader();

        public abstract byte[] buildBody() throws IOException;

    }

    private static class TextPart extends Part {

        private final String value;

        public TextPart(String name, String value) {
            super(name);
            this.value = value;
        }

        @Override
        public String buildHeader() {
            return CONTENT_DISPOSITION + ": " + FORM_DATA + "; name=\"" + name + "\"";
        }

        @Override
        public byte[] buildBody() {
            return value.getBytes(StandardCharsets.UTF_8);
        }

    }

    private static class BinaryPart extends Part {

        private final byte[] data;
        private final String contentType;
        private final String filename;

        public BinaryPart(String name, byte[] data, String contentType, String filename) {
            super(name);
            this.data = data;
            this.contentType = contentType;
            this.filename = filename;
        }

        @Override
        public String buildHeader() {
            return CONTENT_DISPOSITION + ": " + FORM_DATA + "; name=\"" + name + "\"; filename=\"" + filename + "\""
                    + LINE_BREAK + CONTENT_TYPE + ": " + contentType;
        }

        @Override
        public byte[] buildBody() {
            return data;
        }

    }

    private static class Base64 {

        public static String encode(byte[] data) {
            // Implement your own Base64 encoding logic here
            // This is just a placeholder for demonstration purposes
            return java.util.Base64.getEncoder().encodeToString(data);
        }

    }
}
