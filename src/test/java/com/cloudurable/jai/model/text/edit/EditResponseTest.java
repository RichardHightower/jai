package com.cloudurable.jai.model.text.edit;


import com.cloudurable.jai.model.Usage;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EditResponseTest {

    @Test
    void testBuilder() {
        // Create EditChoice objects
        EditChoice choice1 = EditChoice.builder().index(1).text("Choice 1").build();
        EditChoice choice2 = EditChoice.builder().index(2).text("Choice 2").build();


        // Create a list of choices
        List<EditChoice> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);


        Instant now = Instant.now();

        // Create an EditResponse using the builder
        EditResponse response1 = EditResponse.builder()
                .object("object")
                .created(now)
                .usage(Usage.builder().completionTokens(1).promptTokens(1).build())
                .choices(choices)
                .build();


        EditResponse response2 = EditResponse.builder()
                .object("object")
                .created(now)
                .usage(Usage.builder().completionTokens(1).promptTokens(1).build())
                .addChoice(choice1).addChoice(choice2)
                .build();

        EditResponse response3 = EditResponse.builder()
                .object("object3")
                .created(now)
                .usage(Usage.builder().completionTokens(1).promptTokens(1).build())
                .choices(choices)
                .build();

        // Verify the values
        assertEquals("object", response1.getObject());
        assertEquals(choices, response1.getChoices());

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertEquals(response1.toString(), response2.toString());
        assertNotEquals(response1, response3);

    }
}
