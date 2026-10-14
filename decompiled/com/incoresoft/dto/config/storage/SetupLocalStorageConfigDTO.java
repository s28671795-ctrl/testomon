/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.dto.config.storage.SetupStorageConfigDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="SetupLocalStorageConfigDTO", description="Setup local storage configuration")
@JsonDeserialize(builder=SetupLocalStorageConfigDTOBuilder.class)
public final class SetupLocalStorageConfigDTO
implements SetupStorageConfigDTO {
    public static SetupLocalStorageConfigDTOBuilder builder() {
        return new SetupLocalStorageConfigDTOBuilder();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof SetupLocalStorageConfigDTO;
    }

    public int hashCode() {
        boolean result = true;
        return 1;
    }

    public String toString() {
        return "SetupLocalStorageConfigDTO()";
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class SetupLocalStorageConfigDTOBuilder {
        SetupLocalStorageConfigDTOBuilder() {
        }

        public SetupLocalStorageConfigDTO build() {
            return new SetupLocalStorageConfigDTO();
        }

        public String toString() {
            return "SetupLocalStorageConfigDTO.SetupLocalStorageConfigDTOBuilder()";
        }
    }
}

