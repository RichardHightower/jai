package com.cloudurable.jai.model.text.edit;

import com.cloudurable.jai.model.text.Choice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EditChoiceTest {

    @Test
    void testConstructorAndGetters() {
        // Create sample data for the EditChoice
        int index = 1;
        String text = "Sample text";

        // Create an instance of EditChoice using the sample data
        EditChoice editChoice = new EditChoice(index, text);

        // Test the getters
        assertEquals(index, editChoice.getIndex());
        assertEquals(text, editChoice.getText());
    }

    @Test
    void testEqualsAndHashCode() {
        // Create two EditChoice objects with the same index and text
        int index = 1;
        String text = "Sample text";
        EditChoice editChoice1 = new EditChoice(index, text);
        EditChoice editChoice2 = new EditChoice(index, text);

        // Create another EditChoice object with different index and text
        int differentIndex = 2;
        String differentText = "Different text";
        EditChoice differentEditChoice = new EditChoice(differentIndex, differentText);

        // Test equals()
        assertEquals(editChoice1, editChoice2);
        assertNotEquals(editChoice1, differentEditChoice);

        // Test hashCode()
        assertEquals(editChoice1.hashCode(), editChoice2.hashCode());
        assertEquals(editChoice1.toString(), editChoice2.toString());
        assertEquals(editChoice1.getText(), editChoice2.getText());
        assertEquals(editChoice1.getIndex(), editChoice2.getIndex());

        assertNotEquals(editChoice1.hashCode(), differentEditChoice.hashCode());
    }


    // Add more unit tests for other methods if needed
}
