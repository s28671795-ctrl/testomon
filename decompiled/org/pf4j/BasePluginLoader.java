/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginClasspath;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginLoader;
import org.pf4j.PluginManager;
import org.pf4j.util.FileUtils;

public class BasePluginLoader
implements PluginLoader {
    protected PluginManager pluginManager;
    protected PluginClasspath pluginClasspath;

    public BasePluginLoader(PluginManager pluginManager, PluginClasspath pluginClasspath) {
        this.pluginManager = pluginManager;
        this.pluginClasspath = pluginClasspath;
    }

    @Override
    public boolean isApplicable(Path pluginPath) {
        return Files.exists(pluginPath, new LinkOption[0]);
    }

    @Override
    public ClassLoader loadPlugin(Path pluginPath, PluginDescriptor pluginDescriptor) {
        PluginClassLoader pluginClassLoader = this.createPluginClassLoader(pluginPath, pluginDescriptor);
        this.loadClasses(pluginPath, pluginClassLoader);
        this.loadJars(pluginPath, pluginClassLoader);
        return pluginClassLoader;
    }

    protected PluginClassLoader createPluginClassLoader(Path pluginPath, PluginDescriptor pluginDescriptor) {
        return new PluginClassLoader(this.pluginManager, pluginDescriptor, this.getClass().getClassLoader());
    }

    protected void loadClasses(Path pluginPath, PluginClassLoader pluginClassLoader) {
        for (String directory : this.pluginClasspath.getClassesDirectories()) {
            File file = pluginPath.resolve(directory).toFile();
            if (!file.exists() || !file.isDirectory()) continue;
            pluginClassLoader.addFile(file);
        }
    }

    protected void loadJars(Path pluginPath, PluginClassLoader pluginClassLoader) {
        for (String jarsDirectory : this.pluginClasspath.getJarsDirectories()) {
            Path file = pluginPath.resolve(jarsDirectory);
            List<File> jars = FileUtils.getJars(file);
            for (File jar : jars) {
                pluginClassLoader.addFile(jar);
            }
        }
    }
}

