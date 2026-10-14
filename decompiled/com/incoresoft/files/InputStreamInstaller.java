/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.files;

import com.incoresoft.files.InputStreamDownloader;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public abstract class InputStreamInstaller
extends InputStreamDownloader {
    protected final Path installPath;

    public InputStreamInstaller(InputStream inputStream2, long size, Path tempInstallPath, Path installPath, int chunksNumber) {
        super(inputStream2, size, tempInstallPath, chunksNumber);
        this.installPath = installPath;
    }

    protected abstract void install();

    protected abstract void validate();

    @Override
    protected void onDownloaded() {
        super.onDownloaded();
        try {
            this.validate();
            this.moveFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<InstallationListener> installationListeners = this.getInstallationListeners();
        ListenersUtils.triggerListeners(InstallationListener::onPreInstall, installationListeners);
        this.install();
        ListenersUtils.triggerListeners(InstallationListener::onInstalled, installationListeners);
    }

    private List<InstallationListener> getInstallationListeners() {
        return this.listeners.stream().filter(l -> l instanceof InstallationListener).map(l -> (InstallationListener)l).toList();
    }

    private void moveFile() throws IOException {
        if (Files.exists(this.installPath, new LinkOption[0])) {
            Files.copy(this.installPath, Path.of(String.valueOf(this.installPath) + ".bak", new String[0]), StandardCopyOption.REPLACE_EXISTING);
        }
        Files.move(this.downloadPath, this.installPath, StandardCopyOption.REPLACE_EXISTING);
    }
}

