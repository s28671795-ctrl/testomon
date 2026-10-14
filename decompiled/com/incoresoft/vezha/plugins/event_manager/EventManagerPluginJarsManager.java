/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.event_manager;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.database.EnabledPluginsRepository;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.plugin.EventManagerExtension;
import com.incoresoft.plugins.BasePluginJarsManager;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.JavalinEndpointTracker;
import com.incoresoft.plugins.PluginIconEndpointInitializer;
import com.incoresoft.plugins.broadcast.PluginStateSSEBroadcaster;
import com.incoresoft.plugins.exceptions.PluginInitializationException;
import com.incoresoft.plugins.models.PluginFileInfo;
import com.incoresoft.plugins.models.PluginType;
import java.util.List;

public class EventManagerPluginJarsManager
extends BasePluginJarsManager {
    private static final String PLUGINS_PATH = "plugins/event-manager";
    private static final String CONFIG_FILE_NAME = "enabled_event_manager_plugins.json";
    private final ExtensionsManager extensionsManager;

    @Inject
    public EventManagerPluginJarsManager(ExtensionsManager extensionsManager, CoreServiceVersionSupplier coreServiceVersionSupplier, JarFileManifestReader manifestReader, JavalinEndpointTracker endpointTracker, PluginIconEndpointInitializer iconEndpointInitializer, PluginStateSSEBroadcaster broadcaster, EnabledPluginsRepository enabledPluginsRepository) {
        super(manifestReader, coreServiceVersionSupplier, endpointTracker, iconEndpointInitializer, broadcaster, enabledPluginsRepository, PluginType.EVENT_MANAGER_PLUGIN, PLUGINS_PATH, CONFIG_FILE_NAME);
        this.extensionsManager = extensionsManager;
    }

    @Override
    protected void executeAfterStartJar(PluginFileInfo plugin) {
        this.initializeExtensions(plugin);
    }

    private void initializeExtensions(PluginFileInfo pluginInfo) {
        try {
            this.extensionsManager.initExtensions(pluginInfo.getId(), this.getExtensions(EventManagerExtension.class, pluginInfo.getId()));
        }
        catch (Throwable e) {
            throw new PluginInitializationException(pluginInfo.getId(), e);
        }
    }

    @Override
    protected void executeAfterStopJar(PluginFileInfo plugin) {
        this.extensionsManager.terminateExtensions(plugin.getId());
    }

    @Override
    protected void executeBeforeUninstallJar(String pluginId) {
    }

    @Override
    protected List<Class<?>> getExtensionClasses() {
        return List.of(EventManagerExtension.class);
    }
}

