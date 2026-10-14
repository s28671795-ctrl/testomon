/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import org.pf4j.PluginRuntimeException;

public class PluginAlreadyLoadedException
extends PluginRuntimeException {
    private final String pluginId;
    private final Path pluginPath;

    public PluginAlreadyLoadedException(String pluginId, Path pluginPath) {
        super("Plugin '{}'\u00a0already loaded with id '{}'", pluginPath, pluginId);
        this.pluginId = pluginId;
        this.pluginPath = pluginPath;
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public Path getPluginPath() {
        return this.pluginPath;
    }
}

