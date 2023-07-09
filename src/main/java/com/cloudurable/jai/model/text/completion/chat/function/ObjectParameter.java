package com.cloudurable.jai.model.text.completion.chat.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents an object parameter in a chat system.
 */
public class ObjectParameter extends Parameter {

    private final List<Parameter> properties;

    private final List<String> required;

    /**
     * Constructs a new ObjectParameter object.
     *
     * @param name       the name of the object parameter
     * @param type       the type of the object parameter
     * @param properties the list of parameters for the object parameter
     * @param required  required
     */
    public ObjectParameter(String name, String description, ParameterType type, List<Parameter> properties, List<String> required) {
        super(name, description, type);
        this.properties = properties;
        this.required = required;
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
    public List<Parameter> getProperties() {
        return properties;
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
        return Objects.equals(properties, that.properties);
    }

    /**
     * Computes the hash code for this ObjectParameter.
     *
     * @return the hash code value for this ObjectParameter
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), properties);
    }

    public List<String> getRequired() {
        return required;
    }

    /**
     * Builder class for constructing ObjectParameter objects.
     */
    public static class ObjectParameterBuilder {
        private String name;
        private String description;
        private ParameterType type = ParameterType.OBJECT;
        private List<Parameter> parameters;
        private List<String> required;

        private ObjectParameterBuilder() {
        }


        public ObjectParameterBuilder required(List<String> required) {
            this.required = required;
            return this;
        }

        public ObjectParameterBuilder required(String... required) {
            return required(Arrays.asList(required));
        }
        public ObjectParameterBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the name of the object parameter.
         *
         * @param name the name of the object parameter
         * @return the Builder instance
         */
        public ObjectParameterBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the type of the object parameter.
         *
         * @param type the type of the object parameter
         * @return the Builder instance
         */
        public ObjectParameterBuilder type(ParameterType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the list of parameters for the object parameter.
         *
         * @param parameters the list of parameters for the object parameter
         * @return the Builder instance
         */
        public ObjectParameterBuilder parameters(List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        public List<Parameter> getParameters() {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            return parameters;
        }

        public ObjectParameterBuilder addParameter(Parameter parameter) {
             getParameters().add(parameter);
             return this;
        }

        /**
         * Constructs a new ObjectParameter object with the provided properties.
         *
         * @return a new ObjectParameter object
         */
        public ObjectParameter build() {
            return new ObjectParameter(this.name, this.description,  this.type, this.parameters, required);
        }


    }
}
