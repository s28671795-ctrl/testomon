/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.pf4j.AbstractPluginManager;
import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.CompoundPluginLoader;
import org.pf4j.CompoundPluginRepository;
import org.pf4j.DefaultExtensionFactory;
import org.pf4j.DefaultExtensionFinder;
import org.pf4j.DefaultPluginFactory;
import org.pf4j.DefaultPluginLoader;
import org.pf4j.DefaultPluginRepository;
import org.pf4j.DefaultPluginStatusProvider;
import org.pf4j.DefaultVersionManager;
import org.pf4j.DevelopmentPluginLoader;
import org.pf4j.DevelopmentPluginRepository;
import org.pf4j.ExtensionFactory;
import org.pf4j.ExtensionFinder;
import org.pf4j.JarPluginLoader;
import org.pf4j.JarPluginRepository;
import org.pf4j.LoggingPluginStateListener;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginFactory;
import org.pf4j.PluginLoader;
import org.pf4j.PluginRepository;
import org.pf4j.PluginStatusProvider;
import org.pf4j.PluginWrapper;
import org.pf4j.PropertiesPluginDescriptorFinder;
import org.pf4j.VersionManager;
import org.pf4j.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPluginManager
extends AbstractPluginManager {
    private static final Logger log = LoggerFactory.getLogger(DefaultPluginManager.class);
    public static final String PLUGINS_DIR_CONFIG_PROPERTY_NAME = "pf4j.pluginsConfigDir";

    public DefaultPluginManager() {
    }

    public DefaultPluginManager(Path ... pluginsRoots) {
        super(pluginsRoots);
    }

    public DefaultPluginManager(List<Path> pluginsRoots) {
        super(pluginsRoots);
    }

    @Override
    protected PluginDescriptorFinder createPluginDescriptorFinder() {
        return new CompoundPluginDescriptorFinder().add(new PropertiesPluginDescriptorFinder()).add(new ManifestPluginDescriptorFinder());
    }

    @Override
    protected ExtensionFinder createExtensionFinder() {
        DefaultExtensionFinder extensionFinder = new DefaultExtensionFinder(this);
        this.addPluginStateListener(extensionFinder);
        return extensionFinder;
    }

    @Override
    protected PluginFactory createPluginFactory() {
        return new DefaultPluginFactory();
    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new DefaultExtensionFactory();
    }

    @Override
    protected PluginStatusProvider createPluginStatusProvider() {
        String configDir = System.getProperty(PLUGINS_DIR_CONFIG_PROPERTY_NAME);
        Path configPath = configDir != null ? Paths.get(configDir, new String[0]) : (Path)this.getPluginsRoots().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("No pluginsRoot configured"));
        return new DefaultPluginStatusProvider(configPath);
    }

    @Override
    protected PluginRepository createPluginRepository() {
        return new CompoundPluginRepository().add(new DevelopmentPluginRepository(this.getPluginsRoots()), this::isDevelopment).add(new JarPluginRepository(this.getPluginsRoots()), this::isNotDevelopment).add(new DefaultPluginRepository(this.getPluginsRoots()), this::isNotDevelopment);
    }

    @Override
    protected PluginLoader createPluginLoader() {
        return new CompoundPluginLoader().add(new DevelopmentPluginLoader(this), this::isDevelopment).add(new JarPluginLoader(this), this::isNotDevelopment).add(new DefaultPluginLoader(this), this::isNotDevelopment);
    }

    @Override
    protected VersionManager createVersionManager() {
        return new DefaultVersionManager();
    }

    @Override
    protected void initialize() {
        super.initialize();
        if (this.isDevelopment()) {
            this.addPluginStateListener(new LoggingPluginStateListener());
        }
        log.info("PF4J version {} in '{}' mode", (Object)this.getVersion(), (Object)this.getRuntimeMode());
    }

    @Override
    protected PluginWrapper loadPluginFromPath(Path pluginPath) {
        try {
            pluginPath = FileUtils.expandIfZip(pluginPath);
        }
        catch (Exception e) {
            log.warn("Failed to unzip " + pluginPath, e);
            return null;
        }
        return super.loadPluginFromPath(pluginPath);
    }
}

