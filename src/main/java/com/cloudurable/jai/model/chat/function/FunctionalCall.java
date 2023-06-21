package com.cloudurable.jai.model.chat.function;

import java.util.List;
import java.util.Objects;

/**
 * Represents a functional call in a chat system.
 */
public class FunctionalCall {
    private final String name;
    private final List<Argument<?>> arguments;

    /**
     * Constructs a new FunctionalCall object.
     *
     * @param name      the name of the function being called
     * @param arguments the list of arguments for the function call
     */
    public FunctionalCall(String name, List<Argument<?>> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * Returns a new Builder instance to construct a FunctionalCall object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the name of the function being called.
     *
     * @return the name of the function being called
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of arguments for the function call.
     *
     * @return the list of arguments for the function call
     */
    public List<Argument<?>> getArguments() {
        return arguments;
    }

    /**
     * Returns a string representation of the FunctionalCall.
     *
     * @return a string representation of the FunctionalCall
     */
    @Override
    public String toString() {
        return "FunctionalCall{" +
                "name='" + name + '\'' +
                ", arguments=" + arguments +
                '}';
    }

    /**
     * Checks if this FunctionalCall is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionalCall)) return false;
        FunctionalCall that = (FunctionalCall) o;
        return Objects.equals(name, that.name) && Objects.equals(arguments, that.arguments);
    }

    /**
     * Computes the hash code for this FunctionalCall.
     *
     * @return the hash code value for this FunctionalCall
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, arguments);
    }

    /**
     * Builder class for constructing FunctionalCall objects.
     */
    public static class Builder {
        private String name;
        private List<Argument<?>> arguments;

        private Builder() {
        }

        /**
         * Sets the name of the function being called.
         *
         * @param name the name of the function being called
         * @return the Builder instance
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the list of arguments for the function call.
         *
         * @param arguments the list of arguments for the function call
         * @return the Builder instance
         */
        public Builder setArguments(List<Argument<?>> arguments) {
            this.arguments = arguments;
            return this;
        }

        /**
         * Constructs a new FunctionalCall object with the provided properties.
         *
         * @return a new FunctionalCall object
         */
        public FunctionalCall build() {
            return new FunctionalCall(name, arguments);
        }
    }
}

