package com.cloudurable.jai.model.finetune;

import io.nats.jparse.node.ObjectNode;
import io.nats.jparse.parser.JsonParser;
import io.nats.jparse.parser.JsonParserBuilder;

import java.time.Instant;

/**
 * The FileDataDeserializer class provides a utility method for deserializing JSON into a FileData object.
 */
public class FineTuneDataDeserializer {
    private FineTuneDataDeserializer() {
    }

    /**
     * Deserializes the given JSON string into a FileData object.
     *
     * @param json the JSON string to deserialize
     * @return the deserialized FileData public static  object
     */
    public static FineTuneData deserialize(final String json) {

        final JsonParser jsonParser = JsonParserBuilder.builder().build();
        final ObjectNode objectNode = jsonParser.parse(json).getObjectNode();

        return getFineTuneData(objectNode);
    }


    /**
     * Deserializes the given JSON string into a FileData object.
     *
     * @param objectNode the JSON object to deserialize from
     * @return the deserialized FileData public static  object
     */
    public static FineTuneData getFineTuneData(ObjectNode objectNode) {
        final FineTuneData.Builder builder = FineTuneData.builder();
        builder.object(objectNode.getString("object"));
        builder.id(objectNode.getString("id"))
                .createdAt(Instant.ofEpochSecond(objectNode.getInt("created_at")));


        if (objectNode.containsKey("updated_at")) {
            builder.updatedAt(Instant.ofEpochSecond(objectNode.getInt("updated_at")));
        }

        if (objectNode.containsKey("fine_tuned_model")) {
            builder.fineTunedModel(objectNode.getString("fine_tuned_model"));
        }

        builder.hyperParams(objectNode.getObjectNode("hyperparams"));

        if (objectNode.containsKey("organization_id")) {
            builder.organizationId(objectNode.getString("organization_id"));
        }
        if (objectNode.containsKey("status")) {
            builder.organizationId(objectNode.getString("status"));
        }
        if (objectNode.containsKey("result_files")) {
            builder.resultFiles(objectNode.getArrayNode("result_files"));
        }
        if (objectNode.containsKey("validation_files")) {
            builder.validationFiles(objectNode.getArrayNode("validation_files"));
        }
        if (objectNode.containsKey("training_files")) {
            builder.trainingFiles(objectNode.getArrayNode("training_files"));
        }
        if (objectNode.containsKey("events")) {
            builder.events(objectNode.getArrayNode("events"));
        }
        return builder.build();
    }
}
