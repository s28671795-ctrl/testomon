/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.service;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.Instant;

public interface ServiceMetadata {
    @JSONField(name="timestamp")
    default public long getTimestamp() {
        return Instant.now().toEpochMilli();
    }
}

