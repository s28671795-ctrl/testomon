/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import java.util.List;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.JMSConsumerStatsImpl;
import org.apache.activemq.management.JMSProducerStatsImpl;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.management.TimeStatisticImpl;
import org.apache.activemq.util.IndentPrinter;

public class JMSSessionStatsImpl
extends StatsImpl {
    private List producers;
    private List consumers;
    private CountStatisticImpl messageCount;
    private CountStatisticImpl pendingMessageCount;
    private CountStatisticImpl expiredMessageCount;
    private TimeStatisticImpl messageWaitTime;
    private CountStatisticImpl durableSubscriptionCount;
    private TimeStatisticImpl messageRateTime;

    public JMSSessionStatsImpl(List producers, List consumers) {
        this.producers = producers;
        this.consumers = consumers;
        this.messageCount = new CountStatisticImpl("messageCount", "Number of messages exchanged");
        this.pendingMessageCount = new CountStatisticImpl("pendingMessageCount", "Number of pending messages");
        this.expiredMessageCount = new CountStatisticImpl("expiredMessageCount", "Number of expired messages");
        this.messageWaitTime = new TimeStatisticImpl("messageWaitTime", "Time spent by a message before being delivered");
        this.durableSubscriptionCount = new CountStatisticImpl("durableSubscriptionCount", "The number of durable subscriptions");
        this.messageWaitTime = new TimeStatisticImpl("messageWaitTime", "Time spent by a message before being delivered");
        this.messageRateTime = new TimeStatisticImpl("messageRateTime", "Time taken to process a message (thoughtput rate)");
        this.addStatistic("messageCount", this.messageCount);
        this.addStatistic("pendingMessageCount", this.pendingMessageCount);
        this.addStatistic("expiredMessageCount", this.expiredMessageCount);
        this.addStatistic("messageWaitTime", this.messageWaitTime);
        this.addStatistic("durableSubscriptionCount", this.durableSubscriptionCount);
        this.addStatistic("messageRateTime", this.messageRateTime);
    }

    public JMSProducerStatsImpl[] getProducers() {
        Object[] producerArray = this.producers.toArray();
        int size = producerArray.length;
        JMSProducerStatsImpl[] answer = new JMSProducerStatsImpl[size];
        for (int i = 0; i < size; ++i) {
            ActiveMQMessageProducer producer = (ActiveMQMessageProducer)producerArray[i];
            answer[i] = producer.getProducerStats();
        }
        return answer;
    }

    public JMSConsumerStatsImpl[] getConsumers() {
        Object[] consumerArray = this.consumers.toArray();
        int size = consumerArray.length;
        JMSConsumerStatsImpl[] answer = new JMSConsumerStatsImpl[size];
        for (int i = 0; i < size; ++i) {
            ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer)consumerArray[i];
            answer[i] = consumer.getConsumerStats();
        }
        return answer;
    }

    @Override
    public void reset() {
        super.reset();
        JMSConsumerStatsImpl[] cstats = this.getConsumers();
        int size = cstats.length;
        for (int i = 0; i < size; ++i) {
            cstats[i].reset();
        }
        JMSProducerStatsImpl[] pstats = this.getProducers();
        size = pstats.length;
        for (int i = 0; i < size; ++i) {
            pstats[i].reset();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        JMSConsumerStatsImpl[] cstats = this.getConsumers();
        int size = cstats.length;
        for (int i = 0; i < size; ++i) {
            cstats[i].setEnabled(enabled);
        }
        JMSProducerStatsImpl[] pstats = this.getProducers();
        size = pstats.length;
        for (int i = 0; i < size; ++i) {
            pstats[i].setEnabled(enabled);
        }
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

    public TimeStatisticImpl getMessageWaitTime() {
        return this.messageWaitTime;
    }

    public CountStatisticImpl getDurableSubscriptionCount() {
        return this.durableSubscriptionCount;
    }

    public TimeStatisticImpl getMessageRateTime() {
        return this.messageRateTime;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(" ");
        buffer.append(this.messageCount);
        buffer.append(" ");
        buffer.append(this.messageRateTime);
        buffer.append(" ");
        buffer.append(this.pendingMessageCount);
        buffer.append(" ");
        buffer.append(this.expiredMessageCount);
        buffer.append(" ");
        buffer.append(this.messageWaitTime);
        buffer.append(" ");
        buffer.append(this.durableSubscriptionCount);
        buffer.append(" producers{ ");
        JMSProducerStatsImpl[] producerArray = this.getProducers();
        for (int i = 0; i < producerArray.length; ++i) {
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(Integer.toString(i));
            buffer.append(" = ");
            buffer.append(producerArray[i]);
        }
        buffer.append(" } consumers{ ");
        JMSConsumerStatsImpl[] consumerArray = this.getConsumers();
        for (int i = 0; i < consumerArray.length; ++i) {
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(Integer.toString(i));
            buffer.append(" = ");
            buffer.append(consumerArray[i]);
        }
        buffer.append(" }");
        return buffer.toString();
    }

    public void dump(IndentPrinter out) {
        out.printIndent();
        out.println(this.messageCount);
        out.printIndent();
        out.println(this.messageRateTime);
        out.printIndent();
        out.println(this.pendingMessageCount);
        out.printIndent();
        out.println(this.expiredMessageCount);
        out.printIndent();
        out.println(this.messageWaitTime);
        out.printIndent();
        out.println(this.durableSubscriptionCount);
        out.println();
        out.printIndent();
        out.println("producers {");
        out.incrementIndent();
        JMSProducerStatsImpl[] producerArray = this.getProducers();
        for (int i = 0; i < producerArray.length; ++i) {
            JMSProducerStatsImpl producer = producerArray[i];
            producer.dump(out);
        }
        out.decrementIndent();
        out.printIndent();
        out.println("}");
        out.printIndent();
        out.println("consumers {");
        out.incrementIndent();
        JMSConsumerStatsImpl[] consumerArray = this.getConsumers();
        for (int i = 0; i < consumerArray.length; ++i) {
            JMSConsumerStatsImpl consumer = consumerArray[i];
            consumer.dump(out);
        }
        out.decrementIndent();
        out.printIndent();
        out.println("}");
    }

    public void onCreateDurableSubscriber() {
        this.durableSubscriptionCount.increment();
    }

    public void onRemoveDurableSubscriber() {
        this.durableSubscriptionCount.decrement();
    }
}

