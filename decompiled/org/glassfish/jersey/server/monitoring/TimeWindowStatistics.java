/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

public interface TimeWindowStatistics {
    public long getTimeWindow();

    public double getRequestsPerSecond();

    public long getMinimumDuration();

    public long getMaximumDuration();

    public long getAverageDuration();

    public long getRequestCount();

    @Deprecated
    public TimeWindowStatistics snapshot();
}

