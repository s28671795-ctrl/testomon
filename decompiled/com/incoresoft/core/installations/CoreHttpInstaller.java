/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core.installations;

import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.http.HttpInstaller;
import com.incoresoft.marketplace.models.ProductVersion;
import java.nio.file.Path;

public class CoreHttpInstaller
extends HttpInstaller {
    private final ApplicationReloadManager reloadManager;

    public CoreHttpInstaller(ApplicationReloadManager reloadManager, ProductVersion productVersion, Path tempInstallPath, Path installPath) {
        super(productVersion.getDownloadUrl(), tempInstallPath, installPath, productVersion.getHash(), 100);
        this.reloadManager = reloadManager;
    }

    @Override
    protected void install() {
        this.reloadManager.reload();
    }
}

