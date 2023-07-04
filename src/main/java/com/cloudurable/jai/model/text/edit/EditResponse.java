package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.TextResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditResponse extends TextResponse {

    private final List<EditChoice> choices;

    public EditResponse(String object, Instant created, Usage usage, List<EditChoice> choices) {
        super("", object, created, usage);
        this.choices = choices;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<EditChoice> getChoices() {
        return choices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditResponse)) return false;
        EditResponse that = (EditResponse) o;
        return Objects.equals(choices, that.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choices);
    }

    @Override
    public String toString() {
        return "EditResponse{" +
                "choices=" + choices +
                ", id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", usage=" + usage +
                '}';
    }

    public static class Builder {
        private String object;
        private Instant created;
        private Usage usage;
        private List<EditChoice> choices;
        private Builder() {
        }

        public String getObject() {
            return object;
        }

        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Instant getCreated() {
            return created;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Usage getUsage() {
            return usage;
        }

        public Builder usage(Usage usage) {
            this.usage = usage;
            return this;
        }

        public List<EditChoice> getChoices() {
            if (choices == null) {
                choices = new ArrayList<>();
            }
            return choices;
        }

        public Builder choices(List<EditChoice> choices) {
            this.choices = choices;
            return this;
        }

        public Builder addChoice(EditChoice choice) {
            this.getChoices().add(choice);
            return this;
        }


        public EditResponse build() {
            return new EditResponse(object, created, usage, getChoices());
        }


    }
}
