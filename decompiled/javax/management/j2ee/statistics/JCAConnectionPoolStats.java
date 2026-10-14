/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.BoundedRangeStatistic;
import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.JCAConnectionStats;
import javax.management.j2ee.statistics.RangeStatistic;

public interface JCAConnectionPoolStats
extends JCAConnectionStats {
    public CountStatistic getCloseCount();

    public CountStatistic getCreateCount();

    public BoundedRangeStatistic getFreePoolSize();

    public BoundedRangeStatistic getPoolSize();

    public RangeStatistic getWaitingThreadCount();
}

