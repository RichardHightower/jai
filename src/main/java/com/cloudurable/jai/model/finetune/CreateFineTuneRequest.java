package com.cloudurable.jai.model.finetune;

import com.cloudurable.jai.model.Request;

import java.util.Arrays;
import java.util.Objects;


public class CreateFineTuneRequest implements Request {

    /**
     * model
     * string
     * Optional
     * Defaults to curie
     * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci", or a
     * fine-tuned model created after 2022-04-21. To learn more about these models, see the Models documentation.
     */
    private final String model;

    /**
     * The ID of an uploaded file that contains training data.
     * Your dataset must be formatted as a JSONL file, where each training example is a JSON object
     * with the keys "prompt" and "completion". Additionally, you must upload your file with the purpose fine-tune.
     */
    private final String trainingFile;

    /**
     * The ID of an uploaded file that contains validation data.
     * <p>
     * If you provide this file, the data is used to generate validation metrics periodically during fine-tuning.
     * These metrics can be viewed in the fine-tuning results file. Your train and validation data should be mutually exclusive.
     * <p>
     * Your dataset must be formatted as a JSONL file, where each validation example is a JSON object with the keys
     * "prompt" and "completion". Additionally, you must upload your file with the purpose fine-tune.
     */
    private final String validationFile;

    /**
     * n_epochs
     * integer
     * Optional
     * Defaults to 4
     * The number of epochs to train the model for. An epoch refers to one full cycle through the training dataset.
     */
    private final int nEpochs;

    /**
     * batch_size
     * integer
     * Optional
     * Defaults to null
     * The batch size to use for training. The batch size is the number of training examples used to train a single
     * forward and backward pass.
     * <p>
     * By default, the batch size will be dynamically configured to be ~0.2% of the number of examples in the training
     * set, capped at 256 - in general, we've found that larger batch sizes tend to work better for larger datasets.
     */
    private final int batchSize;

    /**
     * learning_rate_multiplier
     * number
     * Optional
     * Defaults to null
     * The learning rate multiplier to use for training. The fine-tuning learning rate is the original learning rate
     * used for pretraining multiplied by this value.
     * <p>
     * By default, the learning rate multiplier is the 0.05, 0.1, or 0.2 depending on final batch_size (larger learning
     * rates tend to perform better with larger batch sizes). We recommend experimenting with values in the range
     * 0.02 to 0.2 to see what produces the best results.
     */
    private final float learningRateMultiplier;

    /**
     * prompt_loss_weight
     * number
     * Optional
     * Defaults to 0.01
     * The weight to use for loss on the prompt tokens. This controls how much the model tries to learn to generate
     * the prompt (as compared to the completion which always has a weight of 1.0), and can add a stabilizing effect to
     * training when completions are short.
     * <p>
     * If prompts are extremely long (relative to completions), it may make sense to reduce this weight so as to avoid
     * over-prioritizing learning the prompt.
     */
    private final float promptLossWeight;

    /**
     * compute_classification_metrics
     * boolean
     * Optional
     * Defaults to false
     * If set, we calculate classification-specific metrics such as accuracy and F-1 score using the validation set at
     * the end of every epoch. These metrics can be viewed in the results file.
     * <p>
     * In order to compute classification metrics, you must provide a validation_file. Additionally, you must specify
     * classification_n_classes for multiclass classification or classification_positive_class for binary classification.
     */
    private final boolean computeClassificationMetrics;

    /**
     * classification_n_classes
     * integer
     * Optional
     * Defaults to null
     * The number of classes in a classification task.
     * <p>
     * This parameter is required for multiclass classification.
     */
    private final int classificationNClasses;

    /**
     * classification_positive_class
     * string
     * Optional
     * Defaults to null
     * The positive class in binary classification.
     * <p>
     * This parameter is needed to generate precision, recall, and F1 metrics when doing binary classification.
     */
    private final String classificationPositiveClass;

    /**
     * classification_betas
     * array
     * Optional
     * Defaults to null
     * If this is provided, we calculate F-beta scores at the specified beta values. The F-beta score is a generalization of F-1 score. This is only used for binary classification.
     * <p>
     * With a beta of 1 (i.e. the F-1 score), precision and recall are given the same weight. A larger beta score puts more weight on recall and less on precision. A smaller beta score puts more weight on precision and less on recall.
     */
    private final float[] classificationBetas;

    /**
     * suffix
     * string
     * Optional
     * Defaults to null
     * A string of up to 40 characters that will be added to your fine-tuned model name.
     * <p>
     * For example, a suffix of "custom-model-name" would produce a model name like ada:ft-your-org:custom-model-name-2022-02-15-04-21-04.
     */
    private final String suffix;

