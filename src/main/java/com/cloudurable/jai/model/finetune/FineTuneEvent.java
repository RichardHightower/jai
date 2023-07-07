/**

 The FineTuneEvent class represents an event associated with fine-tuning.
 It contains information about the object, level, message, and creation timestamp of the event.
 */
package com.cloudurable.jai.model.finetune;
import java.time.Instant;
import java.util.Objects;

/**
 * FineTuneEvent
 */
public class FineTuneEvent {

    private final String object;
    private final String level;
    private final String message;
    private final Instant createdAt;

    /**
     * Constructs a new instance of FineTuneEvent with the specified parameters.
     *
     * @param object    The object associated with the event.
     * @param level     The level of the event.
     * @param message   The message of the event.
     * @param createdAt The creation timestamp of the event.
     */
    private FineTuneEvent(String object, String level, String message, Instant createdAt) {
        this.object = object;
        this.level = level;
        this.message = message;
        this.createdAt = createdAt;
    }

    /**
     * Returns a new instance of the Builder for constructing FineTuneEvent objects.
     *
     * @return A new instance of the Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the object associated with the event.
     *
     * @return The object associated with the event.
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets the level of the event.
     *
     * @return The level of the event.
     */
    public String getLevel() {
        return level;
    }

    /**
     * Gets the message of the event.
     *
     * @return The message of the event.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the creation timestamp of the event.
     *
     * @return The creation timestamp of the event.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FineTuneEvent)) return false;
        FineTuneEvent that = (FineTuneEvent) o;
        return Objects.equals(object, that.object) && Objects.equals(level, that.level)
                && Objects.equals(message, that.message) && Objects.equals(createdAt, that.createdAt);
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

    /**
     * The Builder class for constructing FineTuneEvent instances.
     */
    public static class Builder {
        private String object;
        private String level;
        private String message;
        private Instant createdAt;

        private Builder() {
            // Private constructor to prevent instantiation
        }

        /**
         * Sets the object associated with the event.
         *
         * @param object The object associated with the event.
         * @return The builder instance.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the level of the event.
         *
         * @param level The level of the event.
         * @return The builder instance.
         */
        public Builder level(String level) {
            this.level = level;
            return this;
        }

        /**
         * Sets the message of the event.
         *
         * @param message The message of the event.
         * @return The builder instance.
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the creation timestamp of the event.
         *
         * @param createdAt The creation timestamp of the event.
         * @return The builder instance.
         */
        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Builds a new instance of FineTuneEvent using the configured values.
         *
         * @return A new instance of FineTuneEvent.
         */
        public FineTuneEvent build() {
            return new FineTuneEvent(object, level, message, createdAt);
        }
    }
}
