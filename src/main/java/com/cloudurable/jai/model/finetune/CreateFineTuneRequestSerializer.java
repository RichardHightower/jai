package com.cloudurable.jai.model.finetune;

import com.cloudurable.jai.util.JsonSerializer;

public class CreateFineTuneRequestSerializer {
    private CreateFineTuneRequestSerializer() {
    }


    public static String serialize(CreateFineTuneRequest request) {
        final JsonSerializer jsonSerializer = new JsonSerializer();

        jsonSerializer.startObject();

        jsonSerializer.addAttribute("training-file", request.getTrainingFile());
        jsonSerializer.addAttributeIf("validation_file", request.getValidationFile());
        jsonSerializer.addAttributeIf("model", request.getModel());

        if (request.getNEpochs() != 4) {
            jsonSerializer.addAttributeIf("n_epochs", request.getNEpochs());
        }

        if (request.getBatchSize() != -1) {
            jsonSerializer.addAttribute("batch_size", request.getBatchSize());
        }

        if (request.getLearningRateMultiplier() != -1) {
            jsonSerializer.addAttribute("learning_rate_multiplier", request.getLearningRateMultiplier());
        }

        if (request.getPromptLossWeight() != 0.01) {
            jsonSerializer.addAttribute("prompt_loss_weight", request.getPromptLossWeight());
        }

        if (!request.isComputeClassificationMetrics()) {
            jsonSerializer.addAttribute("compute_classification_metrics", request.isComputeClassificationMetrics());
        }

        if (request.getClassificationNClasses() != -1) {
            jsonSerializer.addAttribute("classification_n_classes", request.getClassificationNClasses());
        }

        jsonSerializer.addAttributeIf("classification_positive_class", request.getClassificationPositiveClass());

        if (request.getClassificationBetas() != null) {
            jsonSerializer.startNestedArrayAttribute("classification_betas");
            for (float f : request.getClassificationBetas()) {
                jsonSerializer.addElement(f);
            }
            jsonSerializer.endArray();
        }

        jsonSerializer.addAttributeIf("suffix", request.getSuffix());

        jsonSerializer.endObject();

        return jsonSerializer.toString();


    }

}
