package com.cloudurable.jai.model.text.completion;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a completion request for generating text completions based on a given prompt.
 * Extends the {@link CommonCompletionRequest} class.
 */
public class CompletionRequest extends CommonCompletionRequest {


    /**
     * The prompt for completion.
     */
    private final String prompt;

    /**
     * The suffix for completion.
     */
    private final String suffix;

    /**
     * The number of completion results to return.
     */
    private final int bestOf;

    /**
     * The log probabilities associated with the completion.
     * Default value is {@code null}.
     */
    private final Integer logprobs;

    /**
     * Indicates whether to echo back the prompt in addition to the completion.
     * Default value is {@code false}.
     */
    private final boolean echo;

    /**
     * Constructs a CompletionRequest object.
     *
     * @param model            The model to use for completion.
     * @param prompt           The prompt for completion.
     * @param suffix           The suffix for completion.
     * @param bestOf           The number of completion results to return.
     * @param temperature      The temperature for controlling randomness of output.
     * @param topP             The cumulative probability for choosing token in output.
     * @param completionCount  The number of completions to generate for each prompt.
     * @param stream           Whether to stream the response.
     * @param stop             A list of strings at which to stop generation.
     * @param maxTokens        The maximum number of tokens to generate.
     * @param presencePenalty  The penalty for presence of tokens in the output.
     * @param frequencyPenalty The penalty for the usage of tokens in the output.
     * @param logitBias        The logit bias for specific tokens.
     * @param user             The user associated with the request.
     * @param logprobs         The log probabilities associated with the completion (default: null).
     * @param echo             Whether to echo back the prompt in addition to the completion (default: false).
     */
    public CompletionRequest(String model, String prompt, String suffix, int bestOf,
                             float temperature, float topP, int completionCount,
                             boolean stream, List<String> stop, int maxTokens,
                             float presencePenalty, float frequencyPenalty,
                             Map<Integer, Float> logitBias, String user,
                             Integer logprobs, boolean echo) {
        super(model, temperature, topP, completionCount, stream, stop, maxTokens,
                presencePenalty, frequencyPenalty, logitBias, user);
        this.prompt = prompt;
        this.suffix = suffix;
        this.bestOf = bestOf;
        this.logprobs = logprobs;
        this.echo = echo;
    }

    /**
     * Returns a new builder instance to construct a CompletionRequest object.
     *
     * @return A new builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the prompt for completion.
     *
     * @return The prompt for completion.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Returns the suffix for completion.
     *
     * @return The suffix for completion.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Returns the number of completion results to return.
     *
     * @return The number of completion results to return.
     */
    public int getBestOf() {
        return bestOf;
    }

    /**
     * Returns the log probabilities associated with the completion.
     *
     * @return The log probabilities associated with the completion.
     */
    public Integer getLogprobs() {
        return logprobs;
    }

    /**
     * Returns whether to echo back the prompt in addition to the completion.
     *
     * @return {@code true} if echoing back the prompt is enabled, {@code false} otherwise.
     */
    public boolean isEcho() {
        return echo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompletionRequest)) return false;
        CompletionRequest that = (CompletionRequest) o;
        return super.equals(that) && Objects.equals(prompt, that.prompt)
                && Objects.equals(suffix, that.suffix)
                && Objects.equals(logprobs, that.logprobs)
                && echo == that.echo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prompt, suffix, logprobs, echo) * super.hashCode() * 13;
    }

    /**
     * Builder class for constructing CompletionRequest objects.
     */
    public static class Builder {
        private String model;
        private String prompt;
        private String suffix;
        private float temperature;
        private float topP;
        private int completionCount;
        private boolean stream;
        private List<String> stop;
        private int maxTokens;
        private float presencePenalty;
        private float frequencyPenalty;
        private Map<Integer, Float> logitBias;
        private String user;
        private int bestOf;
        private Integer logprobs;
        private boolean echo;

        private Builder() {
        }

        /**
         * Sets the model to use for completion.
         *
         * @param model The model to use for completion.
         * @return The builder instance.
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the prompt for completion.
         *
         * @param prompt The prompt for completion.
         * @return The builder instance.
         */
        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        /**
         * Sets the suffix for completion.
         *
         * @param suffix The suffix for completion.
         * @return The builder instance.
         */
        public Builder suffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        /**
         * Sets the temperature for controlling randomness of output.
         *
         * @param temperature The temperature for controlling randomness of output.
         * @return The builder instance.
         */
        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets the cumulative probability for choosing token in output.
         *
         * @param topP The cumulative probability for choosing token in output.
         * @return The builder instance.
         */
        public Builder topP(float topP) {
            this.topP = topP;
            return this;
        }

        /**
         * Sets the number of completions to generate for each prompt.
         *
         * @param completionCount The number of completions to generate for each prompt.
         * @return The builder instance.
         */
        public Builder completionCount(int completionCount) {
            this.completionCount = completionCount;
            return this;
        }

        /**
         * Sets whether to stream the response.
         *
         * @param stream Whether to stream the response.
         * @return The builder instance.
         */
        public Builder stream(boolean stream) {
            this.stream = stream;
            return this;
        }

        /**
         * Sets a list of strings at which to stop generation.
         *
         * @param stop A list of strings at which to stop generation.
         * @return The builder instance.
         */
        public Builder stop(List<String> stop) {
            this.stop = stop;
            return this;
        }

        /**
         * Sets the maximum number of tokens to generate.
         *
         * @param maxTokens The maximum number of tokens to generate.
         * @return The builder instance.
         */
        public Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        /**
         * Sets the penalty for presence of tokens in the output.
         *
         * @param presencePenalty The penalty for presence of tokens in the output.
         * @return The builder instance.
         */
        public Builder presencePenalty(float presencePenalty) {
            this.presencePenalty = presencePenalty;
            return this;
        }

        /**
         * Sets the penalty for the usage of tokens in the output.
         *
         * @param frequencyPenalty The penalty for the usage of tokens in the output.
         * @return The builder instance.
         */
        public Builder frequencyPenalty(float frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        /**
         * Sets the logit bias for specific tokens.
         *
         * @param logitBias The logit bias for specific tokens.
         * @return The builder instance.
         */
        public Builder logitBias(Map<Integer, Float> logitBias) {
            this.logitBias = logitBias;
            return this;
        }

        /**
         * Sets the user associated with the request.
         *
         * @param user The user associated with the request.
         * @return The builder instance.
         */
        public Builder user(String user) {
            this.user = user;
            return this;
        }

        /**
         * Sets the number of completion results to return.
         *
         * @param bestOf The number of completion results to return.
         * @return The builder instance.
         */
        public Builder bestOf(int bestOf) {
            this.bestOf = bestOf;
            return this;
        }

        /**
         * Sets the log probabilities associated with the completion.
         *
         * @param logprobs The log probabilities associated with the completion.
         * @return The builder instance.
         */
        public Builder logprobs(Integer logprobs) {
            this.logprobs = logprobs;
            return this;
        }

        /**
         * Sets whether to echo back the prompt in addition to the completion.
         *
         * @param echo Whether to echo back the prompt in addition to the completion.
         * @return The builder instance.
         */
        public Builder echo(boolean echo) {
            this.echo = echo;
            return this;
        }

        /**
         * Builds a CompletionRequest object with the provided values.
         *
         * @return The constructed CompletionRequest object.
         */
        public CompletionRequest build() {
            return new CompletionRequest(model, prompt, suffix, bestOf, temperature, topP, completionCount,
                    stream, stop, maxTokens, presencePenalty, frequencyPenalty, logitBias, user,
                    logprobs, echo);
        }
    }
}
