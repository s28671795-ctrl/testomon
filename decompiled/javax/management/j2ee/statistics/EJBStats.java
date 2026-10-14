/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.Stats;

public interface EJBStats
extends Stats {
    public CountStatistic getCreateCount();

    public CountStatistic getRemoveCount();
}

