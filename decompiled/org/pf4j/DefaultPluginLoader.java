/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.pf4j.BasePluginLoader;
import org.pf4j.DefaultPluginClasspath;
import org.pf4j.PluginManager;

public class DefaultPluginLoader
extends BasePluginLoader {
    public DefaultPluginLoader(PluginManager pluginManager) {
        super(pluginManager, new DefaultPluginClasspath());
    }

    @Override
    public boolean isApplicable(Path pluginPath) {
        return super.isApplicable(pluginPath) && Files.isDirectory(pluginPath, new LinkOption[0]);
    }
}

