/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.errors;

import com.incoresoft.vezha.analytics.errors.AnalyticsErrorOrigin;
import com.incoresoft.vezha.analytics.errors.BaseAnalyticsError;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class AnalyticsSideError
implements BaseAnalyticsError {
    private final String localeKey;

    public AnalyticsSideError(@NotNull String localeKey) {
        this.localeKey = Objects.requireNonNull(localeKey);
    }

    @Override
    public AnalyticsErrorOrigin getOrigin() {
        return AnalyticsErrorOrigin.ANALYTICS;
    }

    @Override
    public String getLocaleKey() {
        return this.localeKey;
    }
}

