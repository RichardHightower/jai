package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateFineTuneRequestTest {

    @Test
    public void testBuilder() {
        String model = "curie";
        String trainingFile = "training.jsonl";
        String validationFile = "validation.jsonl";
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


        CreateFineTuneRequest request2 = CreateFineTuneRequest.builder()
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

        assertEquals(model, request.getModel());
        assertEquals(trainingFile, request.getTrainingFile());
        assertEquals(validationFile, request.getValidationFile());
        assertEquals(nEpochs, request.getNEpochs());
        assertEquals(batchSize, request.getBatchSize());
        assertEquals(learningRateMultiplier, request.getLearningRateMultiplier(), 0.001f);
        assertEquals(promptLossWeight, request.getPromptLossWeight(), 0.001f);
        assertEquals(computeClassificationMetrics, request.isComputeClassificationMetrics());
        assertEquals(classificationNClasses, request.getClassificationNClasses());
        assertEquals(classificationPositiveClass, request.getClassificationPositiveClass());
        assertEquals(classificationBetas, request.getClassificationBetas());
        assertEquals(suffix, request.getSuffix());

        assertEquals(request, request2);
        assertEquals(request.hashCode(), request2.hashCode());
        assertEquals(request.toString(), request2.toString());
    }
}