    public CreateFineTuneRequest(String model, String trainingFile, String validationFile, int nEpochs, int batchSize,
                                 float learningRateMultiplier, float promptLossWeight,
                                 boolean computeClassificationMetrics, int classificationNClasses,
                                 String classificationPositiveClass, float[] classificationBetas, String suffix) {
        this.model = model;
        this.trainingFile = trainingFile;
        this.validationFile = validationFile;
        this.nEpochs = nEpochs;
        this.batchSize = batchSize;
        this.learningRateMultiplier = learningRateMultiplier;
        this.promptLossWeight = promptLossWeight;
        this.computeClassificationMetrics = computeClassificationMetrics;
        this.classificationNClasses = classificationNClasses;
        this.classificationPositiveClass = classificationPositiveClass;
        this.classificationBetas = classificationBetas;
        this.suffix = suffix;
    }

    /**
     * Returns a new instance of the Builder for constructing UploadFileRequest objects.
     *
     * @return a new instance of the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getModel() {
        return model;
    }

    public String getTrainingFile() {
        return trainingFile;
    }

    public String getValidationFile() {
        return validationFile;
    }

    public int getNEpochs() {
        return nEpochs;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public float getLearningRateMultiplier() {
        return learningRateMultiplier;
    }

    public float getPromptLossWeight() {
        return promptLossWeight;
    }

    public boolean isComputeClassificationMetrics() {
        return computeClassificationMetrics;
    }

    public int getClassificationNClasses() {
        return classificationNClasses;
    }

    public String getClassificationPositiveClass() {
        return classificationPositiveClass;
    }

    public float[] getClassificationBetas() {
        return classificationBetas;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateFineTuneRequest)) return false;
        CreateFineTuneRequest that = (CreateFineTuneRequest) o;
        return nEpochs == that.nEpochs && batchSize == that.batchSize && Float.compare(that.learningRateMultiplier, learningRateMultiplier) == 0 && Float.compare(that.promptLossWeight, promptLossWeight) == 0 && computeClassificationMetrics == that.computeClassificationMetrics && classificationNClasses == that.classificationNClasses && Objects.equals(model, that.model) && Objects.equals(trainingFile, that.trainingFile) && Objects.equals(validationFile, that.validationFile) && Objects.equals(classificationPositiveClass, that.classificationPositiveClass) && Arrays.equals(classificationBetas, that.classificationBetas) && Objects.equals(suffix, that.suffix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(model, trainingFile, validationFile, nEpochs, batchSize, learningRateMultiplier, promptLossWeight, computeClassificationMetrics, classificationNClasses, classificationPositiveClass, suffix);
        result = 31 * result + Arrays.hashCode(classificationBetas);
        return result;
    }

    @Override
    public String toString() {
        return "CreateFineTuneRequest{" +
                "model='" + model + '\'' +
                ", trainingFile='" + trainingFile + '\'' +
                ", validationFile='" + validationFile + '\'' +
                ", nEpochs=" + nEpochs +
                ", batchSize=" + batchSize +
                ", learningRateMultiplier=" + learningRateMultiplier +
                ", promptLossWeight=" + promptLossWeight +
                ", computeClassificationMetrics=" + computeClassificationMetrics +
                ", classificationNClasses=" + classificationNClasses +
                ", classificationPositiveClass='" + classificationPositiveClass + '\'' +
                ", classificationBetas=" + Arrays.toString(classificationBetas) +
                ", suffix='" + suffix + '\'' +
                '}';
    }

    /**
     * Builder for constructing UploadFileRequest instances.
     */
    public static class Builder {

        /**
         * model
         * string
         * Optional
         * Defaults to curie
         * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci", or a
         * fine-tuned model created after 2022-04-21. To learn more about these models, see the Models documentation.
         */
        private String model;

        /**
         * The ID of an uploaded file that contains training data.
         * Your dataset must be formatted as a JSONL file, where each training example is a JSON object
         * with the keys "prompt" and "completion". Additionally, you must upload your file with the purpose fine-tune.
         */
        private String trainingFile;

        /**
         * The ID of an uploaded file that contains validation data.
         * <p>
         * If you provide this file, the data is used to generate validation metrics periodically during fine-tuning.
         * These metrics can be viewed in the fine-tuning results file. Your train and validation data should be mutually exclusive.
         * <p>
         * Your dataset must be formatted as a JSONL file, where each validation example is a JSON object with the keys
         * "prompt" and "completion". Additionally, you must upload your file with the purpose fine-tune.
         */
        private String validationFile;

        /**
         * n_epochs
         * integer
         * Optional
         * Defaults to 4
         * The number of epochs to train the model for. An epoch refers to one full cycle through the training dataset.
         */
        private int nEpochs = 4;

