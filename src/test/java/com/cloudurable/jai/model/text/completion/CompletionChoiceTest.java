package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.FinishReason;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CompletionChoiceTest {

    @Test
    void testCompletionChoiceBuilder() {
        int index = 1;
        FinishReason finishReason = FinishReason.STOP;
        String text = "Hello";
        List<Integer> logprobs = Arrays.asList(1, 2, 3);

        CompletionChoice completionChoice = CompletionChoice.builder()
                .index(index)
                .finishReason(finishReason)
                .text(text)
                .logprobs(logprobs)
                .build();

        assertNotNull(completionChoice);
        assertEquals(index, completionChoice.getIndex());
        assertEquals(finishReason, completionChoice.getFinishReason());
        assertEquals(text, completionChoice.getText());
        assertEquals(logprobs, completionChoice.getLogprobs());
    }

    @Test
    void testCompletionChoiceToString() {
        int index = 1;
        FinishReason finishReason = FinishReason.LENGTH;
        String text = "Hello";
        List<Integer> logprobs = Arrays.asList(1, 2, 3);

        CompletionChoice completionChoice = new CompletionChoice(index, finishReason, text, logprobs);

        String expectedToString = "CompletionChoice{" +
                "text='" + text + '\'' +
                ", index=" + index +
                ", logprobs=" + logprobs +
                ", finishReason=" + finishReason +
                '}';

        assertEquals(expectedToString, completionChoice.toString());
    }
}
