/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.cleaner;

import com.incoresoft.middleware.cleaner.CleaningCategory;
import java.time.Duration;
import java.util.Map;

public interface CleaningSettingsDTO {
    public Map<CleaningCategory, Duration> asMap();
}

