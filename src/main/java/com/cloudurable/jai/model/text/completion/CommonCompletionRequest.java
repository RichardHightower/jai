package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.text.TextRequest;

import java.util.List;
import java.util.Map;

/**
 * Abstract class for common completion requests.
 * Extends the TextRequest class.
 */
public abstract class CommonCompletionRequest extends TextRequest {
    protected final boolean stream;
    protected final List<String> stop;
    protected final int maxTokens;
    protected final float presencePenalty;
    protected final float frequencyPenalty;
    protected final Map<Integer, Float> logitBias;
    protected final String user;

    /**
     * Constructs a CommonCompletionRequest object with the specified parameters.
     *
     * @param model            The model for the completion request.
     * @param temperature      The temperature for sampling.
     * @param topP             The top-p value for nucleus sampling.
     * @param completionCount  The number of completions to generate.
     * @param stream           Indicates if the request should be streamed.
     * @param stop             The list of stop tokens.
     * @param maxTokens        The maximum number of tokens to generate.
     * @param presencePenalty  The presence penalty value.
     * @param frequencyPenalty The frequency penalty value.
     * @param logitBias        The logit bias map.
     * @param user             The user associated with the request.
     */
    public CommonCompletionRequest(String model, float temperature, float topP, int completionCount, boolean stream, List<String> stop, int maxTokens, float presencePenalty, float frequencyPenalty, Map<Integer, Float> logitBias, String user) {
        super(model, temperature, topP, completionCount);
        this.stream = stream;
        this.stop = stop;
        this.maxTokens = maxTokens;
        this.presencePenalty = presencePenalty;
        this.frequencyPenalty = frequencyPenalty;
        this.logitBias = logitBias;
        this.user = user;
    }

    /**
     * Checks if the chat request should be streamed.
     *
     * @return true if the chat request should be streamed, false otherwise
     */
    public boolean isStream() {
        return stream;
    }

    /**
     * Gets the list of stop tokens for the chat request.
     *
     * @return the list of stop tokens for the chat request
     */
    public List<String> getStop() {
        return stop;
    }

    /**
     * Gets the maximum number of tokens for the chat request.
     *
     * @return the maximum number of tokens for the chat request
     */
    public int getMaxTokens() {
        return maxTokens;
    }

    /**
     * Gets the presence penalty value for the chat request.
     *
     * @return the presence penalty value for the chat request
     */
    public float getPresencePenalty() {
        return presencePenalty;
    }

    /**
     * Gets the frequency penalty value for the chat request.
     *
     * @return the frequency penalty value for the chat request
     */
    public float getFrequencyPenalty() {
        return frequencyPenalty;
    }

    /**
     * Gets the logit bias map for the chat request.
     *
     * @return the logit bias map for the chat request
     */
    public Map<Integer, Float> getLogitBias() {
        return logitBias;
    }

    /**
     * Gets the user associated with the chat request.
     *
     * @return the user associated with the chat request
     */
    public String getUser() {
        return user;
    }
}
