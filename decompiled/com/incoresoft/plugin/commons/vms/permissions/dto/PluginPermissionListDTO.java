/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.permissions.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.vms.permissions.dto.PluginPermissionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description="PluginPermissionListDTO", defaultValue="List of plugin permissions")
public record PluginPermissionListDTO(@JsonProperty(value="data") @JSONField(name="data") @Schema(name="data", description="List of plugin permissions") List<PluginPermissionDTO> data) {
}

