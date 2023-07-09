package com.cloudurable.jai.model.text.completion.chat.function;

import java.util.Objects;

/**
 * Represents a function in a chat system.
 */
public class FunctionDef {

    private final String name;
    private final String description;
    private final ObjectParameter parameters;

    /**
     * Constructs a new Function object.
     *
     * @param name        the name of the function
     * @param description the description of the function
     * @param parameters  the list of parameters for the function
     * @throws NullPointerException if the name is null
     */
    public FunctionDef(String name, String description, ObjectParameter parameters) {
        Objects.requireNonNull(name, "Name is required");
        this.name = name;
        this.description = description;
        this.parameters =parameters;
    }

    /**
     * Returns a new Builder instance to construct a Function object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the name of the function.
     *
     * @return the name of the function
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the function.
     *
     * @return the description of the function
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the list of parameters for the function.
     *
     * @return the list of parameters for the function
     */
    public ObjectParameter getParameters() {
        return parameters;
    }

    /**
     * Checks if this Function is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionDef)) return false;
        FunctionDef function = (FunctionDef) o;
        return Objects.equals(name, function.name) && Objects.equals(description, function.description);
    }

    /**
     * Computes the hash code for this Function.
     *
     * @return the hash code value for this Function
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    /**
     * Builder class for constructing Function objects.
     */
    public static class Builder {

        private String name;
        private String description;
        private ObjectParameter parameters;

        private Builder() {
        }

        /**
         * Gets the name of the function.
         *
         * @return the name of the function
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the function.
         *
         * @param name the name of the function
         * @return the Builder instance
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Gets the description of the function.
         *
         * @return the description of the function
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the description of the function.
         *
         * @param description the description of the function
         * @return the Builder instance
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the list of parameters for the function.
         *
         * @param parameters the list of parameters for the function
         * @return the Builder instance
         */
        public Builder setParameters(ObjectParameter parameters) {
            this.parameters = parameters;
            return this;
        }


        /**
         * Constructs a new Function object with the provided properties.
         *
         * @return a new Function object
         */
        public FunctionDef build() {
            return new FunctionDef(this.getName(), this.getDescription(), parameters);
        }
    }
}
