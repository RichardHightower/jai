package com.cloudurable.jai.model.finetune;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FineTuneDataDeserializerTest {

    @Test
    public void testDeserialize() {
        String json = "{\"object\": \"file\", " +
                "\"id\": \"file-123\", " +
                "\"created_at\": 1625544000, " +
                "\"updated_at\": 1625544000, " +
                "\"fine_tuned_model\": \"model\", " +
                "\"hyperparams\": {}, " +
                "\"organization_id\": \"org-123\", " +
                "\"result_files\": [\n" +
                "    {\n" +
                "      \"id\": \"file-QQm6ZpqdNwAaVC3aSz5sWwLT\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 81509,\n" +
                "      \"created_at\": 1614807863,\n" +
                "      \"filename\": \"compiled_results.csv\",\n" +
                "      \"purpose\": \"fine-tune-results\"\n" +
                "    }\n" +
                "  ], " +
                "\"status\": \"success\", " +
                "\"validation_files\": [\n" +
                "    {\n" +
                "      \"id\": \"file-XGinujblHPwGLSztz8cPS8XY\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 1547276,\n" +
                "      \"created_at\": 1610062281,\n" +
                "      \"filename\": \"my-data-train.jsonl\",\n" +
                "      \"purpose\": \"fine-tune-validate\"\n" +
                "    }\n" +
                "  ], " +
                "\"events\": [\n" +
                "    {\n" +
                "      \"object\": \"fine-tune-event\",\n" +
                "      \"created_at\": 1614807352,\n" +
                "      \"level\": \"info\",\n" +
                "      \"message\": \"Job enqueued. Waiting for jobs ahead to complete. Queue number: 0.\"\n" +
                "    }], " +
                "\"training_files\": [\n" +
                "    {\n" +
                "      \"id\": \"file-XGinujblHPwGLSztz8cPS8XY\",\n" +
                "      \"object\": \"file\",\n" +
                "      \"bytes\": 1547276,\n" +
                "      \"created_at\": 1610062281,\n" +
                "      \"filename\": \"my-data-train.jsonl\",\n" +
                "      \"purpose\": \"fine-tune-train\"\n" +
                "    }\n" +
                "  ]}";


        FineTuneData fineTuneData = FineTuneDataDeserializer.deserialize(json);


        assertEquals("fine-tune-train", fineTuneData.getTrainingFiles().get(0).getPurpose());
        assertEquals("fine-tune-validate", fineTuneData.getValidationFiles().get(0).getPurpose());
        assertEquals("fine-tune-results", fineTuneData.getResultFiles().get(0).getPurpose());
        assertEquals("info", fineTuneData.getEvents().get(0).getLevel());


    }

}
