package com.cloudurable.com.cloudurable.model.chat.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a function in a chat system.
 */
public class Function {

    private final String name;
    private final String description;
    private final List<Parameter> parameters;

    /**
     * Constructs a new Function object.
     *
     * @param name        the name of the function
     * @param description the description of the function
     * @param parameters  the list of parameters for the function
     * @throws NullPointerException if the name is null
     */
    public Function(String name, String description, List<Parameter> parameters) {
        Objects.requireNonNull(name, "Name is required");
        this.name = name;
        this.description = description;
        this.parameters = Collections.unmodifiableList(parameters);
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
    public List<Parameter> getParameters() {
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
        if (!(o instanceof Function)) return false;
        Function function = (Function) o;
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
        private List<Parameter> parameters;

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
        public Builder setName(String name) {
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
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Gets the list of parameters for the function.
         * If the parameters list is null, it will be initialized to an empty ArrayList.
         *
         * @return the list of parameters for the function
         */
        public List<Parameter> getParameters() {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            return parameters;
        }

        /**
         * Sets the list of parameters for the function.
         *
         * @param parameters the list of parameters for the function
         * @return the Builder instance
         */
        public Builder setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        /**
         * Adds a parameter to the list of parameters for the function.
         *
         * @param parameter the parameter to add
         * @return the Builder instance
         */
        public Builder addParameter(Parameter parameter) {
            getParameters().add(parameter);
            return this;
        }

        /**
         * Constructs a new Function object with the provided properties.
         *
         * @return a new Function object
         */
        public Function build() {
            return new Function(this.getName(), this.getDescription(), this.getParameters());
        }
    }
}
