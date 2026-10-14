/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.Stats;
import javax.management.j2ee.statistics.TimeStatistic;

public interface JDBCConnectionStats
extends Stats {
    public String getJdbcDataSource();

    public TimeStatistic getWaitTime();

    public TimeStatistic getUseTime();
}

