/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http;

import com.incoresoft.http.HttpHashValidatingDownloader;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpInstaller
extends HttpHashValidatingDownloader {
    private static final Logger log = LoggerFactory.getLogger(HttpInstaller.class);

    public HttpInstaller(String downloadUrl, Path tempInstallPath, Path installPath, String expectedHash, int chunksNumber) {
        super(downloadUrl, tempInstallPath, installPath, expectedHash, chunksNumber);
    }

    protected abstract void install();

    @Override
    protected void onDownloaded() {
        super.onDownloaded();
        List<InstallationListener> installationListeners = this.getInstallationListeners();
        ListenersUtils.triggerListeners(InstallationListener::onPreInstall, installationListeners);
        this.install();
        ListenersUtils.triggerListeners(InstallationListener::onInstalled, installationListeners);
    }

    private List<InstallationListener> getInstallationListeners() {
        return this.listeners.stream().filter(l -> l instanceof InstallationListener).map(l -> (InstallationListener)l).toList();
    }
}

