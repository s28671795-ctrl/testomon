/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Date;
import java.util.Map;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;

public interface ExecutionStatistics {
    public Date getLastStartTime();

    public Map<Long, TimeWindowStatistics> getTimeWindowStatistics();

    @Deprecated
    public ExecutionStatistics snapshot();
}

