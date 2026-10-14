/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.incoresoft.dto.config.storage.StorageType;
import com.incoresoft.dto.config.storage.deserialization.StorageConfigDTODeserializer;
import com.incoresoft.middleware.http.serialization.Serialization;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(name="StorageConfigDTO", description="Storage configuration")
@JsonDeserialize(using=StorageConfigDTODeserializer.class)
public interface StorageConfigDTO {
    public static final String TYPE_FIELD_NAME = "type";

    @JsonProperty(value="type")
    @JSONField(name="type")
    @Schema(name="type", description="Storage type", example="local")
    default public StorageType getType() {
        return StorageType.getByConfigDTOClass(this.getClass());
    }

    @JsonIgnore
    default public Map<String, String> getProperties() {
        return Serialization.objectMapper().convertValue((Object)this, new TypeReference<Map<String, String>>(this){});
    }
}

