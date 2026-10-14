/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core.installations.listeners;

import com.incoresoft.core.installations.CoreServiceUpdatesRegistry;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.services.installations.CoreServiceInstallationsSSEBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreUpdateListener
implements InstallationListener {
    private static final Logger log = LoggerFactory.getLogger(CoreUpdateListener.class);
    private final CoreService service;
    private final CoreServiceUpdatesRegistry installationsRegistry;
    private final CoreServiceInstallationsSSEBroadcaster broadcaster;

    @Override
    public void onStart() {
        log.info("\"{}\" update started.", (Object)this.service.getName());
        this.broadcaster.broadcastInstallationStarted(this.service.getId());
    }

    @Override
    public void onPartDownloaded(double downloadedPart) {
        log.info(String.format("\"%s\" update download - %.2f%%.", this.service.getId(), downloadedPart));
        this.broadcaster.broadcastInstallationProgress(this.service.getId(), downloadedPart);
    }

    @Override
    public void onDownloaded() {
        log.info(String.format("\"%s\" update downloaded.", this.service.getId()));
    }

    @Override
    public void onError(Throwable e) {
        log.error(String.format("Exception during \"%s\" update", this.service.getId()), e);
        this.installationsRegistry.unregister();
        this.broadcaster.broadcastInstallationFailed(this.service.getId());
    }

    @Override
    public void onPreInstall() {
        log.info(String.format("\"%s\" update installed. %s will be restarted", this.service.getId(), this.service.getId()));
        this.installationsRegistry.unregister();
        this.broadcaster.broadcastInstallationCompleted(this.service.getId());
    }

    @Override
    public void onInstalled() {
    }

    public CoreUpdateListener(CoreService service2, CoreServiceUpdatesRegistry installationsRegistry, CoreServiceInstallationsSSEBroadcaster broadcaster) {
        this.service = service2;
        this.installationsRegistry = installationsRegistry;
        this.broadcaster = broadcaster;
    }
}

