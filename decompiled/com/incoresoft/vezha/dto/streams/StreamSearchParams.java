/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

public record StreamSearchParams(@JSONField(name="name") @JsonProperty(value="name") String name, @JSONField(name="type") @JsonProperty(value="type") String type, @JSONField(name="show_deleted") @JsonProperty(value="show_deleted") boolean showDeleted, @JSONField(name="uuids") @JsonProperty(value="uuids") Set<UUID> uuids) {
}

