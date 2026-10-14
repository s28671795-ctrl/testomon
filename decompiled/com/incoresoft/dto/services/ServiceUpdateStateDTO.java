/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.services;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.services.ServiceUpdateState;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ServiceUpdateStateDTO(@JsonProperty(value="id") @JSONField(name="id") @Schema(name="id", description="Id of service") String id, @JsonProperty(value="name") @JSONField(name="name") @Schema(name="name", description="Name of service") String name, @JsonProperty(value="current_version") @JSONField(name="current_version") @Schema(name="current_version", description="Current version of service", implementation=String.class, example="24.1.1") Version currentVersion, @JsonProperty(value="available_versions") @JSONField(name="available_versions") @ArraySchema(schema=@Schema(name="available_versions", description="Available versions", implementation=String.class, example="24.1.1")) List<Version> availableVersions, @JsonProperty(value="is_update_available") @JSONField(name="is_update_available") @Schema(name="is_update_available", description="Flag indicating if update is available") boolean isUpdateAvailable, @JsonProperty(value="is_being_updated") @JSONField(name="is_being_updated") @Schema(name="is_being_updated", description="Flag indicating if service is being updated") boolean isBeingUpdated) {
    public ServiceUpdateStateDTO(ServiceUpdateState serviceUpdateState) {
        this(serviceUpdateState.getId(), serviceUpdateState.getName(), serviceUpdateState.getCurrentVersion(), serviceUpdateState.getAvailableVersions(), serviceUpdateState.isUpdateAvailable(), serviceUpdateState.isBeingUpdated());
    }
}

