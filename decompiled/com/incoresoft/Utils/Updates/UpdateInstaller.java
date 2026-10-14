/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.Updates;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class UpdateInstaller {
    protected final Set<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();

    public void subscribe(UpdateListener listener) {
        this.listeners.add(Objects.requireNonNull(listener, "listener"));
    }

    public abstract CompletableFuture<Void> invoke();

    protected void onStart() {
        this.invokeListeners(UpdateListener::onStart);
    }

    protected void onPartDownloaded(double percent) {
        this.invokeListeners(it -> it.onPartDownloaded(percent));
    }

    protected void onDownloaded() {
        this.invokeListeners(UpdateListener::onDownloaded);
    }

    protected void onChecksum(String expectedHash, String actualHash) {
        this.invokeListeners(it -> it.onChecksum(expectedHash, actualHash));
    }

    protected void onPreInstall() {
        this.invokeListeners(UpdateListener::onPreInstall);
    }

    protected void onInstalled() {
        this.invokeListeners(UpdateListener::onInstalled);
    }

    protected void onError(Throwable e) {
        this.invokeListeners(it -> it.onError(e));
    }

    private void invokeListeners(Consumer<UpdateListener> listener) {
        for (UpdateListener it : this.listeners) {
            try {
                listener.accept(it);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static interface UpdateListener {
        public void onStart();

        public void onPartDownloaded(double var1);

        public void onDownloaded();

        public void onChecksum(String var1, String var2);

        public void onInstalled();

        default public void onPreInstall() {
        }

        public void onError(Throwable var1);
    }
}

