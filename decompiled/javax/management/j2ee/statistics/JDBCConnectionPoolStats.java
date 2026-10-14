/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.BoundedRangeStatistic;
import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.JDBCConnectionStats;
import javax.management.j2ee.statistics.RangeStatistic;

public interface JDBCConnectionPoolStats
extends JDBCConnectionStats {
    public CountStatistic getCreateCount();

    public CountStatistic getCloseCount();

    public BoundedRangeStatistic getPoolSize();

    public BoundedRangeStatistic getFreePoolSize();

    public RangeStatistic getWaitingThreadCount();
}

