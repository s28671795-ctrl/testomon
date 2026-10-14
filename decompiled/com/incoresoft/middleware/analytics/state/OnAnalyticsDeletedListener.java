/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.state;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;

public interface OnAnalyticsDeletedListener
extends AnalyticsStateListener {
    @Override
    default public void onAnalyticsStarted(Analytics analytics) {
    }

    @Override
    default public void onAnalyticsStopped(Analytics analytics) {
    }

    @Override
    public void onAnalyticsDeleted(Analytics var1);
}

