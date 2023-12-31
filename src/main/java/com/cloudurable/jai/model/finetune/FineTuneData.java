/**

 The FineTuneData class represents fine-tuned data for a model.
 It contains information about the fine-tuned model, hyperparameters, result files,
 training files, validation files, status, organization ID, and events.
 */
package com.cloudurable.jai.model.finetune;
import com.cloudurable.jai.model.file.FileData;
import com.cloudurable.jai.model.file.FileDataDeserializer;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.cloudurable.jai.model.finetune.ListFineTuneEventResponseDeserializer.getEventData;

/**
 * Fine Tune Data.
 */
public class FineTuneData {
    private final String id;
    private final String object;
    private final Instant createdAt;
    private final String fineTunedModel;
    private final ObjectNode hyperParams;
    private final ArrayNode resultFiles;
    private final String status;
    private final ArrayNode trainingFiles;
    private final ArrayNode validationFiles;
    private final Instant updatedAt;
    private final String organizationId;
    private final ArrayNode events;

    /**
     * Constructs a new instance of FineTuneData with the specified parameters.
     *
     * @param id              The ID of the fine-tuned data.
     * @param object          The object type of the fine-tuned data.
     * @param createdAt       The creation timestamp of the fine-tuned data.
     * @param fineTunedModel  The fine-tuned model.
     * @param hyperParams     The hyperparameters of the fine-tuned data.
     * @param resultFiles     The result files generated by the fine-tuning process.
     * @param status          The status of the fine-tuning process.
     * @param trainingFiles   The training files used for fine-tuning.
     * @param validationFiles The validation files used for fine-tuning.
     * @param updatedAt       The update timestamp of the fine-tuned data.
     * @param organizationId  The organization ID associated with the fine-tuned data.
     * @param events          The events associated with the fine-tuning process.
     */
    private FineTuneData(String id, String object, Instant createdAt, String fineTunedModel,
                         ObjectNode hyperParams, ArrayNode resultFiles, String status,
                         ArrayNode trainingFiles, ArrayNode validationFiles, Instant updatedAt,
                         String organizationId, ArrayNode events) {
        this.id = id;
        this.object = object;
        this.createdAt = createdAt;
        this.fineTunedModel = fineTunedModel;
        this.hyperParams = hyperParams;
        this.resultFiles = resultFiles;
        this.status = status;
        this.trainingFiles = trainingFiles;
        this.validationFiles = validationFiles;
        this.updatedAt = updatedAt;
        this.organizationId = organizationId;
        this.events = events;
    }

    /**
     * Returns a new instance of the Builder for constructing FineTuneData objects.
     *
     * @return A new instance of the Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the ID of the fine-tuned data.
     *
     * @return The ID of the fine-tuned data.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the object type of the fine-tuned data.
     *
     * @return The object type of the fine-tuned data.
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets the creation timestamp of the fine-tuned data.
     *
     * @return The creation timestamp of the fine-tuned data.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the fine-tuned model.
     *
     * @return The fine-tuned model.
     */
    public String getFineTunedModel() {
        return fineTunedModel;
    }

    /**
     * Gets the hyperparameters of the fine-tuned data.
     *
     * @return The hyperparameters of the fine-tuned data.
     */
    public ObjectNode getHyperParams() {
        return hyperParams;
    }

    /**
     * Gets the result files generated by the fine-tuning process.
     *
     * @return A list of FileData objects representing the result files.
     */
    public List<FileData> getResultFiles() {
        if (resultFiles == null) {
            return Collections.emptyList();
        }
        return resultFiles.mapObjectNode(FileDataDeserializer::getFileData);
    }

    /**
     * Gets the status of the fine-tuning process.
     *
     * @return The status of the fine-tuning process.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the training files used for fine-tuning.
     *
     * @return A list of FileData objects representing the training files.
     */
    public List<FileData> getTrainingFiles() {
        if (trainingFiles == null) {
            return Collections.emptyList();
        }
        return trainingFiles.mapObjectNode(FileDataDeserializer::getFileData);
    }

    /**
     * Gets the validation files used for fine-tuning.
     *
     * @return A list of FileData objects representing the validation files.
     */
    public List<FileData> getValidationFiles() {
        if (validationFiles == null) {
            return Collections.emptyList();
        }
        return validationFiles.mapObjectNode(FileDataDeserializer::getFileData);
    }

