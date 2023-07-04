package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.text.Choice;

import java.util.Objects;

public class EditChoice extends Choice {

    private final String text;

    public EditChoice(int index, String text) {
        super(index, FinishReason.NULL);
        this.text = text;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditChoice)) return false;
        EditChoice that = (EditChoice) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "EditChoice{" +
                "text='" + text + '\'' +
                "super=" + super.toString() + ',' +
                '}';
    }

    public static class Builder {
        private int index;
        private String text;
        private Builder() {
        }

        public int getIndex() {
            return index;
        }

        public Builder index(int index) {
            this.index = index;
            return this;
        }


        public String getText() {
            return text;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public EditChoice build() {
            return new EditChoice(index, text);
        }
    }
}
