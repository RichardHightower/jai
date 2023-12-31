package com.cloudurable.jai.model.text.completion.chat.function;

import java.util.Objects;

/**
 * Represents a parameter in a chat system.
 */
public class Parameter {
    private final String name;
    private final String description;
    private final ParameterType type;

    /**
     * Constructs a new Parameter object.
     *
     * @param name        the name of the parameter
     * @param description description
     * @param type        the type of the parameter
     */
    public Parameter(String name, String description, ParameterType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }



    /**
     * Gets the name of the parameter.
     *
     * @return the name of the parameter
     */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Gets the type of the parameter.
     *
     * @return the type of the parameter
     */
    public ParameterType getType() {
        return type;
    }

    /**
     * Checks if this Parameter is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(name, parameter.name) && type == parameter.type;
    }

    /**
     * Computes the hash code for this Parameter.
     *
     * @return the hash code value for this Parameter
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    /**
     * Returns a string representation of the Parameter.
     *
     * @return a string representation of the Parameter
     */
    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + ',' +
                "type=" + type + ',' +
                "super=" + super.toString() +
                '}';
    }

    /**
     * Returns a new Builder instance to construct a Parameter object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing Parameter objects.
     */
    public static class Builder {
        private String name;
        private ParameterType type = ParameterType.STRING;
        private String description;

        private Builder() {
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the name of the parameter.
         *
         * @param name the name of the parameter
         * @return the Builder instance
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the type of the parameter.
         *
         * @param type the type of the parameter
         * @return the Builder instance
         */
        public Builder type(ParameterType type) {
            this.type = type;
            return this;
        }

        /**
         * Constructs a new Parameter object with the provided properties.
         *
         * @return a new Parameter object
         */
        public Parameter build() {
            return new Parameter(this.name, description, this.type);
        }
    }
}
