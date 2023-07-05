package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.FinishReason;
import com.cloudurable.jai.model.Usage;
import io.nats.jparse.node.ObjectNode;

public class DeserializerUtils {

    public static Usage deserializeUsage(final ObjectNode usageNode) {
        Usage.Builder builder = Usage.builder();

        if (usageNode.getNode("completion_tokens") != null) {
            builder.completionTokens(usageNode.getInt("completion_tokens"));
        }
        builder.promptTokens(usageNode.getInt("prompt_tokens"));
        builder.totalTokens(usageNode.getInt("total_tokens"));
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
