/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.Stats;
import javax.management.j2ee.statistics.TimeStatistic;

public interface JCAConnectionStats
extends Stats {
    public String getConnectionFactory();

    public String getManagedConnectionFactory();

    public TimeStatistic getWaitTime();

    public TimeStatistic getUseTime();
}

