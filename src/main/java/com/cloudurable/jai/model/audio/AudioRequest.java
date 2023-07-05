package com.cloudurable.jai.model.audio;

import com.cloudurable.jai.model.Request;

/**
 * Represents an audio transcription request.
 */
public abstract class AudioRequest implements Request {

    /**
     * The audio file object (not file name) to transcribe, in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm.
     */
    private final byte[] file;

    /**
     * The name of the audio file.
     */
    private final String fileName;

    /**
     * ID of the model to use. Only whisper-1 is currently available.
     */
    private final String model;

    /**
     * An optional text to guide the model's style or continue a previous audio segment. The prompt should match the audio language.
     */
    private final String prompt;

    /**
     * The format of the transcript output.
     */
    private final AudioResponseFormat responseFormat;

    /**
     * The sampling temperature for generating the output.
     */
    private final float temperature;

    /**
     * Constructs an AudioRequest object.
     *
     * @param file           The audio file object to transcribe.
     * @param fileName       The name of the audio file.
     * @param model          The ID of the model to use.
     * @param prompt         An optional text to guide the model's style or continue a previous audio segment.
     * @param responseFormat The format of the transcript output.
     * @param temperature    The sampling temperature for generating the output.
     */
    public AudioRequest(byte[] file, String fileName, String model, String prompt, AudioResponseFormat responseFormat, float temperature) {
        this.file = file;
        this.fileName = fileName;
        this.model = model;
        this.prompt = prompt;
        this.responseFormat = responseFormat;
        this.temperature = temperature;
    }

    /**
     * Returns the audio file object.
     *
     * @return The audio file object.
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * Returns the ID of the model to use.
     *
     * @return The ID of the model to use.
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the prompt text to guide the model's style or continue a previous audio segment.
     *
     * @return The prompt text.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Returns the format of the transcript output.
     *
     * @return The format of the transcript output.
     */
    public AudioResponseFormat getResponseFormat() {
        return responseFormat;
    }

    /**
     * Returns the sampling temperature for generating the output.
     *
     * @return The sampling temperature.
     */
    public float getTemperature() {
        return temperature;
    }


    /**
     * Return file name audio file name
     *
     * @return audio file name.
     */
    public String getFileName() {
        return fileName;
    }
}
