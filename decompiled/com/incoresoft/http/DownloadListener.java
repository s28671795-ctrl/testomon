/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http;

public interface DownloadListener {
    public void onStart();

    public void onPartDownloaded(double var1);

    public void onDownloaded();

    public void onError(Throwable var1);
}

