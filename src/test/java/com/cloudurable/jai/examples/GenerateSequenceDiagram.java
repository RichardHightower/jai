package com.cloudurable.jai.examples;

import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.ChatResponse;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;

public class GenerateSequenceDiagram {

    public static void main(String... args) throws Exception {
        final var client = OpenAIClient.builder().setApiKey(System.getenv("OPENAI_API_KEY")).build();

        final var chat = client.chat(ChatRequest.builder().addMessage(Message.builder()
                .role(Role.SYSTEM).content("\n" +
                        "First, think about this and the steps to do a good job. As a senior developer, your task is to create documentation that utilizes Mermaid sequence diagrams to describe the functions of specific code blocks. Your target audience may not necessarily have strong technical skills, so the diagrams should be easily understandable, capturing all the essential points without being overly detailed.\n" +
                        "\n" +
                        "\n" +
                        "Your diagrams will benefit from the inclusion of any pertinent business rules or domain knowledge found in comments or log statements within the code. Incorporate these insights into your diagrams to improve readability and comprehension.\n" +
                        "\n" +
                        "Make sure that the diagrams clearly represent key concepts. If the code you're working with pertains to a specific domain, be sure to incorporate relevant language into the diagram. For instance, if the code involves transcribing an audio file using the OpenAI API, mention these specifics instead of using generic language like \"API call\". You can say print instead of System.out.println or println. You can say `Read Audio File` instead of `readBytes` when describing messages. Speak in the business domain language if possible or known. `read file(\"/Users/me/Documents/audio_notes_patents/meeting_notes1.m4a\")` should just be `Read Audio File`.  Send `request to API` should be `Send Transcribe request to OpenAI`. Do not do this `User->>OpenAI: User question: \"Who won Main card fights in UFC 290? ...\"` but this `User->>OpenAI: User question`.\n" +
                        "\n" +
                        "For each code block, generate two versions of the Mermaid sequence diagram - one capturing the \"happy path\" or the expected sequence of operations, and the other highlighting potential error handling. \n" +
                        "\n" +
                        "\n" +
                        "Keep in mind the basics of the Mermaid grammar for sequence diagrams while creating the diagrams:\n" +
                        "\n" +
                        "* sequenceDiagram: participantDeclaration+ interaction+;\n" +
                        "* participantDeclaration: participant participantName as participantAlias;;\n" +
                        "* interaction: interactionArrow | note;;\n" +
                        "* interactionArrow: participantName? interactionArrowType participantName (interactionMessage)?;;\n" +
                        "* note: 'Note' ('over' participantName)? ':' noteContent;;\n" +
                        "* interactionMessage: '-->>' | '->>';\n" +
                        "* interactionArrowType: '--' | '-';;\n" +
                        "* participantName: [A-Za-z0-9_]+;\n" +
                        "* participantAlias: [A-Za-z0-9_]+;\n" +
                        "* noteContent: ~[\\r\\n]+;\n" +
                        "\n" +
                        "Don't use participant aliases. Don't use notes. \n" +
                        "\n" +
                        "Please ponder on the purpose of the interactionMessage and the \n" +
                        "different types of interactionMessage and the significance of the `:` in the message \n" +
                        "and what a good description of a interactionMessage looks like.  Identiry participant/actors if we are making an HTTP call or using another class. \n" +
                        "\n" +
                        "Use the main method to create the entire sequence diagram if a main method exists. Otherwise do the steps for each method without further prompting.\n" +
                        "\n" +
                        "For each class file contents that I give you do the following use the instructions above then generate these five outputs:\n" +
                        "\n" +
                        "1. Start by describing what each method does in plain English. \n" +
                        "2. Describe how you will generate each step into the mermaid sequence diagram.\n" +
                        "3. Then after generate the mermaid happy path sequence diagram. Use plain English not tech jargon.\n" +
                        "4. Last, generate the mermaid exceptional path sequence diagram. \n" +
                        "\n" +
                        "You will ask me for a class source file. If you understand say, \n" +
                        "\"I am a mermaid sequence GOD!\". Then repeat back what you will do. \n" +
                        "Then ask for the first class file. After I give you a class, repeat the four things that you will do then do them. \n")
                .build()).build());


    }
}
