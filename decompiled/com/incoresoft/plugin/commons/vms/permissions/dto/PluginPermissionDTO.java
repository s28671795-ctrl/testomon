/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.permissions.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(name="PluginPermissionDTO", description="Permission class structure")
public record PluginPermissionDTO(@JsonProperty(value="id") @JSONField(name="id") @Schema(name="id", description="Permission id") String id, @JsonProperty(value="locale_key") @JSONField(name="locale_key") @Schema(name="locale_key", description="Permission locale key") String localeKey, @JsonProperty(value="required") @JSONField(name="required") @ArraySchema(schema=@Schema(name="required", description="List of required permission ids", implementation=String.class)) Set<String> required) {
}

