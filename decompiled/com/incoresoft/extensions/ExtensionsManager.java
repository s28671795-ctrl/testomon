/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.extensions;

import com.google.inject.Inject;
import com.incoresoft.extensions.ExtensionListener;
import com.incoresoft.extensions.ExtensionsRegistry;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExtensionsManager {
    private static final Logger log = LoggerFactory.getLogger(ExtensionsManager.class);
    private final Set<ExtensionListener> listeners = Collections.newSetFromMap(new ConcurrentHashMap());
    private final ExtensionsRegistry extensionsRegistry = new ExtensionsRegistry();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initExtensions(String pluginId, Collection<? extends InitializableExtension<?>> extensions) {
        for (InitializableExtension<?> extension : extensions) {
            this.extensionsRegistry.register(pluginId, extension);
            ExtensionsManager extensionsManager = this;
            synchronized (extensionsManager) {
                this.initExtension(extension);
                ListenersUtils.triggerListeners(l -> l.onExtensionInit(extension, pluginId), this.listeners);
            }
        }
    }

    protected abstract void initExtension(InitializableExtension<?> var1);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void terminateExtensions(@NotNull String pluginId) {
        Set<InitializableExtension<?>> extensions = this.extensionsRegistry.unregister(pluginId);
        for (InitializableExtension<?> extension : extensions) {
            try {
                ExtensionsManager extensionsManager = this;
                synchronized (extensionsManager) {
                    extension.terminate();
                    ListenersUtils.triggerListeners(l -> l.onExtensionTerminate(extension, pluginId), this.listeners);
                }
            }
            catch (Throwable e) {
                log.error("Termination error in extension {}", (Object)extension.getClass().getName(), (Object)e);
            }
        }
    }

    public Map<String, Set<? extends InitializableExtension<?>>> getAll() {
        return this.extensionsRegistry.getAll();
    }

    public <V extends InitializableExtension<?>> Collection<V> getAll(Class<V> extensionClass) {
        return this.extensionsRegistry.getAll().values().stream().flatMap(Collection::stream).filter(extensionClass::isInstance).map(extensionClass::cast).toList();
    }

    public <V extends InitializableExtension<?>> Collection<V> getAll(String pluginId, Class<V> extensionClass) {
        return this.extensionsRegistry.getAll(pluginId).stream().filter(extensionClass::isInstance).map(extensionClass::cast).toList();
    }

    public String getPluginIdByExtension(InitializableExtension<?> extension) {
        return this.extensionsRegistry.getPluginIdByExtension(extension);
    }

    public void addListener(ExtensionListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ExtensionListener listener) {
        this.listeners.remove(listener);
    }

    @Inject
    public ExtensionsManager() {
    }
}

