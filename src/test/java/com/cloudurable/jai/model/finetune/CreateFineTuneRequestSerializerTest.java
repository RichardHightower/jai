package com.cloudurable.jai.model.finetune;

import io.nats.jparse.Json;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateFineTuneRequestSerializerTest {

    @Test
    public void testSerialize() {
        String trainingFile = "training.jsonl";
        String validationFile = "validation.jsonl";
        String model = "curie";
        int nEpochs = 4;
        int batchSize = 32;
        float learningRateMultiplier = 0.1f;
        float promptLossWeight = 0.01f;
        boolean computeClassificationMetrics = true;
        int classificationNClasses = 3;
        String classificationPositiveClass = "positive";
        float[] classificationBetas = {0.5f, 1.0f};
        String suffix = "custom-model";

        CreateFineTuneRequest request = CreateFineTuneRequest.builder()
                .model(model)
                .trainingFile(trainingFile)
                .validationFile(validationFile)
                .nEpochs(nEpochs)
                .batchSize(batchSize)
                .learningRateMultiplier(learningRateMultiplier)
                .promptLossWeight(promptLossWeight)
                .computeClassificationMetrics(computeClassificationMetrics)
                .classificationNClasses(classificationNClasses)
                .classificationPositiveClass(classificationPositiveClass)
                .classificationBetas(classificationBetas)
                .suffix(suffix)
                .build();

        String expected = Json.niceJson("{'training-file':'training.jsonl','validation_file':'validation.jsonl'," +
                "'model':'curie','batch_size':32,'learning_rate_multiplier':0.1,'prompt_loss_weight':0.01," +
                "'classification_n_classes':3,'classification_positive_class':'positive','classification_betas':[0.5,1.0]," +
                "'suffix':'custom-model'}");


        String serialized = CreateFineTuneRequestSerializer.serialize(request);

        assertEquals(expected, serialized);
    }
}
