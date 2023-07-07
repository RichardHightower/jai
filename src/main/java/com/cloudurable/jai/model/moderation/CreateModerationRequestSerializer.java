package com.cloudurable.jai.model.moderation;

import com.cloudurable.jai.util.JsonSerializer;

public class CreateModerationRequestSerializer {


    public static String serialize(CreateModerationRequest request) {
        final JsonSerializer jsonSerializer = new JsonSerializer();
        jsonSerializer.startObject();
        jsonSerializer.addAttribute("input", request.getInput());
        jsonSerializer.addAttributeIf("model", request.getModel());
        jsonSerializer.endObject();
        return jsonSerializer.toString();
    }
}
