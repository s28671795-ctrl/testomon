/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

import com.incoresoft.plugins.exceptions.PluginNotFoundException;

public class PluginNotFoundInSystemException
extends PluginNotFoundException {
    public PluginNotFoundInSystemException(String pluginId) {
        super(pluginId);
    }
}

