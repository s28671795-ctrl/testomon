/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.settings;

import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import java.util.List;

public interface Settings {
    public String getStorage();

    public List<WindowsShareConfig> getWindowsShareConfigs();
}

