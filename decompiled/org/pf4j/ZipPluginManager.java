/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.CompoundPluginLoader;
import org.pf4j.CompoundPluginRepository;
import org.pf4j.DefaultPluginLoader;
import org.pf4j.DefaultPluginManager;
import org.pf4j.DefaultPluginRepository;
import org.pf4j.DevelopmentPluginLoader;
import org.pf4j.DevelopmentPluginRepository;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginLoader;
import org.pf4j.PluginRepository;
import org.pf4j.PropertiesPluginDescriptorFinder;

public class ZipPluginManager
extends DefaultPluginManager {
    @Override
    protected PluginDescriptorFinder createPluginDescriptorFinder() {
        return new PropertiesPluginDescriptorFinder();
    }

    @Override
    protected PluginLoader createPluginLoader() {
        return new CompoundPluginLoader().add(new DevelopmentPluginLoader(this), this::isDevelopment).add(new DefaultPluginLoader(this), this::isNotDevelopment);
    }

    @Override
    protected PluginRepository createPluginRepository() {
        return new CompoundPluginRepository().add(new DevelopmentPluginRepository(this.getPluginsRoots()), this::isDevelopment).add(new DefaultPluginRepository(this.getPluginsRoots()), this::isNotDevelopment);
    }
}

