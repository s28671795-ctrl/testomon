/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.listeners;

import com.google.inject.Inject;
import com.incoresoft.extensions.ExtensionListener;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.mq.AnalyticsListenerManagerImpl;

public class AnalyticsExtensionListener
implements ExtensionListener {
    private final AnalyticsManagerImpl analyticsManager;
    private final AnalyticsListenerManagerImpl analyticsListenerManager;
    private final ExtensionsManager extensionsManager;

    @Inject
    public void init() {
        this.extensionsManager.addListener(this);
    }

    @Override
    public void onExtensionInit(InitializableExtension<?> extension, String pluginId) {
        if (extension instanceof VezhaExtension) {
            VezhaExtension vezhaExtension = (VezhaExtension)extension;
            this.analyticsManager.loadByPlugin(vezhaExtension.getPluginName());
        }
    }

    @Override
    public void onExtensionTerminate(InitializableExtension<?> extension, String pluginId) {
        if (extension instanceof VezhaExtension) {
            VezhaExtension vezhaExtension = (VezhaExtension)extension;
            this.analyticsListenerManager.unregister(vezhaExtension);
            this.analyticsManager.removeListeners(vezhaExtension);
        }
    }

    @Inject
    public AnalyticsExtensionListener(AnalyticsManagerImpl analyticsManager, AnalyticsListenerManagerImpl analyticsListenerManager, ExtensionsManager extensionsManager) {
        this.analyticsManager = analyticsManager;
        this.analyticsListenerManager = analyticsListenerManager;
        this.extensionsManager = extensionsManager;
    }
}

