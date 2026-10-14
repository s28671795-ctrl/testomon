/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.metadata;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.Instant;

public interface AnalyticsMetadata {
    @JSONField(name="va_id")
    public int getVideoAnalyticsId();

    @JSONField(name="timestamp")
    default public Instant getTimestamp() {
        return Instant.now();
    }
}

