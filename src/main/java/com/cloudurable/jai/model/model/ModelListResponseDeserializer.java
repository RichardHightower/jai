package com.cloudurable.jai.model.model;

import com.cloudurable.jai.model.image.ImageResponse;
import com.cloudurable.jai.model.image.ImageResponseData;
import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;

public class ModelListResponseDeserializer {
    private ModelListResponseDeserializer(){}


    /**
     * Deserializes a JSON string into an ModelListResponse object.
     *
     * @param json The JSON string to deserialize.
     * @return The deserialized ImageResponse object.
     */
    public static ModelListResponse deserialize(final String json) {
        final ModelListResponse.Builder builder = ModelListResponse.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = jsonParser.parse(json).getObjectNode();

        builder.object(objectNode.getString("object"));

        final List<ModelData> data = objectNode.getArrayNode("data")
                .mapObjectNode(node -> ModelData.builder().id(node.getString("id"))
                        .ownedBy(node.getString("owned_by"))
                        .object(node.getString("object"))
                        .permission(node.getArrayNode("permission")).build());

        builder.data(data);

        return builder.build();
    }

}
