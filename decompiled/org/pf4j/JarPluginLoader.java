/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginLoader;
import org.pf4j.PluginManager;
import org.pf4j.util.FileUtils;

public class JarPluginLoader
implements PluginLoader {
    protected PluginManager pluginManager;

    public JarPluginLoader(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public boolean isApplicable(Path pluginPath) {
        return Files.exists(pluginPath, new LinkOption[0]) && FileUtils.isJarFile(pluginPath);
    }

    @Override
    public ClassLoader loadPlugin(Path pluginPath, PluginDescriptor pluginDescriptor) {
        PluginClassLoader pluginClassLoader = new PluginClassLoader(this.pluginManager, pluginDescriptor, this.getClass().getClassLoader());
        pluginClassLoader.addFile(pluginPath.toFile());
        return pluginClassLoader;
    }
}

