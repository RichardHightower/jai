package com.cloudurable.jai.model.text.completion.chat.function;

import java.util.Arrays;
import java.util.List;

public class EnumParameter extends Parameter{

    private final List<String> enumValues;
    /**
     * Constructs a new Parameter object.
     *
     * @param name        the name of the parameter
     * @param description description
     * @param type        the type of the parameter
     * @param enumValues enum values
     */
    public EnumParameter(String name, String description, ParameterType type, List<String> enumValues) {
        super(name, description, type);
        this.enumValues = enumValues;
    }

    public List<String> getEnumValues() {
        return enumValues;
    }

    /**
     * Returns a new Builder instance to construct a Parameter object.
     *
     * @return a new Builder instance
     */
    public static EnumBuilder enumBuilder() {
        return new EnumBuilder();
    }

    /**
     * Builder class for constructing Parameter objects.
     */
    public static class EnumBuilder {
        private String name;
        private ParameterType type = ParameterType.STRING;
        private String description;

        private List<String> enumValues;

        private EnumBuilder() {
        }

        public EnumBuilder enumValues(List<String> enumValues) {
            this.enumValues = enumValues;
            return this;
        }

        public EnumBuilder enumValues(String... enumValues) {
            return enumValues(Arrays.asList(enumValues));
        }

        public EnumBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the name of the parameter.
         *
         * @param name the name of the parameter
         * @return the Builder instance
         */
        public EnumBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the type of the parameter.
         *
         * @param type the type of the parameter
         * @return the Builder instance
         */
        public EnumBuilder type(ParameterType type) {
            this.type = type;
            return this;
        }

        /**
         * Constructs a new Parameter object with the provided properties.
         *
         * @return a new Parameter object
         */
        public EnumParameter build() {
            return new EnumParameter(this.name, description, this.type, this.enumValues);
        }
    }
}
