/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.analytics.module.hardware;

import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettings;
import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettingsMode;
import com.incoresoft.middleware.model.analytics.module.hardware.Hardware;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareAcceleration;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareDecode;

public interface HardwareSettings {
    public Hardware getHardware();

    public HardwareAcceleration getAcceleration();

    public HardwareDecode getDecoding();

    default public FrameRateSettings getFrameRateSettings() {
        return new FrameRateSettings(this){

            @Override
            public FrameRateSettingsMode getMode() {
                return FrameRateSettingsMode.fps;
            }

            public int getFps() {
                return 25;
            }
        };
    }
}

