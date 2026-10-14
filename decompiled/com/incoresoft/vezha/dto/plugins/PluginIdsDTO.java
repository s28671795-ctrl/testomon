/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.plugins;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="PluginIdsDTO", description="List of plugin IDs")
public record PluginIdsDTO(@JSONField(name="plugin_ids") @Schema(description="List of plugin IDs", example="[\"plugin1\", \"plugin2\"]") List<String> pluginIds) {
}

