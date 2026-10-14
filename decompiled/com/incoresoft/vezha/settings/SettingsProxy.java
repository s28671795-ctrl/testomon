/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.settings;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.settings.Settings;
import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import com.incoresoft.vezha.windowsshare.WindowsShareConfigManager;
import java.util.List;

public class SettingsProxy
implements Settings {
    private final WindowsShareConfigManager windowsShareConfigManager;

    @Override
    public String getStorage() {
        throw new UnsupportedOperationException("Use Storage interface directly");
    }

    @Override
    public List<WindowsShareConfig> getWindowsShareConfigs() {
        return List.copyOf(this.windowsShareConfigManager.getAll());
    }

    @Inject
    public SettingsProxy(WindowsShareConfigManager windowsShareConfigManager) {
        this.windowsShareConfigManager = windowsShareConfigManager;
    }
}

