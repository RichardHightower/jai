package com.cloudurable.jai.model.model;

import io.nats.jparse.node.ArrayNode;

import java.util.Objects;

public class ModelData {
    private final String id;
    private final String object;

    private final String ownedBy;
    private final ArrayNode permission;

    private ModelData(String id, String object, String organizationOwner, ArrayNode permission) {
        this.id = id;
        this.object = object;
        this.ownedBy = organizationOwner;
        this.permission = permission;
    }


    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

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
                ", organizationOwner='" + ownedBy + '\'' +
                ", permission=" + permission +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder(){}
        private String id;
        private String object;
        private String ownedBy;
        private ArrayNode permission;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Builder ownedBy(String organizationOwner) {
            this.ownedBy = organizationOwner;
            return this;
        }

        public Builder permission(ArrayNode permission) {
            this.permission = permission;
            return this;
        }

        public ModelData build() {
            return new ModelData(id, object, ownedBy, permission);
        }
    }


}
