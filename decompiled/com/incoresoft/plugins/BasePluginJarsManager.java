/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.database.EnabledPluginsRepository;
import com.incoresoft.plugins.EnabledPluginsTracker;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.JavalinEndpointTracker;
import com.incoresoft.plugins.PluginIconEndpointInitializer;
import com.incoresoft.plugins.PluginManagerWrapper;
import com.incoresoft.plugins.broadcast.PluginStateSSEBroadcaster;
import com.incoresoft.plugins.exceptions.PluginLoadingException;
import com.incoresoft.plugins.exceptions.PluginNotFoundInSystemException;
import com.incoresoft.plugins.exceptions.PluginStartingException;
import com.incoresoft.plugins.exceptions.PluginStoppingException;
import com.incoresoft.plugins.exceptions.PluginUninstallingException;
import com.incoresoft.plugins.exceptions.PluginsStartingException;
import com.incoresoft.plugins.exceptions.PluginsStoppingException;
import com.incoresoft.plugins.models.PluginFileInfo;
import com.incoresoft.plugins.models.PluginType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.pf4j.PluginState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePluginJarsManager {
    private static final Logger log = LoggerFactory.getLogger(BasePluginJarsManager.class);
    private final PluginManagerWrapper pluginManagerWrapper;
    private final PluginIconEndpointInitializer iconEndpointInitializer;
    private final EnabledPluginsTracker enabledPluginsTracker;
    private final JavalinEndpointTracker endpointTracker;
    private final PluginStateSSEBroadcaster broadcaster;
    private final PluginType pluginType;
    private final String pluginsPathValue;

    public BasePluginJarsManager(JarFileManifestReader manifestReader, CoreServiceVersionSupplier coreServiceVersionSupplier, JavalinEndpointTracker endpointTracker, PluginIconEndpointInitializer iconEndpointInitializer, PluginStateSSEBroadcaster broadcaster, EnabledPluginsRepository enabledPluginsRepository, PluginType pluginType, String pluginsPathValue, String configFileName) {
        this.enabledPluginsTracker = new EnabledPluginsTracker(enabledPluginsRepository, configFileName);
        this.endpointTracker = endpointTracker;
        this.iconEndpointInitializer = iconEndpointInitializer;
        this.broadcaster = broadcaster;
        this.pluginType = pluginType;
        this.pluginsPathValue = pluginsPathValue;
        this.pluginManagerWrapper = new PluginManagerWrapper(manifestReader, coreServiceVersionSupplier, pluginsPathValue);
    }

    protected abstract void executeAfterStartJar(PluginFileInfo var1);

    protected abstract void executeAfterStopJar(PluginFileInfo var1);

    protected abstract void executeBeforeUninstallJar(String var1);

    protected abstract List<Class<?>> getExtensionClasses();

    public void loadAndStartEnabledPlugins() {
        this.pluginManagerWrapper.loadPlugins();
        List<PluginFileInfo> enabledPlugins = this.pluginManagerWrapper.getPlugins().stream().filter(plugin -> this.enabledPluginsTracker.enabled(plugin.getId())).toList();
        try {
            this.startAll(enabledPlugins);
        }
        catch (PluginsStartingException pluginsStartingException) {
            // empty catch block
        }
    }

    public List<PluginFileInfo> getPlugins() {
        return this.pluginManagerWrapper.getPlugins();
    }

    public Optional<PluginFileInfo> getPlugin(String pluginId) {
        return this.pluginManagerWrapper.getPlugin(pluginId);
    }

    public void loadPlugin(Path pluginPath) {
        this.pluginManagerWrapper.loadPlugin(pluginPath);
    }

    public void startPlugin(String pluginId) {
        try {
            this.startPluginInternal(pluginId);
        }
        catch (Throwable e) {
            log.error("Couldn't start plugin", e);
            try {
                this.stopPluginInternal(pluginId, false, true);
            }
            catch (Exception ex) {
                log.error("Couldn't stop plugin", ex);
            }
            throw new PluginStartingException(e);
        }
    }

    public void startAll() {
        this.startAll(this.getPlugins());
    }

    public Path stopPlugin(String pluginId) {
        return this.stopPlugin(pluginId, true);
    }

    public Path stopPlugin(String pluginId, boolean broadcast) {
        try {
            return this.stopPluginInternal(pluginId, true, broadcast);
        }
        catch (Throwable e) {
            log.error("Couldn't stop plugin", e);
            throw new PluginStoppingException(e);
        }
    }

    public void stopAll() {
        ArrayList<String> failed = new ArrayList<String>();
        for (PluginFileInfo plugin : this.getPlugins()) {
            try {
                this.stopPluginInternal(plugin.getId(), true, true);
            }
            catch (Throwable e) {
                log.error("Couldn't stop plugin", e);
                failed.add(plugin.getId());
            }
        }
        if (!failed.isEmpty()) {
            throw new PluginsStoppingException(failed);
        }
    }

    public void loadAndStart(Path pluginPath) {
        try {
            String pluginId = this.pluginManagerWrapper.loadPlugin(pluginPath);
            try {
                this.startPlugin(pluginId);
            }
            catch (RuntimeException e) {
                this.pluginManagerWrapper.unloadPlugin(pluginId);
                Files.deleteIfExists(pluginPath);
                log.error("Couldn't start plugin", e);
                throw e;
            }
        }
        catch (Exception e) {
            log.error("Couldn't load or start plugin", e);
            throw new PluginLoadingException(e);
        }
    }

    public void unloadPlugin(String pluginId) {
        this.pluginManagerWrapper.unloadPlugin(pluginId);
    }

    public void uninstallPlugin(String pluginId) {
        try {
            this.executeBeforeUninstallJar(pluginId);
            Path pluginPath = this.stopPlugin(pluginId);
            this.unloadPlugin(pluginId);
            Files.deleteIfExists(pluginPath);
            this.broadcaster.broadcastPluginUninstalled(pluginId, this.pluginType);
        }
        catch (Exception e) {
            log.error("Couldn't uninstall plugin", e);
            throw new PluginUninstallingException(e);
        }
    }

    protected <T> List<T> getExtensions(Class<T> type, String id) {
        return this.pluginManagerWrapper.getExtensions(type, id);
    }

    private synchronized void startPluginInternal(String pluginId) {
        Objects.requireNonNull(pluginId, "pluginId cannot be null");
        PluginFileInfo plugin = this.pluginManagerWrapper.getPlugin(pluginId).orElseThrow(() -> new PluginNotFoundInSystemException(pluginId));
        if (plugin.getState() == PluginState.STARTED) {
            log.warn("Plugin \"{}\" is already started", (Object)plugin.getId());
            return;
        }
        PluginState newState = this.pluginManagerWrapper.startPlugin(pluginId);
        if (newState == PluginState.DISABLED) {
            return;
        }
        this.validateContainsExtensions(plugin);
        this.enabledPluginsTracker.setEnabled(pluginId);
        this.endpointTracker.trackPlugin(plugin, it -> {
            this.executeAfterStartJar((PluginFileInfo)it);
            this.iconEndpointInitializer.initializeIconEndpoint((PluginFileInfo)it);
        });
        this.broadcaster.broadcastPluginStarted(pluginId, this.pluginType);
    }

    private void validateContainsExtensions(PluginFileInfo plugin) {
        List extensions = this.getExtensionClasses().stream().flatMap(extClass -> this.getExtensions((Class)extClass, plugin.getId()).stream()).toList();
        if (extensions.isEmpty()) {
            throw new RuntimeException(String.format("Plugin %s misses expected extensions", plugin.getId()));
        }
    }

    private synchronized Path stopPluginInternal(String pluginId, boolean disableForNextRuns, boolean broadcast) {
        Objects.requireNonNull(pluginId, "pluginId cannot be null");
        PluginFileInfo pluginToStop = this.pluginManagerWrapper.getPlugin(pluginId).orElseThrow(() -> new PluginNotFoundInSystemException(pluginId));
        PluginState oldState = pluginToStop.getState();
        if (oldState == PluginState.STOPPED) {
            log.warn("Plugin \"{}\" is already stopped", (Object)pluginToStop.getId());
            return pluginToStop.getPath();
        }
        this.pluginManagerWrapper.stopPlugin(pluginId);
        this.endpointTracker.untrackPlugin(pluginToStop);
        this.executeAfterStopJar(pluginToStop);
        if (disableForNextRuns) {
            this.enabledPluginsTracker.setDisabled(pluginId);
        }
        if (broadcast) {
            this.broadcaster.broadcastPluginStopped(pluginId, this.pluginType);
        }
        return pluginToStop.getPath();
    }

    private void startAll(List<PluginFileInfo> plugins) {
        ArrayList<String> failed = new ArrayList<String>();
        for (PluginFileInfo plugin : plugins) {
            try {
                this.startPlugin(plugin.getId());
            }
            catch (Throwable e) {
                failed.add(plugin.getId());
            }
        }
        if (!failed.isEmpty()) {
            throw new PluginsStartingException(failed);
        }
    }

    public String getPluginsPathValue() {
        return this.pluginsPathValue;
    }
}

