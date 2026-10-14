/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.exceptions;

import java.util.List;

public abstract class PluginsOperationFailedException
extends RuntimeException {
    public abstract List<String> getPluginIds();
}

