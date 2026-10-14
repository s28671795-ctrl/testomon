/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import org.apache.activemq.management.BoundedRangeStatisticImpl;
import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.JCAConnectionStatsImpl;
import org.apache.activemq.management.RangeStatisticImpl;
import org.apache.activemq.management.TimeStatisticImpl;

public class JCAConnectionPoolStatsImpl
extends JCAConnectionStatsImpl {
    private CountStatisticImpl closeCount;
    private CountStatisticImpl createCount;
    private BoundedRangeStatisticImpl freePoolSize;
    private BoundedRangeStatisticImpl poolSize;
    private RangeStatisticImpl waitingThreadCount;

    public JCAConnectionPoolStatsImpl(String connectionFactory, String managedConnectionFactory, TimeStatisticImpl waitTime, TimeStatisticImpl useTime, CountStatisticImpl closeCount, CountStatisticImpl createCount, BoundedRangeStatisticImpl freePoolSize, BoundedRangeStatisticImpl poolSize, RangeStatisticImpl waitingThreadCount) {
        super(connectionFactory, managedConnectionFactory, waitTime, useTime);
        this.closeCount = closeCount;
        this.createCount = createCount;
        this.freePoolSize = freePoolSize;
        this.poolSize = poolSize;
        this.waitingThreadCount = waitingThreadCount;
        this.addStatistic("freePoolSize", freePoolSize);
        this.addStatistic("poolSize", poolSize);
        this.addStatistic("waitingThreadCount", waitingThreadCount);
    }

    public CountStatisticImpl getCloseCount() {
        return this.closeCount;
    }

    public CountStatisticImpl getCreateCount() {
        return this.createCount;
    }

    public BoundedRangeStatisticImpl getFreePoolSize() {
        return this.freePoolSize;
    }

    public BoundedRangeStatisticImpl getPoolSize() {
        return this.poolSize;
    }

    public RangeStatisticImpl getWaitingThreadCount() {
        return this.waitingThreadCount;
    }
}

