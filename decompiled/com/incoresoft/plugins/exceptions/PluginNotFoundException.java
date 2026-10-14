/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class PluginNotFoundException
extends RuntimeException {
    private final String pluginId;

    public PluginNotFoundException(@NotNull String pluginId) {
        this.pluginId = Objects.requireNonNull(pluginId);
    }

    public String getPluginId() {
        return this.pluginId;
    }
}

