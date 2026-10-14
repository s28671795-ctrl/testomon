/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

@Schema(name="ChangeStreamsLocationDTO", description="Change streams location class structure")
public record ChangeStreamsLocationDTO(@Schema(name="uuids", description="List of stream UUIDs") @JsonProperty(value="uuids") @JSONField(name="uuids") @NotEmpty Set<UUID> uuids, @Schema(name="location", description="New location") @JsonProperty(value="location") @JSONField(name="location") @NotNull CoordinatesDTO location) {
}

