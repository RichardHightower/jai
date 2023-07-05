package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.text.TextRequest;

import java.util.Objects;

/**
 * Edit request object to edit text.
 */
public class EditRequest extends TextRequest {

    /**
     * The input text to use as a starting point for the edit.
     */
    private final String input;

    /**
     * The instruction that tells the model how to edit the prompt.
     */
    private final String instruction;

    /**
     * Constructs a TextRequest object.
     *
     * @param model           The model used for the text request.
     * @param temperature     The temperature value for the text request.
     * @param topP            The top-p value for the text request.
     * @param completionCount The number of completions to generate for each prompt.
     * @param input           The input text to use as a starting point for the edit.
     * @param instruction     The instruction that tells the model how to edit the prompt.
     */
    public EditRequest(String model, float temperature, float topP, int completionCount, String input, String instruction) {
        super(model, temperature, topP, completionCount);
        this.input = input;
        this.instruction = instruction;
    }

    /**
     * Edit request builder
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * get Input
     *
     * @return input
     */
    public String getInput() {
        return input;
    }

    /**
     * get Instruction
     *
     * @return instruction
     */
    public String getInstruction() {
        return instruction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditRequest)) return false;
        if (!super.equals(o)) return false;
        EditRequest that = (EditRequest) o;
        return Objects.equals(input, that.input) && Objects.equals(instruction, that.instruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), input, instruction);
    }


    /**
     * Builder for EditRequest.
     */
    public static class Builder {
        /**
         * The input text to use as a starting point for the edit.
         */
        private String input;

        /**
         * The instruction that tells the model how to edit the prompt.
         */
        private String instruction;

        /**
         * The model to use for completion.
         */
        private String model;

        /**
         * The temperature for controlling randomness of output.
         */
        private float temperature;

        /**
         * The cumulative probability for choosing a token in the output.
         */
        private float topP;

        /**
         * The number of completions to generate for each prompt.
         */
        private int completionCount;

        private Builder() {
        }

        /**
         * Sets the input text to use as a starting point for the edit.
         *
         * @param input The input text for the edit.
         * @return This builder instance.
         */
        public Builder input(String input) {
            this.input = input;
            return this;
        }

        /**
         * Sets the instruction that tells the model how to edit the prompt.
         *
         * @param instruction The instruction for the edit.
         * @return This builder instance.
         */
        public Builder instruction(String instruction) {
            this.instruction = instruction;
            return this;
        }

        /**
         * Sets the model to use for completion.
         *
         * @param model The model to use for completion.
         * @return This builder instance.
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the temperature for controlling the randomness of output.
         *
         * @param temperature The temperature value.
         * @return This builder instance.
         */
        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets the cumulative probability for choosing a token in the output.
         *
         * @param topP The top-p value for choosing tokens.
         * @return This builder instance.
         */
        public Builder topP(float topP) {
            this.topP = topP;
            return this;
        }

        /**
         * Sets the number of completions to generate for each prompt.
         *
         * @param completionCount The number of completions to generate.
         * @return This builder instance.
         */
        public Builder completionCount(int completionCount) {
            this.completionCount = completionCount;
            return this;
        }

        /**
         * Builds and returns a new {@link EditRequest} object with the configured parameters.
         *
         * @return A new {@link EditRequest} instance.
         */
        public EditRequest build() {
            return new EditRequest(model, temperature, topP, completionCount, input, instruction);
        }

    }
}
