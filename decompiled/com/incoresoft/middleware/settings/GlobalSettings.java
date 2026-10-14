/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.settings;

import com.incoresoft.middleware.settings.OnSettingsChangeListener;
import java.util.Map;

public interface GlobalSettings {
    public Map<String, Object> getSettingsMap();

    default public Object getValue(String key) {
        return this.getSettingsMap().get(key);
    }

    public void addListener(OnSettingsChangeListener var1);

    public void removeListener(OnSettingsChangeListener var1);
}

