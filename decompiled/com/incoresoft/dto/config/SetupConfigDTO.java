/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.dto.config.SetupDatabaseConfigDTO;
import com.incoresoft.dto.config.storage.SetupS3StorageConfigDTO;
import com.incoresoft.dto.config.storage.SetupStorageConfigDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonDeserialize(builder=SetupConfigDTOBuilder.class)
public final class SetupConfigDTO {
    @JsonProperty(value="database")
    @Schema(name="database", description="Database config", implementation=SetupDatabaseConfigDTO.class)
    @NotNull
    @Valid
    private final SetupDatabaseConfigDTO database;
    @JsonProperty(value="storage")
    @Schema(name="storage", description="Storage config", implementation=SetupS3StorageConfigDTO.class)
    @Valid
    private final SetupStorageConfigDTO storage;

    public static SetupConfigDTOBuilder builder() {
        return new SetupConfigDTOBuilder();
    }

    public SetupDatabaseConfigDTO getDatabase() {
        return this.database;
    }

    public SetupStorageConfigDTO getStorage() {
        return this.storage;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SetupConfigDTO)) {
            return false;
        }
        SetupConfigDTO other = (SetupConfigDTO)o;
        SetupDatabaseConfigDTO this$database = this.getDatabase();
        SetupDatabaseConfigDTO other$database = other.getDatabase();
        if (this$database == null ? other$database != null : !((Object)this$database).equals(other$database)) {
            return false;
        }
        SetupStorageConfigDTO this$storage = this.getStorage();
        SetupStorageConfigDTO other$storage = other.getStorage();
        return !(this$storage == null ? other$storage != null : !this$storage.equals(other$storage));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        SetupDatabaseConfigDTO $database = this.getDatabase();
        result = result * 59 + ($database == null ? 43 : ((Object)$database).hashCode());
        SetupStorageConfigDTO $storage = this.getStorage();
        result = result * 59 + ($storage == null ? 43 : $storage.hashCode());
        return result;
    }

    public String toString() {
        return "SetupConfigDTO(database=" + String.valueOf(this.getDatabase()) + ", storage=" + String.valueOf(this.getStorage()) + ")";
    }

    public SetupConfigDTO(SetupDatabaseConfigDTO database, SetupStorageConfigDTO storage) {
        this.database = database;
        this.storage = storage;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class SetupConfigDTOBuilder {
        private SetupDatabaseConfigDTO database;
        private SetupStorageConfigDTO storage;

        SetupConfigDTOBuilder() {
        }

        @JsonProperty(value="database")
        public SetupConfigDTOBuilder database(SetupDatabaseConfigDTO database) {
            this.database = database;
            return this;
        }

        @JsonProperty(value="storage")
        public SetupConfigDTOBuilder storage(SetupStorageConfigDTO storage) {
            this.storage = storage;
            return this;
        }

        public SetupConfigDTO build() {
            return new SetupConfigDTO(this.database, this.storage);
        }

        public String toString() {
            return "SetupConfigDTO.SetupConfigDTOBuilder(database=" + String.valueOf(this.database) + ", storage=" + String.valueOf(this.storage) + ")";
        }
    }

    public static final class Fields {
        public static final String database = "database";
        public static final String storage = "storage";
    }
}

