package com.cloudurable.com.cloudurable.model;


/**
 * Responses includes a finish_reason. The possible values for finish_reason are:
 * stop: API returned complete model output.
 * length: Incomplete model output due to max_tokens parameter or token limit.
 * content_filter: Omitted content due to a flag from our content filters.
 * null: API response still in progress or incomplete.
 * If you get a length as a finish_reason then set max_tokens to a higher value than normal such as 300 or 500.
 * This ensures that the model doesn't stop generating text before it reaches the end of the message.
 */
public enum FinishReason {
    /**
     * API returned complete model output.
     */
    STOP,

    /**
     * Incomplete model output due to max_tokens parameter or token limit.
     */
    LENGTH,

    /**
     * Omitted content due to a flag from the content filters.
     */
    CONTENT_FILTER,

    /**
     * API response still in progress or incomplete.
     */
    NULL
}

