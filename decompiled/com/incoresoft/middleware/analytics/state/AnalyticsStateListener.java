/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.state;

import com.incoresoft.middleware.analytics.Analytics;

public interface AnalyticsStateListener {
    default public void onAnalyticsPending(Analytics analytics) {
    }

    public void onAnalyticsStarted(Analytics var1);

    public void onAnalyticsStopped(Analytics var1);

    default public void onAnalyticsPaused(Analytics analytics) {
    }

    default public void onAnalyticsAdded(Analytics analytics) {
    }

    default public void onAnalyticsUpdated(Analytics analytics) {
    }

    default public void onAnalyticsDeleted(Analytics analytics) {
    }
}

