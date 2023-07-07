package com.cloudurable.jai.model.finetune;

import com.cloudurable.jai.model.file.FileData;
import io.nats.jparse.Json;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FineTuneDataTest {

    @Test
    public void testBuilder() {
        String id = "file-123";
        String object = "file";
        Instant createdAt = Instant.now();
        String fineTunedModel = "model";

        String status = "success";

        Instant updatedAt = Instant.now();
        String organizationId = "org-123";

        FineTuneData fineTuneData = FineTuneData.builder()
                .id(id)
                .object(object)
                .createdAt(createdAt)
                .fineTunedModel(fineTunedModel)
                .hyperParams(Json.toObjectNode("{}"))
                .resultFiles(Json.toArrayNode("[\n" +
                        "    {\n" +
                        "      \"id\": \"file-QQm6ZpqdNwAaVC3aSz5sWwLT\",\n" +
                        "      \"object\": \"file\",\n" +
                        "      \"bytes\": 81509,\n" +
                        "      \"created_at\": 1614807863,\n" +
                        "      \"filename\": \"compiled_results.csv\",\n" +
                        "      \"purpose\": \"fine-tune-results\"\n" +
                        "    }\n" +
                        "  ]"))
                .status(status)
                .trainingFiles(Json.toArrayNode("[\n" +
                        "    {\n" +
                        "      \"id\": \"file-XGinujblHPwGLSztz8cPS8XY\",\n" +
                        "      \"object\": \"file\",\n" +
                        "      \"bytes\": 1547276,\n" +
                        "      \"created_at\": 1610062281,\n" +
                        "      \"filename\": \"my-data-train.jsonl\",\n" +
                        "      \"purpose\": \"fine-tune-train\"\n" +
                        "    }\n" +
                        "  ]"))
                .validationFiles(Json.toArrayNode("[\n" +
                        "    {\n" +
                        "      \"id\": \"file-XGinujblHPwGLSztz8cPS8XY\",\n" +
                        "      \"object\": \"file\",\n" +
                        "      \"bytes\": 1547276,\n" +
                        "      \"created_at\": 1610062281,\n" +
                        "      \"filename\": \"my-data-train.jsonl\",\n" +
                        "      \"purpose\": \"fine-tune-validate\"\n" +
                        "    }\n" +
                        "  ]"))
                .updatedAt(updatedAt)
                .organizationId(organizationId)
                .events(Json.toArrayNode("[\n" +
                        "    {\n" +
                        "      \"object\": \"fine-tune-event\",\n" +
                        "      \"created_at\": 1614807352,\n" +
                        "      \"level\": \"info\",\n" +
                        "      \"message\": \"Job enqueued. Waiting for jobs ahead to complete. Queue number: 0.\"\n" +
                        "    }]"))
                .build();

        assertEquals(id, fineTuneData.getId());
        assertEquals(object, fineTuneData.getObject());
        assertEquals(createdAt, fineTuneData.getCreatedAt());
        assertEquals(fineTunedModel, fineTuneData.getFineTunedModel());
        assertEquals(status, fineTuneData.getStatus());
        assertEquals(updatedAt, fineTuneData.getUpdatedAt());
        assertEquals(organizationId, fineTuneData.getOrganizationId());

        assertEquals("fine-tune-train", fineTuneData.getTrainingFiles().get(0).getPurpose());
        assertEquals("fine-tune-validate", fineTuneData.getValidationFiles().get(0).getPurpose());
        assertEquals("fine-tune-results", fineTuneData.getResultFiles().get(0).getPurpose());
        assertEquals("info", fineTuneData.getEvents().get(0).getLevel());
    }

    @Test
    public void testEmptyResultFiles() {
        FineTuneData fineTuneData = FineTuneData.builder().resultFiles(null).build();
        List<FileData> resultFiles = fineTuneData.getResultFiles();
        assertEquals(Collections.emptyList(), resultFiles);
    }

    @Test
    public void testEmptyTrainingFiles() {
        FineTuneData fineTuneData = FineTuneData.builder().trainingFiles(null).build();
        List<FileData> trainingFiles = fineTuneData.getTrainingFiles();
        assertEquals(Collections.emptyList(), trainingFiles);
    }

    @Test
    public void testEmptyValidationFiles() {
        FineTuneData fineTuneData = FineTuneData.builder().validationFiles(null).build();
        List<FileData> validationFiles = fineTuneData.getValidationFiles();
        assertEquals(Collections.emptyList(), validationFiles);
    }

    @Test
    public void testEmptyEvents() {
        FineTuneData fineTuneData = FineTuneData.builder().events(null).build();
        List<FineTuneEvent> events = fineTuneData.getEvents();
        assertEquals(Collections.emptyList(), events);
    }
}
