/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

import com.github.zafarkhaja.semver.Version;

public class PluginNotCompatibleException
extends RuntimeException {
    private final Version pluginVersion;

    public PluginNotCompatibleException(Version pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public Version getPluginVersion() {
        return this.pluginVersion;
    }
}

