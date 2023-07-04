package com.cloudurable.jai.model.text.completion.chat.function;

import java.util.Objects;

/**
 * Represents a list parameter in a chat system.
 */
public class ArrayParameter extends Parameter {

    private final ParameterType elementType;

    /**
     * Constructs a new ListParameter object.
     *
     * @param name  the name of the list parameter
     * @param type  the type of the list parameter
     * @param type1 the type of the list elements
     */
    public ArrayParameter(String name, ParameterType type, ParameterType type1) {
        super(name, type);
        this.elementType = type1;
    }

    /**
     * Returns a new ArrayParameterBuilder instance to construct a ListParameter object.
     *
     * @return a new ArrayParameterBuilder instance
     */
    public static ArrayParameterBuilder arrayParamBuilder() {
        return new ArrayParameterBuilder();
    }

    /**
     * Gets the type of the list parameter.
     *
     * @return the type of the list parameter
     */
    public ParameterType getElementType() {
        return elementType;
    }

    /**
     * Checks if this ListParameter is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayParameter)) return false;
        if (!super.equals(o)) return false;
        ArrayParameter that = (ArrayParameter) o;
        return elementType == that.elementType;
    }

    /**
     * Computes the hash code for this ListParameter.
     *
     * @return the hash code value for this ListParameter
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elementType);
    }

    /**
     * Returns a string representation of the ListParameter.
     *
     * @return a string representation of the ListParameter
     */
    @Override
    public String toString() {
        return "ListParameter{" +
                "type=" + elementType + ',' +
                "super=" + super.toString() +
                '}';
    }

    /**
     * Builder class for constructing ListParameter objects.
     */
    public static class ArrayParameterBuilder {
        private String name;
        private ParameterType elementType;

        private ArrayParameterBuilder() {
        }

        /**
         * Sets the name of the list parameter.
         *
         * @param name the name of the list parameter
         * @return the Builder instance
         */
        public ArrayParameterBuilder setName(String name) {
            this.name = name;
            return this;
        }


        /**
         * Sets the type of the list elements.
         *
         * @param elementType the type of the list elements
         * @return the Builder instance
         */
        public ArrayParameterBuilder setElementType(ParameterType elementType) {
            this.elementType = elementType;
            return this;
        }

        /**
         * Constructs a new ListParameter object with the provided properties.
         *
         * @return a new ListParameter object
         */
        public ArrayParameter build() {
            return new ArrayParameter(this.name, ParameterType.ARRAY, this.elementType);
        }
    }
}
