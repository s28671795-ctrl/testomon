/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.BoundedRangeStatistic;
import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.Stats;

public interface JVMStats
extends Stats {
    public CountStatistic getUpTime();

    public BoundedRangeStatistic getHeapSize();
}

