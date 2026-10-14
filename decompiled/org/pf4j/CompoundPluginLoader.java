/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompoundPluginLoader
implements PluginLoader {
    private static final Logger log = LoggerFactory.getLogger(CompoundPluginLoader.class);
    private List<PluginLoader> loaders = new ArrayList<PluginLoader>();

    public CompoundPluginLoader add(PluginLoader loader) {
        if (loader == null) {
            throw new IllegalArgumentException("null not allowed");
        }
        this.loaders.add(loader);
        return this;
    }

    public CompoundPluginLoader add(PluginLoader loader, BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            return this.add(loader);
        }
        return this;
    }

    public int size() {
        return this.loaders.size();
    }

    @Override
    public boolean isApplicable(Path pluginPath) {
        for (PluginLoader loader : this.loaders) {
            if (!loader.isApplicable(pluginPath)) continue;
            return true;
        }
        return false;
    }

    @Override
    public ClassLoader loadPlugin(Path pluginPath, PluginDescriptor pluginDescriptor) {
        for (PluginLoader loader : this.loaders) {
            if (loader.isApplicable(pluginPath)) {
                log.debug("'{}' is applicable for plugin '{}'", (Object)loader, (Object)pluginPath);
                try {
                    ClassLoader classLoader = loader.loadPlugin(pluginPath, pluginDescriptor);
                    if (classLoader == null) continue;
                    return classLoader;
                }
                catch (Exception e) {
                    log.error(e.getMessage());
                    continue;
                }
            }
            log.debug("'{}' is not applicable for plugin '{}'", (Object)loader, (Object)pluginPath);
        }
        throw new RuntimeException("No PluginLoader for plugin '" + pluginPath + "' and descriptor '" + pluginDescriptor + "'");
    }
}

