/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.EJBStats;
import javax.management.j2ee.statistics.RangeStatistic;

public interface SessionBeanStats
extends EJBStats {
    public RangeStatistic getMethodReadyCount();
}

