/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import org.pf4j.CompoundPluginLoader;
import org.pf4j.CompoundPluginRepository;
import org.pf4j.DefaultPluginManager;
import org.pf4j.DevelopmentPluginLoader;
import org.pf4j.DevelopmentPluginRepository;
import org.pf4j.JarPluginLoader;
import org.pf4j.JarPluginRepository;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginLoader;
import org.pf4j.PluginRepository;

public class JarPluginManager
extends DefaultPluginManager {
    public JarPluginManager() {
    }

    public JarPluginManager(Path ... pluginsRoots) {
        super(pluginsRoots);
    }

    @Override
    protected PluginDescriptorFinder createPluginDescriptorFinder() {
        return new ManifestPluginDescriptorFinder();
    }

    @Override
    protected PluginLoader createPluginLoader() {
        return new CompoundPluginLoader().add(new DevelopmentPluginLoader(this), this::isDevelopment).add(new JarPluginLoader(this), this::isNotDevelopment);
    }

    @Override
    protected PluginRepository createPluginRepository() {
        return new CompoundPluginRepository().add(new DevelopmentPluginRepository(this.getPluginsRoots()), this::isDevelopment).add(new JarPluginRepository(this.getPluginsRoots()), this::isNotDevelopment);
    }
}

