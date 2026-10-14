/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

import com.incoresoft.plugins.exceptions.PluginNotFoundException;
import org.jetbrains.annotations.NotNull;

public class PluginNotFoundInMarketplaceException
extends PluginNotFoundException {
    public PluginNotFoundInMarketplaceException(@NotNull String pluginId) {
        super(pluginId);
    }
}

