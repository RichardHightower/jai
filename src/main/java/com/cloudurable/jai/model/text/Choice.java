package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.FinishReason;

public abstract class Choice {
    protected final int index;
    protected final FinishReason finishReason;

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
