package com.cloudurable.jai.model.text.completion;

import com.cloudurable.jai.model.Usage;
import com.cloudurable.jai.model.text.TextResponse;

import java.time.Instant;
import java.util.List;

public class CompletionResponse extends TextResponse {

    private final List<CompletionChoice> choices;
    public CompletionResponse(String id, String object, Instant created, Usage usage, List<CompletionChoice> choices) {
        super(id, object, created, usage);
        this.choices = choices;
    }

    public List<CompletionChoice> getChoices() {
        return choices;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String object;
        private Instant created;
        private Usage usage;
        private List<CompletionChoice> choices;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder usage(Usage usage) {
            this.usage = usage;
            return this;
        }

        public Builder choices(List<CompletionChoice> choices) {
            this.choices = choices;
            return this;
        }

        public CompletionResponse build() {
            return new CompletionResponse(id, object, created, usage, choices);
        }
    }
}
