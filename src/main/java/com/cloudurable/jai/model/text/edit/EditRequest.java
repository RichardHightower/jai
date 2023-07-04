package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.text.TextRequest;

import java.util.Objects;

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

    public static Builder builder() {
        return new Builder();
    }

    public String getInput() {
        return input;
    }

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

    public static class Builder {
        /**
         * The input text to use as a starting point for the edit.
         */
        private String input;
        /**
         * The instruction that tells the model how to edit the prompt.
         */
        private String instruction;
        private String model;
        private float temperature;
        private float topP;
        private int completionCount;
        private Builder() {
        }

        public String getInput() {
            return input;
        }

        public Builder input(String input) {
            this.input = input;
            return this;
        }

        public String getInstruction() {
            return instruction;
        }

        public Builder instruction(String instruction) {
            this.instruction = instruction;
            return this;
        }

        public String getModel() {
            return model;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public float getTemperature() {
            return temperature;
        }

        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public float getTopP() {
            return topP;
        }

        public Builder topP(float topP) {
            this.topP = topP;
            return this;
        }

        public int getCompletionCount() {
            return completionCount;
        }

        public Builder completionCount(int completionCount) {
            this.completionCount = completionCount;
            return this;
        }

        public EditRequest build() {
            return new EditRequest(model, temperature, topP, completionCount, input, instruction);
        }

    }
}
