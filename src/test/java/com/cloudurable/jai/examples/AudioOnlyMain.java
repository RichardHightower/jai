package com.cloudurable.jai.examples;

import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.audio.AudioResponse;
import com.cloudurable.jai.model.audio.TranscriptionRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class AudioOnlyMain {
    public static void main(final String... args) {
        try {


            File file1 = new File("/Users/richardhightower/thebouqs/notes/august3rd/TheBouqDevOps.m4a");
            File file2 = new File("/Users/richardhightower/thebouqs/notes/august3rd/TheBouqDevOps2.m4a");
            File file3 = new File("/Users/richardhightower/thebouqs/notes/august3rd/TheBouqDevOps3.m4a");

            File[] files = new File[]{file1, file2, file3};
            for (File file : files) {
                String results = callTranscribe(file);
                System.out.println("File Done ------------ ");
                System.out.println(results);
                System.out.println();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }



    private static String callTranscribe(File file) throws IOException {
        // Create the client

        final var openAiKey = System.getenv("OPENAI_API_KEY");
        final OpenAIClient client = OpenAIClient.builder().setApiKey(openAiKey).build();



        byte[] bytes = Files.readAllBytes(file.toPath());
        // Create the chat request
        final TranscriptionRequest request = TranscriptionRequest.builder()
                .model("whisper-1").prompt("Write up notes").language("en").file(bytes)
                .build();


        // Call Open AI API with chat message
        final ClientResponse<TranscriptionRequest, AudioResponse> response = client.transcribe(request);

        response.getException().ifPresent(Throwable::printStackTrace);


        response.getStatusMessage().ifPresent(error -> System.out.printf("status message %s %d \n", error, response.getStatusCode().orElse(0)));

        response.getStatusCode().ifPresent(code -> System.out.println("RESON"));
        if (response.getResponse().isPresent()) {
            return response.getResponse().get().getBody();
        }

        return "";

    }


}
