/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.errors;

import com.incoresoft.vezha.analytics.errors.AnalyticsErrorOrigin;
import com.incoresoft.vezha.analytics.errors.BaseAnalyticsError;
import java.util.Objects;

public enum CoreAnalyticsErrors implements BaseAnalyticsError
{
    NO_AVAILABLE_SERVER(AnalyticsErrorOrigin.CORE),
    NO_AVAILABLE_LICENSE(AnalyticsErrorOrigin.CORE),
    NO_SPACE_ON_DISK(AnalyticsErrorOrigin.CORE);

    private final String localeKey = this.name();
    private final AnalyticsErrorOrigin origin;

    private CoreAnalyticsErrors(AnalyticsErrorOrigin origin) {
        this.origin = Objects.requireNonNull(origin);
    }

    @Override
    public String getLocaleKey() {
        return this.localeKey;
    }

    @Override
    public AnalyticsErrorOrigin getOrigin() {
        return this.origin;
    }
}

