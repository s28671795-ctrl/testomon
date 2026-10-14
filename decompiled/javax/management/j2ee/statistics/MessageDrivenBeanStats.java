/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.EJBStats;

public interface MessageDrivenBeanStats
extends EJBStats {
    public CountStatistic getMessageCount();
}

