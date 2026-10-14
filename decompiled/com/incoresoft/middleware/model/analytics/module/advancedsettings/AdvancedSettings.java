/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.advancedsettings;

import com.incoresoft.middleware.model.analytics.module.advancedsettings.ModelOptions;

public interface AdvancedSettings {
    default public float getSensitivity() {
        return 5.0f;
    }

    default public int getTrackerSensitivity() {
        return 8;
    }

    default public ModelOptions getModel() {
        return ModelOptions.quality;
    }

    default public int getTrackerBufferTime() {
        return 10;
    }

    default public int getMinHeight() {
        return 25;
    }

    default public int getMinWidth() {
        return 25;
    }
}

