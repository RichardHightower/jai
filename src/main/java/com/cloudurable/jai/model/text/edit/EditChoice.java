package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.text.Choice;

import java.util.Objects;

/**
 * Represents an edit choice in a text-based interaction.
 */
public class EditChoice extends Choice {

    private final String text;

    /**
     * Constructs a new EditChoice object.
     *
     * @param index The index of the edit choice.
     * @param text  The text content of the edit choice.
     */
    public EditChoice(int index, String text) {
        super(index, FinishReason.NULL);
        this.text = text;
    }

    /**
     * Builder
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the text content of the edit choice.
     *
     * @return The text content of the edit choice.
     */
    public String getText() {
        return text;
    }

    /**
     * Checks if this EditChoice is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditChoice)) return false;
        EditChoice that = (EditChoice) o;
        return Objects.equals(text, that.text);
    }

    /**
     * Computes the hash code of the EditChoice.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    /**
     * Returns a string representation of the EditChoice.
     *
     * @return A string representation of the EditChoice.
     */
    @Override
    public String toString() {
        return "EditChoice{" +
                "text='" + text + '\'' +
                "super=" + super.toString() + ',' +
                '}';
    }

    /**
     * Builder class for constructing EditChoice objects.
     */
    public static class Builder {
        private int index;
        private String text;

        private Builder() {
        }

        /**
         * Sets the index of the edit choice.
         *
         * @param index The index of the edit choice.
         * @return The Builder instance.
         */
        public Builder index(int index) {
            this.index = index;
            return this;
        }

        /**
         * Sets the text content of the edit choice.
         *
         * @param text The text content of the edit choice.
         * @return The Builder instance.
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Builds and returns a new EditChoice object.
         *
         * @return A new EditChoice object.
         */
        public EditChoice build() {
            return new EditChoice(index, text);
        }
    }
}
