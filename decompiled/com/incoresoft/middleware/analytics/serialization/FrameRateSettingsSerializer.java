/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.middleware.analytics.models.settings.framesettings.FpsMode;
import com.incoresoft.middleware.analytics.models.settings.framesettings.KeyFrameMode;
import com.incoresoft.middleware.analytics.models.settings.framesettings.OnceEveryXSecondsMode;
import com.incoresoft.middleware.model.analytics.module.hardware.FrameRateSettingsMode;
import java.lang.reflect.Type;

public class FrameRateSettingsSerializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String frameRateSettingsJSONString = parser.parseObject(String.class);
        JSONObject frameRateSettings = JSON.parseObject(frameRateSettingsJSONString);
        if (frameRateSettings == null) {
            return null;
        }
        FrameRateSettingsMode mode = frameRateSettings.getObject("mode", FrameRateSettingsMode.class);
        if (mode == null) {
            return null;
        }
        switch (mode) {
            case fps: {
                return (T)JSON.parseObject(frameRateSettingsJSONString, FpsMode.class);
            }
            case key_frame: {
                return (T)JSON.parseObject(frameRateSettingsJSONString, KeyFrameMode.class);
            }
            case once_every_x_seconds: {
                return (T)JSON.parseObject(frameRateSettingsJSONString, OnceEveryXSecondsMode.class);
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 14;
    }
}

