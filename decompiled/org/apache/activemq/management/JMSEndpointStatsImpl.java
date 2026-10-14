/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.JMSSessionStatsImpl;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.management.TimeStatisticImpl;
import org.apache.activemq.util.IndentPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSEndpointStatsImpl
extends StatsImpl {
    private static final Logger LOG = LoggerFactory.getLogger(JMSEndpointStatsImpl.class);
    protected CountStatisticImpl messageCount;
    protected CountStatisticImpl pendingMessageCount;
    protected CountStatisticImpl expiredMessageCount;
    protected TimeStatisticImpl messageWaitTime;
    protected TimeStatisticImpl messageRateTime;

    public JMSEndpointStatsImpl(JMSSessionStatsImpl sessionStats) {
        this();
        this.setParent(this.messageCount, sessionStats.getMessageCount());
        this.setParent(this.pendingMessageCount, sessionStats.getPendingMessageCount());
        this.setParent(this.expiredMessageCount, sessionStats.getExpiredMessageCount());
        this.setParent(this.messageWaitTime, sessionStats.getMessageWaitTime());
        this.setParent(this.messageRateTime, sessionStats.getMessageRateTime());
    }

    public JMSEndpointStatsImpl() {
        this(new CountStatisticImpl("messageCount", "Number of messages processed"), new CountStatisticImpl("pendingMessageCount", "Number of pending messages"), new CountStatisticImpl("expiredMessageCount", "Number of expired messages"), new TimeStatisticImpl("messageWaitTime", "Time spent by a message before being delivered"), new TimeStatisticImpl("messageRateTime", "Time taken to process a message (thoughtput rate)"));
    }

    public JMSEndpointStatsImpl(CountStatisticImpl messageCount, CountStatisticImpl pendingMessageCount, CountStatisticImpl expiredMessageCount, TimeStatisticImpl messageWaitTime, TimeStatisticImpl messageRateTime) {
        this.messageCount = messageCount;
        this.pendingMessageCount = pendingMessageCount;
        this.expiredMessageCount = expiredMessageCount;
        this.messageWaitTime = messageWaitTime;
        this.messageRateTime = messageRateTime;
        this.addStatistic("messageCount", messageCount);
        this.addStatistic("pendingMessageCount", pendingMessageCount);
        this.addStatistic("expiredMessageCount", expiredMessageCount);
        this.addStatistic("messageWaitTime", messageWaitTime);
        this.addStatistic("messageRateTime", messageRateTime);
    }

    @Override
    public synchronized void reset() {
        super.reset();
        this.messageCount.reset();
        this.messageRateTime.reset();
        this.pendingMessageCount.reset();
        this.expiredMessageCount.reset();
        this.messageWaitTime.reset();
    }

    public CountStatisticImpl getMessageCount() {
        return this.messageCount;
    }

    public CountStatisticImpl getPendingMessageCount() {
        return this.pendingMessageCount;
    }

    public CountStatisticImpl getExpiredMessageCount() {
        return this.expiredMessageCount;
    }

    public TimeStatisticImpl getMessageRateTime() {
        return this.messageRateTime;
    }

    public TimeStatisticImpl getMessageWaitTime() {
        return this.messageWaitTime;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.messageCount);
        buffer.append(" ");
        buffer.append(this.messageRateTime);
        buffer.append(" ");
        buffer.append(this.pendingMessageCount);
        buffer.append(" ");
        buffer.append(this.expiredMessageCount);
        buffer.append(" ");
        buffer.append(this.messageWaitTime);
        return buffer.toString();
    }

    public void onMessage() {
        if (this.enabled) {
            long start2 = this.messageCount.getLastSampleTime();
            this.messageCount.increment();
            long end = this.messageCount.getLastSampleTime();
            this.messageRateTime.addTime(end - start2);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.messageCount.setEnabled(enabled);
        this.messageRateTime.setEnabled(enabled);
        this.pendingMessageCount.setEnabled(enabled);
        this.expiredMessageCount.setEnabled(enabled);
        this.messageWaitTime.setEnabled(enabled);
    }

    public void dump(IndentPrinter out) {
        out.printIndent();
        out.println(this.messageCount);
        out.printIndent();
        out.println(this.messageRateTime);
        out.printIndent();
        out.println(this.pendingMessageCount);
        out.printIndent();
        out.println(this.messageRateTime);
        out.printIndent();
        out.println(this.expiredMessageCount);
        out.printIndent();
        out.println(this.messageWaitTime);
    }

    protected void setParent(CountStatisticImpl child, CountStatisticImpl parent) {
        if (child instanceof CountStatisticImpl && parent instanceof CountStatisticImpl) {
            CountStatisticImpl c = child;
            c.setParent(parent);
        } else {
            LOG.warn("Cannot associate endpoint counters with session level counters as they are not both CountStatisticImpl clases. Endpoint: " + child + " session: " + parent);
        }
    }

    protected void setParent(TimeStatisticImpl child, TimeStatisticImpl parent) {
        if (child instanceof TimeStatisticImpl && parent instanceof TimeStatisticImpl) {
            TimeStatisticImpl c = child;
            c.setParent(parent);
        } else {
            LOG.warn("Cannot associate endpoint counters with session level counters as they are not both TimeStatisticImpl clases. Endpoint: " + child + " session: " + parent);
        }
    }
}

