package com.cloudurable.jai.model.file;

import com.cloudurable.jai.util.MultipartEntityBuilder;

/**
 * Encoder class for building a multipart form for an image creation request.
 */
public class UploadFileRequestSerializer {
    private UploadFileRequestSerializer() {
    }

    /**
     * Builds a multipart form for an image request.
     *
     * @param uploadFileRequest The file upload request.
     * @return The constructed MultipartEntityBuilder object.
     */
    public static MultipartEntityBuilder buildForm(UploadFileRequest uploadFileRequest) {
        MultipartEntityBuilder form = MultipartEntityBuilder.create();
        form.addTextBody("purpose", uploadFileRequest.getPurpose());
        form.addBinaryBody("file", uploadFileRequest.getFileContents(), "application/binary", uploadFileRequest.getFileName());

        return form;
    }


    /**
     * Content Type with boundary
     *
     * @param form form
     * @return content type
     */
    public static String getEncodingContentType(MultipartEntityBuilder form) {
        return "multipart/form-data;boundary=\"" + form.getBoundary() + "\"";
    }
}
