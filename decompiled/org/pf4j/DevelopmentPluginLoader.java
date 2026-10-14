/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.BasePluginLoader;
import org.pf4j.DevelopmentPluginClasspath;
import org.pf4j.PluginManager;

public class DevelopmentPluginLoader
extends BasePluginLoader {
    public DevelopmentPluginLoader(PluginManager pluginManager) {
        super(pluginManager, new DevelopmentPluginClasspath());
    }
}

