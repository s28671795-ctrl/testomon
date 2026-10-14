/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.systemlogs;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.services.ServiceUpdateState;

public record VersionsListEntry(@JSONField(name="module") String module, @JSONField(name="version") String version) {
    public VersionsListEntry(ServiceUpdateState serviceInfo) {
        this(serviceInfo.getName(), serviceInfo.getCurrentVersion().toString());
    }

    public VersionsListEntry(InstalledPluginInfo plugin) {
        this(plugin.getId(), plugin.getVersion().toString());
    }
}

