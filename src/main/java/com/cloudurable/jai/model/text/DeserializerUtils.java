package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.Usage;
import io.nats.jparse.node.ObjectNode;

public class DeserializerUtils {

    public static Usage deserializeUsage(final ObjectNode usageNode) {
        Usage.Builder builder = Usage.builder();
        builder.setCompletionTokens(usageNode.getInt("completion_tokens"));
        builder.setPromptTokens(usageNode.getInt("prompt_tokens"));
        builder.setTotalTokens(usageNode.getInt("total_tokens"));
        return builder.build();
    }

    public static FinishReason deserializeFinishReason(final String finishReason) {
        switch (finishReason) {
            case "stop":
                return FinishReason.STOP;
            case "content_filter":
                return FinishReason.CONTENT_FILTER;
            case "length":
                return FinishReason.LENGTH;
            case "null":
                return FinishReason.NULL;
            default:
                return FinishReason.OTHER;
        }
    }

}
