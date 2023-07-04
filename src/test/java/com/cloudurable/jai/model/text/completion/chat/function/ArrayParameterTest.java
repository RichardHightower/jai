package com.cloudurable.jai.model.text.completion.chat.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayParameterTest {

    @Test
    void testArrayParameterBuilder() {
        // Create an ArrayParameter using the builder
        ArrayParameter arrayParameter = ArrayParameter.arrayParamBuilder()
                .setName("listParam")
                .setElementType(ParameterType.STRING)
                .build();

        // Verify the values of the ArrayParameter
        assertEquals("listParam", arrayParameter.getName());
        assertEquals(ParameterType.ARRAY, arrayParameter.getType());
        assertEquals(ParameterType.STRING, arrayParameter.getElementType());
    }

    @Test
    void testArrayParameterEquality() {
        // Create two ArrayParameters with the same values
        ArrayParameter arrayParameter1 = ArrayParameter.arrayParamBuilder()
                .setName("listParam")
                .setElementType(ParameterType.NUMBER)
                .build();
        ArrayParameter arrayParameter2 = ArrayParameter.arrayParamBuilder()
                .setName("listParam")
                .setElementType(ParameterType.NUMBER)
                .build();

        // Verify that the two ArrayParameters are equal
        assertEquals(arrayParameter1, arrayParameter2);
        assertEquals(arrayParameter1.toString(), arrayParameter2.toString());
        assertEquals(arrayParameter1.hashCode(), arrayParameter2.hashCode());
        assertEquals(arrayParameter1.getElementType(), arrayParameter2.getElementType());

    }
}
