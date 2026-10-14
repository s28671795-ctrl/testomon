/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

import com.incoresoft.plugins.exceptions.PluginsOperationFailedException;
import java.util.List;

public class PluginsStartingException
extends PluginsOperationFailedException {
    private final List<String> pluginIds;

    public PluginsStartingException(List<String> pluginIds) {
        this.pluginIds = pluginIds;
    }

    @Override
    public List<String> getPluginIds() {
        return this.pluginIds;
    }
}

