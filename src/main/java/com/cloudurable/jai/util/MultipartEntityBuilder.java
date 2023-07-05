package com.cloudurable.jai.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The {@code MultipartEntityBuilder} class is responsible for building a multipart entity
 * for HTTP requests that require multipart/form-data content type.
 * It allows adding text and binary parts to the entity and generates the byte array representation
 * of the entity.
 */
public class MultipartEntityBuilder {

    // Constants
    private static final String BOUNDARY_PREFIX = "boundary-" + UUID.randomUUID();
    private static final String LINE_BREAK = "\r\n";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FORM_DATA = "form-data";
    private static final String BINARY = "binary";

    // Instance variables
    private final List<Part> parts;
    private final String boundary;

    /**
     * Constructs a new {@code MultipartEntityBuilder} object.
     * This private constructor initializes the parts list and generates a random boundary value.
     */
    private MultipartEntityBuilder() {
        this.parts = new ArrayList<>();
        this.boundary = BOUNDARY_PREFIX;
    }

    /**
     * Creates a new instance of {@code MultipartEntityBuilder}.
     *
     * @return A new {@code MultipartEntityBuilder} instance.
     */
    public static MultipartEntityBuilder create() {
        return new MultipartEntityBuilder();
    }

    /**
     * Adds a text body part to the multipart entity.
     *
     * @param name  The name of the part.
     * @param value The value of the text body.
     * @return The {@code MultipartEntityBuilder} instance.
     */
    public MultipartEntityBuilder addTextBody(String name, String value) {
        parts.add(new TextPart(name, value));
        return this;
    }

    /**
     * Adds a binary body part to the multipart entity.
     *
     * @param name        The name of the part.
     * @param data        The binary data of the body.
     * @param contentType The content type of the binary data.
     * @param filename    The filename associated with the binary data.
     * @return The {@code MultipartEntityBuilder} instance.
     */
    public MultipartEntityBuilder addBinaryBody(String name, byte[] data, String contentType, String filename) {
        parts.add(new BinaryPart(name, data, contentType, filename));
        return this;
    }

    /**
     * Builds the multipart entity and returns it as a byte array.
     *
     * @return The byte array representation of the multipart entity.
     * @throws IOException If an I/O error occurs while building the entity.
     */
    public byte[] build() {
        try {
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the boundary line for the multipart entity.
     *
     * @return The boundary line.
     */
    public String getBoundaryLine() {
        return "--" + boundary;
    }

    /**
     * Returns the boundary value of the multipart entity.
     *
     * @return The boundary value.
     */
    public String getBoundary() {
        return boundary;
    }

    /**
     * Returns the final boundary line for the multipart entity.
     *
     * @return The final boundary line.
     */
    private String getFinalBoundaryLine() {
        return "--" + boundary + "--";
    }

    // Inner Part class

    /**
     * The {@code Part} class represents a part of the multipart entity.
     * It can be a text part or a binary part.
     */
    private abstract static class Part {

        protected final String name;

        /**
         * Constructs a new {@code Part} object with the given name.
         *
         * @param name The name of the part.
         */
        protected Part(String name) {
            this.name = name;
        }

        /**
         * Builds the header for the part.
         *
         * @return The header string.
         */
        public abstract String buildHeader();

        /**
         * Builds the body of the part.
         *
         * @return The body as a byte array.
         * @throws IOException If an I/O error occurs while building the body.
         */
        public abstract byte[] buildBody() throws IOException;

    }

    // Inner TextPart class

    /**
     * The {@code TextPart} class represents a text part of the multipart entity.
     */
    private static class TextPart extends Part {

        private final String value;

        /**
         * Constructs a new {@code TextPart} object with the given name and value.
         *
         * @param name  The name of the part.
         * @param value The value of the text part.
         */
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

    // Inner BinaryPart class

    /**
     * The {@code BinaryPart} class represents a binary part of the multipart entity.
     */
    private static class BinaryPart extends Part {

        private final byte[] data;
        private final String contentType;
        private final String filename;

        /**
         * Constructs a new {@code BinaryPart} object with the given name, binary data, content type, and filename.
         *
         * @param name        The name of the part.
         * @param data        The binary data of the part.
         * @param contentType The content type of the binary data.
         * @param filename    The filename associated with the binary data.
         */
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
}
