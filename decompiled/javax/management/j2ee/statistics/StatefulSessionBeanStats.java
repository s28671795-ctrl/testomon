/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.RangeStatistic;
import javax.management.j2ee.statistics.SessionBeanStats;

public interface StatefulSessionBeanStats
extends SessionBeanStats {
    public RangeStatistic getPassiveCount();
}

