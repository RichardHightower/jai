package com.cloudurable.com.cloudurable.model.chat.function;

import java.util.Objects;

/**
 * Represents a list parameter in a chat system.
 */
public class ListParameter extends Parameter {

    private final ParameterType type;

    /**
     * Constructs a new ListParameter object.
     *
     * @param name the name of the list parameter
     * @param type the type of the list parameter
     * @param type1 the type of the list elements
     */
    public ListParameter(String name, ParameterType type, ParameterType type1) {
        super(name, type);
        this.type = type1;
    }

    /**
     * Gets the type of the list parameter.
     *
     * @return the type of the list parameter
     */
    @Override
    public ParameterType getType() {
        return type;
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
        if (!(o instanceof ListParameter)) return false;
        if (!super.equals(o)) return false;
        ListParameter that = (ListParameter) o;
        return type == that.type;
    }

    /**
     * Computes the hash code for this ListParameter.
     *
     * @return the hash code value for this ListParameter
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    /**
     * Returns a string representation of the ListParameter.
     *
     * @return a string representation of the ListParameter
     */
    @Override
    public String toString() {
        return "ListParameter{" +
                "type=" + type +
                '}';
    }

    /**
     * Returns a new ListParameterBuilder instance to construct a ListParameter object.
     *
     * @return a new ListParameterBuilder instance
     */
    public static ListParameterBuilder listParamBuilder() {
        return new ListParameterBuilder();
    }

    /**
     * Builder class for constructing ListParameter objects.
     */
    public static class ListParameterBuilder {

        private String name;
        private ParameterType type;
        private ParameterType elementType;

        /**
         * Sets the name of the list parameter.
         *
         * @param name the name of the list parameter
         * @return the Builder instance
         */
        public ListParameterBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the type of the list parameter.
         *
         * @param type the type of the list parameter
         * @return the Builder instance
         */
        public ListParameterBuilder setType(ParameterType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the type of the list elements.
         *
         * @param elementType the type of the list elements
         * @return the Builder instance
         */
        public ListParameterBuilder setElementType(ParameterType elementType) {
            this.elementType = elementType;
            return this;
        }

        /**
         * Constructs a new ListParameter object with the provided properties.
         *
         * @return a new ListParameter object
         */
        public ListParameter build() {
            return new ListParameter(this.name, this.type, this.elementType);
        }
    }
}
