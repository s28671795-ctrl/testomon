/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.incoresoft.dto.config.storage.StorageType;
import com.incoresoft.dto.config.storage.deserialization.SetupStorageConfigDTODeserializer;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="Base interface for all storage configuration DTOs")
@JsonDeserialize(using=SetupStorageConfigDTODeserializer.class)
public interface SetupStorageConfigDTO {
    public static final String TYPE_FIELD_NAME = "type";

    @JsonProperty(value="type")
    @Schema(name="type", description="Type of storage", example="local")
    default public StorageType getType() {
        return StorageType.getBySetupConfigDTOClass(this.getClass());
    }
}

