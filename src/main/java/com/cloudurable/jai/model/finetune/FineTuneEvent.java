package com.cloudurable.jai.model.finetune;

import java.time.Instant;
import java.util.Objects;

public class FineTuneEvent {

       /*   "object": "fine-tune-event",
                  "created_at": 1614807356,
                  "level": "info",
                  "message": "Job started."
        */

    private final String object;
    private final String level;
    private final String message;
    private final Instant createdAt;

    private FineTuneEvent(String object, String level, String message, Instant createdAt) {
        this.object = object;
        this.level = level;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getObject() {
        return object;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FineTuneEvent)) return false;
        FineTuneEvent that = (FineTuneEvent) o;
        return Objects.equals(object, that.object) && Objects.equals(level, that.level) && Objects.equals(message, that.message) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, level, message, createdAt);
    }

    @Override
    public String toString() {
        return "FineTuneEvent{" +
                "object='" + object + '\'' +
                ", level='" + level + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Builder {
        private String object;
        private String level;
        private String message;
        private Instant createdAt;
        private Builder() {
        }

        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FineTuneEvent build() {
            return new FineTuneEvent(object, level, message, createdAt);
        }
    }
}
