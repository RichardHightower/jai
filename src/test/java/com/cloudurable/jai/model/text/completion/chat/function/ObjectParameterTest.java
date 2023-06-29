package com.cloudurable.jai.model.text.completion.chat.function;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectParameterTest {

    @Test
    void testObjectParameterBuilder() {
        // Create a list of parameters
        List<Parameter> parameters = Arrays.asList(
                Parameter.builder().setName("param1").setType(ParameterType.STRING).build(),
                Parameter.builder().setName("param2").setType(ParameterType.NUMBER).build()
        );

        // Create an ObjectParameter using the builder
        ObjectParameter objectParameter = ObjectParameter.objectParamBuilder()
                .setName("objectParam")
                .setType(ParameterType.OBJECT)
                .setParameters(parameters)
                .build();

        // Verify the values of the ObjectParameter
        assertEquals("objectParam", objectParameter.getName());
        assertEquals(ParameterType.OBJECT, objectParameter.getType());
        assertEquals(parameters, objectParameter.getParameters());
    }

    @Test
    void testObjectParameterEquality() {
        // Create two ObjectParameters with the same values
        List<Parameter> parameters1 = Arrays.asList(
                Parameter.builder().setName("param1").setType(ParameterType.STRING).build(),
                Parameter.builder().setName("param2").setType(ParameterType.NUMBER).build()
        );
        List<Parameter> parameters2 = Arrays.asList(
                Parameter.builder().setName("param1").setType(ParameterType.STRING).build(),
                Parameter.builder().setName("param2").setType(ParameterType.NUMBER).build()
        );

        ObjectParameter objectParameter1 = ObjectParameter.objectParamBuilder()
                .setName("objectParam")
                .setType(ParameterType.OBJECT)
                .setParameters(parameters1)
                .build();
        ObjectParameter objectParameter2 = ObjectParameter.objectParamBuilder()
                .setName("objectParam")
                .setType(ParameterType.OBJECT)
                .setParameters(parameters2)
                .build();

        // Verify that the two ObjectParameters are equal
        assertEquals(objectParameter1, objectParameter2);
    }
}