    /**
     * Gets the update timestamp of the fine-tuned data.
     *
     * @return The update timestamp of the fine-tuned data.
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Gets the organization ID associated with the fine-tuned data.
     *
     * @return The organization ID associated with the fine-tuned data.
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Gets the events associated with the fine-tuning process.
     *
     * @return A list of FineTuneEvent objects representing the events.
     */
    public List<FineTuneEvent> getEvents() {
        if (events == null) {
            return Collections.emptyList();
        }
        return getEventData(events);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FineTuneData)) return false;
        FineTuneData that = (FineTuneData) o;
        return Objects.equals(id, that.id) && Objects.equals(object, that.object) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(fineTunedModel, that.fineTunedModel) &&
                Objects.equals(hyperParams, that.hyperParams) &&
                Objects.equals(resultFiles, that.resultFiles) &&
                Objects.equals(status, that.status) &&
                Objects.equals(trainingFiles, that.trainingFiles) &&
                Objects.equals(validationFiles, that.validationFiles) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, createdAt, fineTunedModel, hyperParams,
                resultFiles, status, trainingFiles, validationFiles, updatedAt, organizationId);
    }

    @Override
    public String toString() {
        return "FineTuneData{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", createdAt=" + createdAt +
                ", fineTunedModel='" + fineTunedModel + '\'' +
                ", hyperParams=" + hyperParams +
                ", resultFiles=" + resultFiles +
                ", status='" + status + '\'' +
                ", trainingFiles=" + trainingFiles +
                ", validationFiles=" + validationFiles +
                ", updatedAt=" + updatedAt +
                ", organizationId='" + organizationId + '\'' +
                ", events=" + events +
                '}';
    }

    /**
     * The Builder class for constructing FineTuneData instances.
     */
    public static class Builder {
        private String id;
        private String object;
        private Instant createdAt;
        private String fineTunedModel;
        private ObjectNode hyperParams;
        private ArrayNode resultFiles;
        private String status;
        private ArrayNode trainingFiles;
        private ArrayNode validationFiles;
        private Instant updatedAt;
        private String organizationId;
        private ArrayNode events;

        private Builder() {
            // Private constructor to prevent instantiation
        }

        /**
         * Sets the ID of the fine-tuned data.
         *
         * @param id The ID of the fine-tuned data.
         * @return The builder instance.
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object type of the fine-tuned data.
         *
         * @param object The object type of the fine-tuned data.
         * @return The builder instance.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the creation timestamp of the fine-tuned data.
         *
         * @param createdAt The creation timestamp of the fine-tuned data.
         * @return The builder instance.
         */
        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Sets the fine-tuned model.
         *
         * @param fineTunedModel The fine-tuned model.
         * @return The builder instance.
         */
        public Builder fineTunedModel(String fineTunedModel) {
            this.fineTunedModel = fineTunedModel;
            return this;
        }

        /**
         * Sets the hyperparameters of the fine-tuned data.
         *
         * @param hyperParams The hyperparameters of the fine-tuned data.
         * @return The builder instance.
         */
        public Builder hyperParams(ObjectNode hyperParams) {
            this.hyperParams = hyperParams;
            return this;
        }

        /**
         * Sets the result files generated by the fine-tuning process.
         *
         * @param resultFiles The result files generated by the fine-tuning process.
         * @return The builder instance.
         */
        public Builder resultFiles(ArrayNode resultFiles) {
            this.resultFiles = resultFiles;
            return this;
        }

        /**
         * Sets the status of the fine-tuning process.
         *
         * @param status The status of the fine-tuning process.
         * @return The builder instance.
         */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the training files used for fine-tuning.
         *
         * @param trainingFiles The training files used for fine-tuning.
         * @return The builder instance.
         */
        public Builder trainingFiles(ArrayNode trainingFiles) {
            this.trainingFiles = trainingFiles;
            return this;
        }

        /**
         * Sets the validation files used for fine-tuning.
         *
         * @param validationFiles The validation files used for fine-tuning.
         * @return The builder instance.
         */
        public Builder validationFiles(ArrayNode validationFiles) {
            this.validationFiles = validationFiles;
            return this;
        }

        /**
         * Sets the update timestamp of the fine-tuned data.
         *
         * @param updatedAt The update timestamp of the fine-tuned data.
         * @return The builder instance.
         */
        public Builder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        /**
         * Sets the organization ID associated with the fine-tuned data.
         *
         * @param organizationId The organization ID associated with the fine-tuned data.
         * @return The builder instance.
         */
        public Builder organizationId(String organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        /**
         * Sets the events associated with the fine-tuning process.
         *
         * @param events The events associated with the fine-tuning process.
         * @return The builder instance.
         */
        public Builder events(ArrayNode events) {
            this.events = events;
            return this;
        }

        /**
         * Builds a new instance of FineTuneData using the configured values.
         *
         * @return A new instance of FineTuneData.
         */
        public FineTuneData build() {
            return new FineTuneData(id, object, createdAt, fineTunedModel, hyperParams,
                    resultFiles, status, trainingFiles, validationFiles, updatedAt, organizationId, events);
        }
    }
}
