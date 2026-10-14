/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.plugins.CustomJarPluginManager;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.models.PluginFileInfo;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.concurrent.GuardedBy;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginManagerWrapper {
    private static final Logger log = LoggerFactory.getLogger(PluginManagerWrapper.class);
    private final JarFileManifestReader manifestReader;
    private final PluginManager pluginManager;
    @GuardedBy(value="this")
    private final Map<String, Map<String, String>> pluginAttributesMap = new HashMap<String, Map<String, String>>();

    public PluginManagerWrapper(JarFileManifestReader manifestReader, CoreServiceVersionSupplier coreServiceVersionSupplier, String pluginsPathValue) {
        this.manifestReader = manifestReader;
        this.pluginManager = new CustomJarPluginManager(pluginsPathValue);
        this.pluginManager.setSystemVersion(coreServiceVersionSupplier.getCoreVersion().toString());
    }

    public synchronized void loadPlugins() {
        this.pluginManager.loadPlugins();
        for (PluginWrapper wrapper : this.pluginManager.getPlugins()) {
            this.pluginAttributesMap.put(wrapper.getPluginId(), this.manifestReader.read(wrapper));
            log.info("Plugin {} is loaded.", (Object)wrapper.getPluginId());
        }
    }

    public synchronized String loadPlugin(Path path) {
        String id = this.pluginManager.loadPlugin(path);
        PluginWrapper wrapper = this.pluginManager.getPlugin(id);
        this.pluginAttributesMap.put(id, this.manifestReader.read(wrapper));
        log.info("Plugin {} is loaded.", (Object)id);
        return id;
    }

    public synchronized void unloadPlugin(String pluginId) {
        this.pluginManager.unloadPlugin(pluginId);
        this.pluginAttributesMap.remove(pluginId);
    }

    public synchronized <T> List<T> getExtensions(Class<T> type, String id) {
        return this.pluginManager.getExtensions(type, id);
    }

    public synchronized List<PluginFileInfo> getPlugins() {
        return this.pluginManager.getPlugins().stream().map(wrapper -> new PluginFileInfo((PluginWrapper)wrapper, this.pluginAttributesMap.get(wrapper.getPluginId()))).toList();
    }

    public synchronized Optional<PluginFileInfo> getPlugin(String pluginId) {
        PluginWrapper pluginWrapper = this.pluginManager.getPlugin(pluginId);
        if (pluginWrapper == null) {
            return Optional.empty();
        }
        return Optional.of(new PluginFileInfo(pluginWrapper, this.pluginAttributesMap.get(pluginId)));
    }

    public synchronized PluginState startPlugin(String id) {
        return this.pluginManager.startPlugin(id);
    }

    public synchronized void stopPlugin(String pluginId) {
        this.pluginManager.stopPlugin(pluginId);
    }
}

