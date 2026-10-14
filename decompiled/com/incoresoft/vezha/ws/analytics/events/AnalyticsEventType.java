/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.ws.analytics.events;

public enum AnalyticsEventType {
    STATUS_CHANGED,
    ANALYTICS_SERVER_ERROR;


    public String toString() {
        return this.name().toLowerCase();
    }
}

