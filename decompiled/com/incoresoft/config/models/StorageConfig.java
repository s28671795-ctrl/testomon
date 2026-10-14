/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config.models;

import com.incoresoft.dto.config.storage.LocalStorageConfigDTO;
import com.incoresoft.dto.config.storage.StorageConfigDTO;
import java.util.Map;
import java.util.Optional;

public final class StorageConfig {
    private final Map<String, String> properties;
    private final StorageConfigDTO dto;

    public StorageConfig(StorageConfigDTO dto) {
        Map<String, String> properties = dto.getProperties();
        properties.computeIfPresent("path", (key, value) -> value.replace("\\\\", "/"));
        this.properties = properties;
        this.dto = dto;
    }

    public Optional<String> localDirectory() {
        StorageConfigDTO storageConfigDTO = this.dto;
        if (storageConfigDTO instanceof LocalStorageConfigDTO) {
            LocalStorageConfigDTO localStorageDto = (LocalStorageConfigDTO)storageConfigDTO;
            return Optional.of(localStorageDto.getPath());
        }
        return Optional.empty();
    }

    public StorageConfigDTO toDto() {
        return this.dto;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public StorageConfigDTO getDto() {
        return this.dto;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StorageConfig)) {
            return false;
        }
        StorageConfig other = (StorageConfig)o;
        Map<String, String> this$properties = this.getProperties();
        Map<String, String> other$properties = other.getProperties();
        if (this$properties == null ? other$properties != null : !((Object)this$properties).equals(other$properties)) {
            return false;
        }
        StorageConfigDTO this$dto = this.getDto();
        StorageConfigDTO other$dto = other.getDto();
        return !(this$dto == null ? other$dto != null : !this$dto.equals(other$dto));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Map<String, String> $properties = this.getProperties();
        result = result * 59 + ($properties == null ? 43 : ((Object)$properties).hashCode());
        StorageConfigDTO $dto = this.getDto();
        result = result * 59 + ($dto == null ? 43 : $dto.hashCode());
        return result;
    }

    public String toString() {
        return "StorageConfig(properties=" + String.valueOf(this.getProperties()) + ", dto=" + String.valueOf(this.getDto()) + ")";
    }

    public StorageConfig(Map<String, String> properties, StorageConfigDTO dto) {
        this.properties = properties;
        this.dto = dto;
    }
}

