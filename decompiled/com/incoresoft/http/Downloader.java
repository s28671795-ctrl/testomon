/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http;

import com.incoresoft.http.DownloadListener;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class Downloader {
    protected final Set<DownloadListener> listeners = new LinkedHashSet<DownloadListener>();
    protected final Path downloadPath;

    protected Downloader(Path downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void subscribe(DownloadListener listener) {
        this.listeners.add(Objects.requireNonNull(listener, "listener"));
    }

    public abstract CompletableFuture<Void> invoke();

    protected void onStart() {
        ListenersUtils.triggerListeners(DownloadListener::onStart, this.listeners);
    }

    protected void onPartDownloaded(double downloadedPart) {
        ListenersUtils.triggerListeners(l -> l.onPartDownloaded(downloadedPart), this.listeners);
    }

    protected void onDownloaded() {
        ListenersUtils.triggerListeners(DownloadListener::onDownloaded, this.listeners);
    }

    protected void onError(Throwable e) {
        try {
            Files.deleteIfExists(this.downloadPath);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        ListenersUtils.triggerListeners(l -> l.onError(e), this.listeners);
    }
}