        /**
         * batch_size
         * integer
         * Optional
         * Defaults to null
         * The batch size to use for training. The batch size is the number of training examples used to train a single
         * forward and backward pass.
         * <p>
         * By default, the batch size will be dynamically configured to be ~0.2% of the number of examples in the training
         * set, capped at 256 - in general, we've found that larger batch sizes tend to work better for larger datasets.
         */
        private int batchSize = -1;

        /**
         * learning_rate_multiplier
         * number
         * Optional
         * Defaults to null
         * The learning rate multiplier to use for training. The fine-tuning learning rate is the original learning rate
         * used for pretraining multiplied by this value.
         * <p>
         * By default, the learning rate multiplier is the 0.05, 0.1, or 0.2 depending on final batch_size (larger learning
         * rates tend to perform better with larger batch sizes). We recommend experimenting with values in the range
         * 0.02 to 0.2 to see what produces the best results.
         */
        private float learningRateMultiplier = -1;

        /**
         * prompt_loss_weight
         * number
         * Optional
         * Defaults to 0.01
         * The weight to use for loss on the prompt tokens. This controls how much the model tries to learn to generate
         * the prompt (as compared to the completion which always has a weight of 1.0), and can add a stabilizing effect to
         * training when completions are short.
         * <p>
         * If prompts are extremely long (relative to completions), it may make sense to reduce this weight so as to avoid
         * over-prioritizing learning the prompt.
         */
        private float promptLossWeight = 0.01f;

        /**
         * compute_classification_metrics
         * boolean
         * Optional
         * Defaults to false
         * If set, we calculate classification-specific metrics such as accuracy and F-1 score using the validation set at
         * the end of every epoch. These metrics can be viewed in the results file.
         * <p>
         * In order to compute classification metrics, you must provide a validation_file. Additionally, you must specify
         * classification_n_classes for multiclass classification or classification_positive_class for binary classification.
         */
        private boolean computeClassificationMetrics;

        /**
         * classification_n_classes
         * integer
         * Optional
         * Defaults to null
         * The number of classes in a classification task.
         * <p>
         * This parameter is required for multiclass classification.
         */
        private int classificationNClasses = -1;

        /**
         * classification_positive_class
         * string
         * Optional
         * Defaults to null
         * The positive class in binary classification.
         * <p>
         * This parameter is needed to generate precision, recall, and F1 metrics when doing binary classification.
         */
        private String classificationPositiveClass;

        /**
         * classification_betas
         * array
         * Optional
         * Defaults to null
         * If this is provided, we calculate F-beta scores at the specified beta values. The F-beta score is a generalization of F-1 score. This is only used for binary classification.
         * <p>
         * With a beta of 1 (i.e. the F-1 score), precision and recall are given the same weight. A larger beta score puts more weight on recall and less on precision. A smaller beta score puts more weight on precision and less on recall.
         */
        private float[] classificationBetas;

        /**
         * suffix
         * string
         * Optional
         * Defaults to null
         * A string of up to 40 characters that will be added to your fine-tuned model name.
         * <p>
         * For example, a suffix of "custom-model-name" would produce a model name like ada:ft-your-org:custom-model-name-2022-02-15-04-21-04.
         */
        private String suffix;

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder trainingFile(String trainingFile) {
            this.trainingFile = trainingFile;
            return this;
        }

        public Builder validationFile(String validationFile) {
            this.validationFile = validationFile;
            return this;
        }

        public Builder nEpochs(int nEpochs) {
            this.nEpochs = nEpochs;
            return this;
        }

        public Builder batchSize(int batchSize) {
            this.batchSize = batchSize;
            return this;
        }

        public Builder learningRateMultiplier(float learningRateMultiplier) {
            this.learningRateMultiplier = learningRateMultiplier;
            return this;
        }

        public Builder promptLossWeight(float promptLossWeight) {
            this.promptLossWeight = promptLossWeight;
            return this;
        }

        public Builder computeClassificationMetrics(boolean computeClassificationMetrics) {
            this.computeClassificationMetrics = computeClassificationMetrics;
            return this;
        }

        public Builder classificationNClasses(int classificationNClasses) {
            this.classificationNClasses = classificationNClasses;
            return this;
        }

        public Builder classificationPositiveClass(String classificationPositiveClass) {
            this.classificationPositiveClass = classificationPositiveClass;
            return this;
        }

        public Builder classificationBetas(float[] classificationBetas) {
            this.classificationBetas = classificationBetas;
            return this;
        }

        public Builder suffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        /**
         * Builds a new instance of UploadFileRequest using the configured values.
         *
         * @return a new instance of UploadFileRequest
         */
        public CreateFineTuneRequest build() {
            return new CreateFineTuneRequest(model, trainingFile, validationFile, nEpochs, batchSize, learningRateMultiplier, promptLossWeight,
                    computeClassificationMetrics, classificationNClasses, classificationPositiveClass, classificationBetas, suffix);
        }
    }
}
