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


    private FineTuneData(String id, String object, Instant createdAt, String fineTunedModel,
                         ObjectNode hyperParams, ArrayNode resultFiles, String status,
                         ArrayNode trainingFiles, ArrayNode validationFiles, Instant updatedAt, String organizationId, ArrayNode events) {
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
     * Creates a new instance of the Builder for constructing FileData instances.
     *
     * @return a new instance of the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the ID of the file.
     *
     * @return the ID of the file
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the object type of the file.
     *
     * @return the object type of the file
     */
    public String getObject() {
        return object;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getFineTunedModel() {
        return fineTunedModel;
    }

    public ObjectNode getHyperParams() {
        return hyperParams;
    }

    public List<FileData> getResultFiles() {
        if (resultFiles == null) {
            return Collections.emptyList();
        }
        return resultFiles.mapObjectNode(FileDataDeserializer::getFileData);
    }

    public String getStatus() {
        return status;
    }

    public List<FileData> getTrainingFiles() {
        if (trainingFiles == null) {
            return Collections.emptyList();
        }
        return trainingFiles.mapObjectNode(FileDataDeserializer::getFileData);
    }

    public List<FileData> getValidationFiles() {
        if (validationFiles == null) {
            return Collections.emptyList();
        }
        return validationFiles.mapObjectNode(FileDataDeserializer::getFileData);
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

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
        return Objects.equals(id, that.id) && Objects.equals(object, that.object)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(fineTunedModel, that.fineTunedModel)
                && Objects.equals(hyperParams, that.hyperParams) && Objects.equals(resultFiles, that.resultFiles)
                && Objects.equals(status, that.status) && Objects.equals(trainingFiles, that.trainingFiles)
                && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, createdAt, fineTunedModel, hyperParams, resultFiles, status, trainingFiles, updatedAt, organizationId);
    }

    @Override
    public String toString() {
        return "FineTuneData{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", createdAt=" + createdAt +
                ", fineTunedModel=" + fineTunedModel +
                ", hyperParams=" + hyperParams +
                ", resultFiles=" + resultFiles +
                ", status='" + status + '\'' +
                ", trainingFiles=" + trainingFiles +
                ", organizationId=" + organizationId +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Builder for constructing FileData instances.
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
        private Instant updatedAt;
        private String organizationId;

        private ArrayNode events;


        private ArrayNode validationFiles;


        private Builder() {
        }


        public Builder events(ArrayNode events) {
            this.events = events;
            return this;
        }

        public Builder validationFiles(ArrayNode validationFiles) {
            this.validationFiles = validationFiles;
            return this;
        }

        public Builder fineTunedModel(String fineTunedModel) {
            this.fineTunedModel = fineTunedModel;
            return this;
        }

        public Builder hyperParams(ObjectNode hyperParams) {
            this.hyperParams = hyperParams;
            return this;
        }

        public Builder resultFiles(ArrayNode resultFiles) {
            this.resultFiles = resultFiles;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder trainingFiles(ArrayNode trainingFiles) {
            this.trainingFiles = trainingFiles;
            return this;
        }

        public Builder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        /**
         * Sets the creation timestamp of the file.
         *
         * @param createdAt createdAt
         * @return the builder instance
         */
        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }


        /**
         * Sets the ID of the file.
         *
         * @param id the ID of the file
         * @return the builder instance
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object type of the file.
         *
         * @param object the object type of the file
         * @return the builder instance
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Builder organizationId(String organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        /**
         * Builds a new instance of FileData using the configured values.
         *
         * @return a new instance of FileData
         */
        public FineTuneData build() {
            return new FineTuneData(id, object, createdAt, fineTunedModel, hyperParams, resultFiles, status, trainingFiles, validationFiles, updatedAt, organizationId, events);
        }


    }
}
