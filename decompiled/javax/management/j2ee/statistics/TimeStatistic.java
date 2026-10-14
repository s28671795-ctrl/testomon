/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.Statistic;

public interface TimeStatistic
extends Statistic {
    public long getCount();

    public long getMaxTime();

    public long getMinTime();

    public long getTotalTime();
}

