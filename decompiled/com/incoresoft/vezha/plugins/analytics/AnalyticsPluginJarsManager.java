/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins.analytics;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.database.EnabledPluginsRepository;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugins.BasePluginJarsManager;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.JavalinEndpointTracker;
import com.incoresoft.plugins.PluginIconEndpointInitializer;
import com.incoresoft.plugins.broadcast.PluginStateSSEBroadcaster;
import com.incoresoft.plugins.exceptions.PluginInitializationException;
import com.incoresoft.plugins.models.PluginFileInfo;
import com.incoresoft.plugins.models.PluginType;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsPluginJarsManager
extends BasePluginJarsManager {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsPluginJarsManager.class);
    private static final String PLUGINS_PATH = "plugins/analytics";
    private static final String CONFIG_FILE_NAME = "enabled_analytics_plugins.json";
    private final AnalyticsManager analyticsManager;
    private final ExtensionsManager extensionsManager;

    @Inject
    public AnalyticsPluginJarsManager(AnalyticsManager analyticsManager, ExtensionsManager extensionsManager, CoreServiceVersionSupplier coreServiceVersionSupplier, JarFileManifestReader manifestReader, JavalinEndpointTracker endpointTracker, PluginIconEndpointInitializer iconEndpointInitializer, PluginStateSSEBroadcaster broadcaster, EnabledPluginsRepository enabledPluginsRepository) {
        super(manifestReader, coreServiceVersionSupplier, endpointTracker, iconEndpointInitializer, broadcaster, enabledPluginsRepository, PluginType.ANALYTICS_PLUGIN, PLUGINS_PATH, CONFIG_FILE_NAME);
        this.analyticsManager = analyticsManager;
        this.extensionsManager = extensionsManager;
    }

    @Override
    protected void executeAfterStartJar(PluginFileInfo plugin) {
        this.initializeExtensions(plugin);
    }

    private void initializeExtensions(PluginFileInfo pluginInfo) {
        try {
            this.extensionsManager.initExtensions(pluginInfo.getId(), this.getExtensions(VezhaExtension.class, pluginInfo.getId()));
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
        this.stopAnalytics(this.extensionsManager.getAll(pluginId, VezhaExtension.class));
    }

    @Override
    protected List<Class<?>> getExtensionClasses() {
        return List.of(VezhaExtension.class);
    }

    private void stopAnalytics(Collection<VezhaExtension> extensions) {
        extensions.forEach(this::stopAnalytics);
    }

    private void stopAnalytics(VezhaExtension extension) {
        List<Analytics> analyticsToStop = this.analyticsManager.getAll().stream().filter(it -> Objects.equals(it.getPluginName(), extension.getPluginName())).filter(it -> it.getStatus() == AnalyticsStatus.processing || it.getStatus() == AnalyticsStatus.started || it.getStatus() == AnalyticsStatus.paused).toList();
        for (Analytics analytics : analyticsToStop) {
            this.analyticsManager.stop(analytics.getId());
        }
    }
}

