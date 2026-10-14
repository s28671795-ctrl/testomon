/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.metrics;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zafarkhaja.semver.Version;
import com.incoresoft.plugins.models.InstalledPluginInfo;

public record MetricsInstalledPluginDTO(@JSONField(name="id") String id, @JSONField(name="version") Version version, @JSONField(name="is_enabled") boolean isEnabled) {
    public MetricsInstalledPluginDTO(InstalledPluginInfo installedPlugin) {
        this(installedPlugin.getId(), installedPlugin.getVersion(), installedPlugin.isEnabled());
    }
}

