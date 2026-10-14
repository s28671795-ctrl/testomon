/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http;

import com.incoresoft.http.DownloadListener;

public interface InstallationListener
extends DownloadListener {
    public void onInstalled();

    public void onPreInstall();
}

