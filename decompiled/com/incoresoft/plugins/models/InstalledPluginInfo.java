/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.models;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.plugins.models.PluginFileInfo;
import java.nio.file.Path;
import java.util.Map;
import org.pf4j.PluginState;

public class InstalledPluginInfo {
    private final String id;
    private final String description;
    private final String name;
    private final String type;
    private final Path installPath;
    private final Version version;
    private final boolean enabled;
    private final Map<String, String> allAttributes;

    public InstalledPluginInfo(PluginFileInfo fileInfo, String type) {
        this.id = fileInfo.getId();
        this.description = fileInfo.getDescription();
        this.version = fileInfo.getVersion();
        this.installPath = fileInfo.getPath();
        this.enabled = fileInfo.getState() == PluginState.STARTED;
        this.name = fileInfo.getName();
        this.type = type;
        this.allAttributes = fileInfo.getAllAttributes();
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public Path getInstallPath() {
        return this.installPath;
    }

    public Version getVersion() {
        return this.version;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Map<String, String> getAllAttributes() {
        return this.allAttributes;
    }

    public InstalledPluginInfo(String id, String description, String name, String type, Path installPath, Version version, boolean enabled, Map<String, String> allAttributes) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.type = type;
        this.installPath = installPath;
        this.version = version;
        this.enabled = enabled;
        this.allAttributes = allAttributes;
    }
}

