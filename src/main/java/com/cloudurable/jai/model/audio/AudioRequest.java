package com.cloudurable.jai.model.audio;

import com.cloudurable.jai.model.Request;

public abstract class AudioRequest implements Request {

    /**
     * The audio file object (not file name) to transcribe, in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm.
     */
    private final byte[] file;

    /**
     * ID of the model to use. Only whisper-1 is currently available.
     */
    private final String model;
    /* An optional text to guide the model's style or continue a previous audio segment. The prompt should match the audio language. */
    private final String prompt;

    /**
     * An optional format.that defaults to json
     * The format of the transcript output, in one of these options: json, text, srt, verbose_json, or vtt.
     */
    private final AudioResponseFormat responseFormat;

    /**
     * Optional value and it defaults to 0
     * The sampling temperature, between 0 and 1. Higher values like 0.8 will make the output more random,
     * while lower values like 0.2 will make it more focused and deterministic. If set to 0, the model will use
     * log probability to automatically increase the temperature until certain thresholds are hit.
     */
    private final float temperature;

    public AudioRequest(byte[] file, String model, String prompt, AudioResponseFormat responseFormat, float temperature) {
        this.file = file;
        this.model = model;
        this.prompt = prompt;
        this.responseFormat = responseFormat;
        this.temperature = temperature;
    }

    public byte[] getFile() {
        return file;
    }

    public String getModel() {
        return model;
    }

    public String getPrompt() {
        return prompt;
    }

    public AudioResponseFormat getResponseFormat() {
        return responseFormat;
    }

    public float getTemperature() {
        return temperature;
    }


}
