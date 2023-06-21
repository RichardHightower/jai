package com.cloudurable.jai.model.chat.function;

import java.util.List;
import java.util.Objects;

/**
 * Represents an object parameter in a chat system.
 */
public class ObjectParameter extends Parameter {

    private final List<Parameter> parameters;

    /**
     * Constructs a new ObjectParameter object.
     *
     * @param name       the name of the object parameter
     * @param type       the type of the object parameter
     * @param parameters the list of parameters for the object parameter
     */
    public ObjectParameter(String name, ParameterType type, List<Parameter> parameters) {
        super(name, type);
        this.parameters = parameters;
    }

    /**
     * Returns a new ObjectParameterBuilder instance to construct an ObjectParameter object.
     *
     * @return a new ObjectParameterBuilder instance
     */
    public static ObjectParameterBuilder objectParamBuilder() {
        return new ObjectParameterBuilder();
    }

    /**
     * Gets the list of parameters for the object parameter.
     *
     * @return the list of parameters for the object parameter
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Checks if this ObjectParameter is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectParameter)) return false;
        if (!super.equals(o)) return false;
        ObjectParameter that = (ObjectParameter) o;
        return Objects.equals(parameters, that.parameters);
    }

    /**
     * Computes the hash code for this ObjectParameter.
     *
     * @return the hash code value for this ObjectParameter
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parameters);
    }

    /**
     * Builder class for constructing ObjectParameter objects.
     */
    public static class ObjectParameterBuilder {
        private String name;
        private ParameterType type;
        private List<Parameter> parameters;

        private ObjectParameterBuilder() {
        }

        /**
         * Sets the name of the object parameter.
         *
         * @param name the name of the object parameter
         * @return the Builder instance
         */
        public ObjectParameterBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the type of the object parameter.
         *
         * @param type the type of the object parameter
         * @return the Builder instance
         */
        public ObjectParameterBuilder setType(ParameterType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the list of parameters for the object parameter.
         *
         * @param parameters the list of parameters for the object parameter
         * @return the Builder instance
         */
        public ObjectParameterBuilder setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        /**
         * Constructs a new ObjectParameter object with the provided properties.
         *
         * @return a new ObjectParameter object
         */
        public ObjectParameter build() {
            return new ObjectParameter(this.name, this.type, this.parameters);
        }
    }
}
