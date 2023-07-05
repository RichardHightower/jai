package com.cloudurable.jai.model.model;

import io.nats.jparse.node.ArrayNode;

import java.util.Objects;

/**
 * Represents model data.
 */
public class ModelData {
    private final String id;
    private final String object;
    private final String ownedBy;
    private final ArrayNode permission;

    /**
     * Constructs a new instance of ModelData.
     *
     * @param id         the ID of the model
     * @param object     the object of the model
     * @param ownedBy    the owner of the model
     * @param permission the permission of the model
     */
    private ModelData(String id, String object, String ownedBy, ArrayNode permission) {
        this.id = id;
        this.object = object;
        this.ownedBy = ownedBy;
        this.permission = permission;
    }

    /**
     * Creates a new instance of the ModelData.Builder.
     *
     * @return a new instance of the ModelData.Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets the ID of the model.
     *
     * @return the ID of the model
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the object of the model.
     *
     * @return the object of the model
     */
    public String getObject() {
        return object;
    }

    /**
     * Gets the owner of the model.
     *
     * @return the owner of the model
     */
    public String getOwnedBy() {
        return ownedBy;
    }

    /**
     * Gets the permission of the model.
     *
     * @return the permission of the model
     */
    public ArrayNode getPermission() {
        return permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelData)) return false;
        ModelData modelData = (ModelData) o;
        return Objects.equals(id, modelData.id) && Objects.equals(object, modelData.object)
                && Objects.equals(ownedBy, modelData.ownedBy)
                && Objects.equals(permission, modelData.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, object, ownedBy, permission);
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", ownedBy='" + ownedBy + '\'' +
                ", permission=" + permission +
                '}';
    }

    /**
     * Builder for constructing ModelData instances.
     */
    public static class Builder {
        private String id;
        private String object;
        private String ownedBy;
        private ArrayNode permission;

        private Builder() {
        }

        /**
         * Sets the ID of the model.
         *
         * @param id the ID of the model
         * @return the builder instance
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the object of the model.
         *
         * @param object the object of the model
         * @return the builder instance
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Sets the owner of the model.
         *
         * @param ownedBy the owner of the model
         * @return the builder instance
         */
        public Builder ownedBy(String ownedBy) {
            this.ownedBy = ownedBy;
            return this;
        }

        /**
         * Sets the permission of the model.
         *
         * @param permission the permission of the model
         * @return the builder instance
         */
        public Builder permission(ArrayNode permission) {
            this.permission = permission;
            return this;
        }

        /**
         * Builds a new instance of ModelData.
         *
         * @return a new instance of ModelData
         */
        public ModelData build() {
            return new ModelData(id, object, ownedBy, permission);
        }
    }
}
