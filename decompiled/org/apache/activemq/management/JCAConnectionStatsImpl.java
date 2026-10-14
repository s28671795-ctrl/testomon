/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.management.TimeStatisticImpl;

public class JCAConnectionStatsImpl
extends StatsImpl {
    private String connectionFactory;
    private String managedConnectionFactory;
    private TimeStatisticImpl waitTime;
    private TimeStatisticImpl useTime;

    public JCAConnectionStatsImpl(String connectionFactory, String managedConnectionFactory, TimeStatisticImpl waitTime, TimeStatisticImpl useTime) {
        this.connectionFactory = connectionFactory;
        this.managedConnectionFactory = managedConnectionFactory;
        this.waitTime = waitTime;
        this.useTime = useTime;
        this.addStatistic("waitTime", waitTime);
        this.addStatistic("useTime", useTime);
    }

    public String getConnectionFactory() {
        return this.connectionFactory;
    }

    public String getManagedConnectionFactory() {
        return this.managedConnectionFactory;
    }

    public TimeStatisticImpl getWaitTime() {
        return this.waitTime;
    }

    public TimeStatisticImpl getUseTime() {
        return this.useTime;
    }
}

