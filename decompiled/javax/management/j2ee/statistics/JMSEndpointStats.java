/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.Stats;
import javax.management.j2ee.statistics.TimeStatistic;

public interface JMSEndpointStats
extends Stats {
    public CountStatistic getMessageCount();

    public CountStatistic getPendingMessageCount();

    public CountStatistic getExpiredMessageCount();

    public TimeStatistic getMessageWaitTime();
}

