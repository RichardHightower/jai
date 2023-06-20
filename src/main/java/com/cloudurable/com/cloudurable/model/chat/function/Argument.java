package com.cloudurable.com.cloudurable.model.chat.function;

import java.util.Objects;

/**
 * Represents an argument in a chat system.
 *
 * @param <T> the type of the argument value
 */
public class Argument<T> {
    private final ParameterType parameterType;
    private final T value;

    /**
     * Constructs a new Argument object.
     *
     * @param parameterType the parameter type of the argument
     * @param value         the value of the argument
     */
    public Argument(ParameterType parameterType, T value) {
        this.parameterType = parameterType;
        this.value = value;
    }

    /**
     * Gets the parameter type of the argument.
     *
     * @return the parameter type of the argument
     */
    public ParameterType getParameterType() {
        return parameterType;
    }

    /**
     * Gets the value of the argument.
     *
     * @return the value of the argument
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns a string representation of the Argument.
     *
     * @return a string representation of the Argument
     */
    @Override
    public String toString() {
        return "Argument{" +
                "parameterType=" + parameterType +
                ", value=" + value +
                '}';
    }

    /**
     * Checks if this Argument is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Argument)) return false;
        Argument<?> argument = (Argument<?>) o;
        return parameterType == argument.parameterType && Objects.equals(value, argument.value);
    }

    /**
     * Computes the hash code for this Argument.
     *
     * @return the hash code value for this Argument
     */
    @Override
    public int hashCode() {
        return Objects.hash(parameterType, value);
    }

    /**
     * Returns a new Builder instance to construct an Argument object.
     *
     * @param <T> the type of the argument value
     * @return a new Builder instance
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Builder class for constructing Argument objects.
     *
     * @param <T> the type of the argument value
     */
    public static class Builder<T> {
        private ParameterType parameterType;
        private T value;

        /**
         * Sets the parameter type of the argument.
         *
         * @param parameterType the parameter type of the argument
         * @return the Builder instance
         */
        public Builder<T> setParameterType(ParameterType parameterType) {
            this.parameterType = parameterType;
            return this;
        }

        /**
         * Sets the value of the argument.
         *
         * @param value the value of the argument
         * @return the Builder instance
         */
        public Builder<T> setValue(T value) {
            this.value = value;
            return this;
        }

        /**
         * Constructs a new Argument object with the provided properties.
         *
         * @return a new Argument object
         */
        public Argument<T> build() {
            return new Argument<>(parameterType, value);
        }
    }
}

