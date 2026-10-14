/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.Statistic;

public interface Stats {
    public Statistic getStatistic(String var1);

    public String[] getStatisticNames();

    public Statistic[] getStatistics();
}

