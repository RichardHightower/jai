package com.cloudurable.jai.model.image;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;

public class ImageResponseDeserializer {

    public static ImageResponse deserialize(final String json) {
        final ImageResponse.Builder builder = ImageResponse.builder();
        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = jsonParser.parse(json).getObjectNode();
        builder.created(Instant.ofEpochSecond(objectNode.getInt("created")));
        final List<ImageResponseData> imageResponseData = objectNode.getArrayNode("data")
                .mapObjectNode(data -> new ImageResponseData(URI.create(data.getString("url"))));
        builder.data(imageResponseData);

        return builder.build();
    }
}
