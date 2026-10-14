/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee.statistics;

import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.JMSConsumerStats;
import javax.management.j2ee.statistics.JMSProducerStats;
import javax.management.j2ee.statistics.Stats;
import javax.management.j2ee.statistics.TimeStatistic;

public interface JMSSessionStats
extends Stats {
    public JMSProducerStats[] getProducers();

    public JMSConsumerStats[] getConsumers();

    public CountStatistic getMessageCount();

    public CountStatistic getPendingMessageCount();

    public CountStatistic getExpiredMessageCount();

    public TimeStatistic getMessageWaitTime();

    public CountStatistic getDurableSubscriptionCount();
}

