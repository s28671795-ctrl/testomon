/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class PluginAlreadyUpdatedException
extends RuntimeException {
    private final String pluginId;

    public PluginAlreadyUpdatedException(@NotNull String pluginId) {
        this.pluginId = Objects.requireNonNull(pluginId);
    }

    public String getPluginId() {
        return this.pluginId;
    }
}

