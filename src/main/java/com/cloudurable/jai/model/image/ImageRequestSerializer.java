package com.cloudurable.jai.model.image;

import com.cloudurable.jai.model.audio.AudioRequest;
import com.cloudurable.jai.util.JsonSerializer;
import com.cloudurable.jai.util.MultipartEntityBuilder;

/**
 * Encoder class for building a multipart form for an image creation request.
 */
public class ImageRequestSerializer {


    public static MultipartEntityBuilder buildCreateForm(CreateImageRequest imageRequest) {
        MultipartEntityBuilder form = buildForm(imageRequest);
        form.addTextBody("prompt", imageRequest.getPrompt());

        // Add the audio file as binary body
        //form.addBinaryBody("file", imageRequest.getI(), "application/binary", "transcribe.m4a");
        return form;
    }


    public static MultipartEntityBuilder buildEditForm(EditImageRequest imageRequest) {
        MultipartEntityBuilder form = buildForm(imageRequest);
        form.addTextBody("prompt", imageRequest.getPrompt());

        // Add the image file as binary body
        form.addBinaryBody("image", imageRequest.getImageBody(), "application/binary", imageRequest.getImageFileName());

        if (imageRequest.getMaskBody() != null) {
            form.addBinaryBody("mask", imageRequest.getMaskBody(), "application/binary", imageRequest.getMaskImageFileName());
        }
        return form;
    }

    public static MultipartEntityBuilder buildVariationForm(CreateImageVariationRequest imageRequest) {
        MultipartEntityBuilder form = buildForm(imageRequest);

        // Add the image file as binary body
        form.addBinaryBody("image", imageRequest.getImageBody(), "application/binary", imageRequest.getImageFileName());
        return form;
    }

    /**
     * Builds a multipart form for an audio transcription request.
     *
     * @param imageRequest The audio transcription request.
     * @return The constructed MultipartEntityBuilder object.
     */
    public static MultipartEntityBuilder buildForm(ImageRequest imageRequest) {
        MultipartEntityBuilder form = MultipartEntityBuilder.create();




        // Add response format if available
        if (imageRequest.getResponseFormat() != null) {
            form.addTextBody("response_format", imageRequest.getResponseFormat().toString().toLowerCase());
        }

        // Add size if available
        if (imageRequest.getSize() != null) {
            switch (imageRequest.getSize()) {
                case SIZE_1024_1024:
                    form.addTextBody("size", "1024x1024");
                    break;
                case SIZE_256_BY_256:
                    form.addTextBody("size", "256x256");
                    break;
                case SIZE_512_BY_512:
                    form.addTextBody("size", "512x512");
                    break;
            }
        }

        if (imageRequest.getN() != 0 && imageRequest.getN() !=1) {
            form.addTextBody("n", String.valueOf(imageRequest.getN()));
        }

        if(imageRequest.getUser() != null) {
            form.addTextBody("user", imageRequest.getUser());
        }



        return form;
    }


    public static String buildJson(CreateImageRequest imageRequest) {
        JsonSerializer serializer = new JsonSerializer();

        serializer.startObject();

        serializer.addAttribute("prompt", imageRequest.getPrompt());

        // Add response format if available
        if (imageRequest.getResponseFormat() != null) {
            serializer.addAttribute("response_format", imageRequest.getResponseFormat().toString().toLowerCase());
        }

        // Add size if available
        if (imageRequest.getSize() != null) {
            switch (imageRequest.getSize()) {
                case SIZE_1024_1024:
                    serializer.addAttribute("size",  "1024x1024");
                    break;
                case SIZE_256_BY_256:
                    serializer.addAttribute("size", "256x256");
                    break;
                case SIZE_512_BY_512:
                    serializer.addAttribute("size", "512x512");
                    break;
            }
        }

        if (imageRequest.getN() != 0 && imageRequest.getN() !=1) {
            serializer.addAttribute("n", imageRequest.getN());
        }

        if(imageRequest.getUser() != null) {
            serializer.addAttribute("user", imageRequest.getUser());
        }


        serializer.endObject();

        return serializer.toString();
    }


    public static String getEncodingContentType(MultipartEntityBuilder form) {
        return "multipart/form-data;boundary=\"" + form.getBoundary() + "\"";
    }
}
