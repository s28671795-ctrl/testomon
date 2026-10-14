/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.plugins;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.zafarkhaja.semver.Version;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="PluginVersionDTO", description="Plugin version data")
public record PluginVersionDTO(@JsonProperty(value="version") @JSONField(name="version") @Schema(name="version", description="Plugin version", implementation=Version.class, example="25.1.69") Version version) {
}

