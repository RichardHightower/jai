package com.cloudurable.jai.model.text;

import com.cloudurable.jai.model.Response;
import com.cloudurable.jai.model.Usage;

import java.time.Instant;
import java.util.Objects;

public abstract class TextResponse implements Response {
    protected final String id;
    protected final String object;
    protected final Instant created;
    protected final Usage usage;

    public TextResponse(String id, String object, Instant created, Usage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.usage = usage;
    }

    /**
     * Returns the ID of the chat response.
     *
     * @return The ID of the chat response.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the object of the chat response.
     *
     * @return The object of the chat response.
     */
    public String getObject() {
        return object;
    }

    /**
     * Returns the creation time of the chat response.
     *
     * @return The creation time of the chat response.
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * Returns the usage statistics for the chat response.
     *
     * @return The usage statistics for the chat response.
     */
    public Usage getUsage() {
        return usage;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextResponse)) return false;
        TextResponse that = (TextResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(object, that.object) && Objects.equals(created, that.created) && Objects.equals(usage, that.usage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, created, usage);
    }

    @Override
    public String toString() {
        return "TextResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", usage=" + usage +
                '}';
    }
}
