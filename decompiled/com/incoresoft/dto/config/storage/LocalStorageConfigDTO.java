/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.dto.config.storage.StorageConfigDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="LocalStorageConfigDTO", description="Local storage configuration DTO")
@JsonDeserialize(builder=LocalStorageConfigDTOBuilder.class)
public final class LocalStorageConfigDTO
implements StorageConfigDTO {
    @JsonProperty(value="path")
    @Schema(description="Path to the local storage directory", example="../storage")
    private final String path;

    public static LocalStorageConfigDTOBuilder builder() {
        return new LocalStorageConfigDTOBuilder();
    }

    public String getPath() {
        return this.path;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LocalStorageConfigDTO)) {
            return false;
        }
        LocalStorageConfigDTO other = (LocalStorageConfigDTO)o;
        String this$path = this.getPath();
        String other$path = other.getPath();
        return !(this$path == null ? other$path != null : !this$path.equals(other$path));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : $path.hashCode());
        return result;
    }

    public String toString() {
        return "LocalStorageConfigDTO(path=" + this.getPath() + ")";
    }

    public LocalStorageConfigDTO(String path) {
        this.path = path;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class LocalStorageConfigDTOBuilder {
        private String path;

        LocalStorageConfigDTOBuilder() {
        }

        @JsonProperty(value="path")
        public LocalStorageConfigDTOBuilder path(String path) {
            this.path = path;
            return this;
        }

        public LocalStorageConfigDTO build() {
            return new LocalStorageConfigDTO(this.path);
        }

        public String toString() {
            return "LocalStorageConfigDTO.LocalStorageConfigDTOBuilder(path=" + this.path + ")";
        }
    }
}

