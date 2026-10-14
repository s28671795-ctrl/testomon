/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.Statistic;

public interface RangeStatistic
extends Statistic {
    public long getHighWaterMark();

    public long getLowWaterMark();

    public long getCurrent();
}

