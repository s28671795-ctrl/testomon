/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.pf4j.ExtensionFactory;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginStateEvent;
import org.pf4j.PluginStateListener;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;
import org.pf4j.VersionManager;

public class SecurePluginManagerWrapper
implements PluginManager {
    private static final String PLUGIN_PREFIX = "Plugin ";
    private String currentPluginId;
    private PluginManager original;
    protected List<PluginStateListener> pluginStateListeners = new ArrayList<PluginStateListener>();
    private PluginStateListenerWrapper listenerWrapper = new PluginStateListenerWrapper();

    public SecurePluginManagerWrapper(PluginManager original, String currentPluginId) {
        this.original = original;
        this.currentPluginId = currentPluginId;
    }

    @Override
    public boolean isDevelopment() {
        return this.original.isDevelopment();
    }

    @Override
    public boolean isNotDevelopment() {
        return this.original.isNotDevelopment();
    }

    @Override
    public List<PluginWrapper> getPlugins() {
        return Arrays.asList(this.getPlugin(this.currentPluginId));
    }

    @Override
    public List<PluginWrapper> getPlugins(PluginState pluginState) {
        return this.getPlugins().stream().filter(p -> p.getPluginState() == pluginState).collect(Collectors.toList());
    }

    @Override
    public List<PluginWrapper> getResolvedPlugins() {
        return this.getPlugins().stream().filter(p -> p.getPluginState().ordinal() >= PluginState.RESOLVED.ordinal()).collect(Collectors.toList());
    }

    @Override
    public List<PluginWrapper> getUnresolvedPlugins() {
        return Collections.emptyList();
    }

    @Override
    public List<PluginWrapper> getStartedPlugins() {
        return this.getPlugins(PluginState.STARTED);
    }

    @Override
    public PluginWrapper getPlugin(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.getPlugin(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getPlugin for foreign pluginId!");
    }

    @Override
    public void loadPlugins() {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute loadPlugins!");
    }

    @Override
    public String loadPlugin(Path pluginPath) {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute loadPlugin!");
    }

    @Override
    public void startPlugins() {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute startPlugins!");
    }

    @Override
    public PluginState startPlugin(String pluginId) {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute startPlugin!");
    }

    @Override
    public void stopPlugins() {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute stopPlugins!");
    }

    @Override
    public PluginState stopPlugin(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.stopPlugin(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute stopPlugin for foreign pluginId!");
    }

    @Override
    public void unloadPlugins() {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute unloadPlugins!");
    }

    @Override
    public boolean unloadPlugin(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.unloadPlugin(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute unloadPlugin for foreign pluginId!");
    }

    @Override
    public boolean disablePlugin(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.disablePlugin(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute disablePlugin for foreign pluginId!");
    }

    @Override
    public boolean enablePlugin(String pluginId) {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute enablePlugin!");
    }

    @Override
    public boolean deletePlugin(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.deletePlugin(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute deletePlugin for foreign pluginId!");
    }

    @Override
    public ClassLoader getPluginClassLoader(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.getPluginClassLoader(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getPluginClassLoader for foreign pluginId!");
    }

    @Override
    public List<Class<?>> getExtensionClasses(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.getExtensionClasses(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getExtensionClasses for foreign pluginId!");
    }

    @Override
    public <T> List<Class<? extends T>> getExtensionClasses(Class<T> type) {
        return this.getExtensionClasses(type, this.currentPluginId);
    }

    @Override
    public <T> List<Class<? extends T>> getExtensionClasses(Class<T> type, String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.getExtensionClasses(type, pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getExtensionClasses for foreign pluginId!");
    }

    @Override
    public <T> List<T> getExtensions(Class<T> type) {
        return this.getExtensions(type, this.currentPluginId);
    }

    @Override
    public <T> List<T> getExtensions(Class<T> type, String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.getExtensions(type, pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getExtensions for foreign pluginId!");
    }

    @Override
    public List<?> getExtensions(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.getExtensions(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getExtensions for foreign pluginId!");
    }

    @Override
    public Set<String> getExtensionClassNames(String pluginId) {
        if (this.currentPluginId.equals(pluginId)) {
            return this.original.getExtensionClassNames(pluginId);
        }
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getExtensionClassNames for foreign pluginId!");
    }

    @Override
    public ExtensionFactory getExtensionFactory() {
        return this.original.getExtensionFactory();
    }

    @Override
    public RuntimeMode getRuntimeMode() {
        return this.original.getRuntimeMode();
    }

    @Override
    public PluginWrapper whichPlugin(Class<?> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        PluginWrapper plugin = this.getPlugin(this.currentPluginId);
        if (plugin.getPluginClassLoader() == classLoader) {
            return plugin;
        }
        return null;
    }

    @Override
    public void addPluginStateListener(PluginStateListener listener) {
        if (this.pluginStateListeners.isEmpty()) {
            this.original.addPluginStateListener(this.listenerWrapper);
        }
        this.pluginStateListeners.add(listener);
    }

    @Override
    public void removePluginStateListener(PluginStateListener listener) {
        this.pluginStateListeners.remove(listener);
        if (this.pluginStateListeners.isEmpty()) {
            this.original.removePluginStateListener(this.listenerWrapper);
        }
    }

    @Override
    public void setSystemVersion(String version) {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute setSystemVersion!");
    }

    @Override
    public String getSystemVersion() {
        return this.original.getSystemVersion();
    }

    @Override
    public Path getPluginsRoot() {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getPluginsRoot!");
    }

    @Override
    public List<Path> getPluginsRoots() {
        throw new IllegalAccessError(PLUGIN_PREFIX + this.currentPluginId + " tried to execute getPluginsRoots!");
    }

    @Override
    public VersionManager getVersionManager() {
        return this.original.getVersionManager();
    }

    private class PluginStateListenerWrapper
    implements PluginStateListener {
        private PluginStateListenerWrapper() {
        }

        @Override
        public void pluginStateChanged(PluginStateEvent event) {
            if (event.getPlugin().getPluginId().equals(SecurePluginManagerWrapper.this.currentPluginId)) {
                for (PluginStateListener listener : SecurePluginManagerWrapper.this.pluginStateListeners) {
                    listener.pluginStateChanged(event);
                }
            }
        }
    }
}

