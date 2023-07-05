package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.FinishReason;

/**
 * Choice
 */
public abstract class Choice {

    /**
     * Choice index
     */
    protected final int index;

    /**
     * Choice finish reason
     */
    protected final FinishReason finishReason;

    /**
     * Construct.
     *
     * @param index        index
     * @param finishReason finsish reason
     */
    public Choice(int index, FinishReason finishReason) {
        this.index = index;
        this.finishReason = finishReason;
    }

    /**
     * Returns the index of the chat choice.
     *
     * @return The index of the chat choice.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the finish reason for the chat choice.
     *
     * @return The finish reason for the chat choice.
     */
    public FinishReason getFinishReason() {
        return finishReason;
    }


    @Override
    public String toString() {
        return "Choice{" +
                "index=" + index +
                ", finishReason=" + finishReason +
                '}';
    }
}
