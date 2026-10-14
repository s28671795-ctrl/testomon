/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.license;

import com.incoresoft.license.model.LicenseGrabStatus;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AnalyticUsageManager {
    private final int allowedAnalytics;
    private final Set<Integer> licensedAnalytics;

    public AnalyticUsageManager(int maxAllowedAnalytics) {
        this.allowedAnalytics = maxAllowedAnalytics;
        this.licensedAnalytics = Collections.newSetFromMap(new ConcurrentHashMap(maxAllowedAnalytics));
    }

    public AnalyticUsageManager resize(int maxAllowedAnalytics) {
        AnalyticUsageManager usageManager = new AnalyticUsageManager(maxAllowedAnalytics);
        this.licensedAnalytics.forEach(analytic -> {
            this.releaseLicense((int)analytic);
            usageManager.grabLicense((int)analytic);
        });
        return usageManager;
    }

    public int allowedAnalyticsCount() {
        return this.allowedAnalytics;
    }

    public int licensedAnalyticsCount() {
        return this.licensedAnalytics.size();
    }

    public LicenseGrabStatus checkGrabLicense(int analyticsId) {
        if (this.allowedAnalytics == 0) {
            return LicenseGrabStatus.UNAVAILABLE;
        }
        if (this.licensed(analyticsId) || this.licensedAnalytics.size() < this.allowedAnalytics) {
            return LicenseGrabStatus.LICENSED;
        }
        return LicenseGrabStatus.NOT_LICENSED;
    }

    public LicenseGrabStatus grabLicense(int analyticsId) {
        LicenseGrabStatus status = this.checkGrabLicense(analyticsId);
        if (status == LicenseGrabStatus.LICENSED) {
            this.licensedAnalytics.add(analyticsId);
        }
        return status;
    }

    public void releaseLicense(int analyticsId) {
        this.licensedAnalytics.remove(analyticsId);
    }

    public boolean licensed(int analyticsId) {
        return this.licensedAnalytics.contains(analyticsId);
    }
}

