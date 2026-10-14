/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(name="StreamNamesDTO", description="List of stream names")
public record StreamNamesDTO(@JSONField(name="names") @JsonProperty(value="names") @Schema(description="List of stream names", example="[\"stream 1\", \"stream2\"]") Set<String> names) {
}

