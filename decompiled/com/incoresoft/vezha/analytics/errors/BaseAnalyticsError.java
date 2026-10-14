/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.errors;

import com.incoresoft.middleware.analytics.AnalyticsError;
import com.incoresoft.vezha.analytics.errors.AnalyticsErrorOrigin;

public interface BaseAnalyticsError
extends AnalyticsError {
    public AnalyticsErrorOrigin getOrigin();
}

