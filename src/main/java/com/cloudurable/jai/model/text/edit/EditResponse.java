package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.TextResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the response from an edit request in a text-based interaction.
 */
public class EditResponse extends TextResponse {

    private final List<EditChoice> choices;

    /**
     * Constructs a new EditResponse object.
     *
     * @param object  The object value.
     * @param created The timestamp of when the response was created.
     * @param usage   The usage information for the response.
     * @param choices The list of edit choices in the response.
     */
    public EditResponse(String object, Instant created, Usage usage, List<EditChoice> choices) {
        super("", object, created, usage);
        this.choices = choices;
    }

    /**
     * Returns the list of edit choices in the response.
     *
     * @return The list of edit choices.
     */
    public List<EditChoice> getChoices() {
        return choices;
    }

    /**
     * Checks if this EditResponse is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditResponse)) return false;
        if (!super.equals(o)) return false;

        EditResponse that = (EditResponse) o;
        return Objects.equals(choices, that.choices);
    }

    /**
     * Computes the hash code of the EditResponse.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(choices) * 13 * super.hashCode();
    }

    /**
     * Returns a string representation of the EditResponse.
     *
     * @return A string representation of the EditResponse.
     */
    @Override
    public String toString() {
        return "EditResponse{" +
                "choices=" + choices +
                ", id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", usage=" + usage +
                ", super=" + super.toString() +
                '}';
    }

    /**
     * Returns a builder instance to construct EditResponse objects.
     *
     * @return A new instance of the Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing EditResponse objects.
     */
    public static class Builder {
        private String object;
        private Instant created;
        private Usage usage;
        private List<EditChoice> choices;

        private Builder() {
        }

        /**
         * Sets the object value.
         *
         * @param object The object value.
         * @return The Builder instance.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }


        /**
         * Sets the timestamp of when the response was created.
         *
         * @param created The timestamp of when the response was created.
         * @return The Builder instance.
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets the usage information for the response.
         *
         * @param usage The usage information.
         * @return The Builder instance.
         */
        public Builder usage(Usage usage) {
            this.usage = usage;
            return this;
        }

        /**
         * Returns the list of edit choices.
         * If the list is null, it will be initialized as an empty ArrayList.
         *
         * @return The list of edit choices.
         */
        public List<EditChoice> getChoices() {
            if (choices == null) {
                choices = new ArrayList<>();
            }
            return choices;
        }

        /**
         * Sets the list of edit choices.
         *
         * @param choices The list of edit choices.
         * @return The Builder instance.
         */
        public Builder choices(List<EditChoice> choices) {
            this.choices = choices;
            return this;
        }

        /**
         * Adds an edit choice to the list of edit choices.
         *
         * @param choice The edit choice to add.
         * @return The Builder instance.
         */
        public Builder addChoice(EditChoice choice) {
            this.getChoices().add(choice);
            return this;
        }

        /**
         * Builds and returns a new EditResponse object.
         *
         * @return A new EditResponse object.
         */
        public EditResponse build() {
            return new EditResponse(object, created, usage, getChoices());
        }
    }
}
