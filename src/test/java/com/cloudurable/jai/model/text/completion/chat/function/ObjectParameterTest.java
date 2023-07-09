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
                Parameter.builder().name("param1").type(ParameterType.STRING).build(),
                Parameter.builder().name("param2").type(ParameterType.NUMBER).build()
        );

        // Create an ObjectParameter using the builder
        ObjectParameter objectParameter = ObjectParameter.objectParamBuilder()
                .name("objectParam")
                .type(ParameterType.OBJECT)
                .parameters(parameters)
                .build();

        // Verify the values of the ObjectParameter
        assertEquals("objectParam", objectParameter.getName());
        assertEquals(ParameterType.OBJECT, objectParameter.getType());
        assertEquals(parameters, objectParameter.getProperties());
    }

    @Test
    void testObjectParameterEquality() {
        // Create two ObjectParameters with the same values
        List<Parameter> parameters1 = Arrays.asList(
                Parameter.builder().name("param1").type(ParameterType.STRING).build(),
                Parameter.builder().name("param2").type(ParameterType.NUMBER).build()
        );
        List<Parameter> parameters2 = Arrays.asList(
                Parameter.builder().name("param1").type(ParameterType.STRING).build(),
                Parameter.builder().name("param2").type(ParameterType.NUMBER).build()
        );

        ObjectParameter objectParameter1 = ObjectParameter.objectParamBuilder()
                .name("objectParam")
                .type(ParameterType.OBJECT)
                .parameters(parameters1)
                .build();
        ObjectParameter objectParameter2 = ObjectParameter.objectParamBuilder()
                .name("objectParam")
                .type(ParameterType.OBJECT)
                .parameters(parameters2)
                .build();

        // Verify that the two ObjectParameters are equal
        assertEquals(objectParameter1, objectParameter2);
        assertEquals(objectParameter1.toString(),
                objectParameter2.toString());
        assertEquals(objectParameter1.hashCode(),
                objectParameter2.hashCode());
    }
}
