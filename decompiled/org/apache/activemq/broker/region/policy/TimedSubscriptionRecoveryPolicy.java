/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.thread.Scheduler;

public class TimedSubscriptionRecoveryPolicy
implements SubscriptionRecoveryPolicy {
    private static final int GC_INTERVAL = 1000;
    private Scheduler scheduler;
    private final List<TimestampWrapper> buffer = Collections.synchronizedList(new LinkedList());
    private volatile long lastGCRun = System.currentTimeMillis();
    private long recoverDuration = 60000L;
    private final Runnable gcTask = new Runnable(){

        @Override
        public void run() {
            TimedSubscriptionRecoveryPolicy.this.gc();
        }
    };

    @Override
    public SubscriptionRecoveryPolicy copy() {
        TimedSubscriptionRecoveryPolicy rc = new TimedSubscriptionRecoveryPolicy();
        rc.setRecoverDuration(this.recoverDuration);
        return rc;
    }

    @Override
    public boolean add(ConnectionContext context, MessageReference message) throws Exception {
        this.buffer.add(new TimestampWrapper(message, this.lastGCRun));
        return true;
    }

    @Override
    public void recover(ConnectionContext context, Topic topic, SubscriptionRecovery sub) throws Exception {
        ArrayList<TimestampWrapper> copy = new ArrayList<TimestampWrapper>(this.buffer);
        if (!copy.isEmpty()) {
            for (TimestampWrapper timestampWrapper : copy) {
                MessageReference message = timestampWrapper.message;
                sub.addRecoveredMessage(context, message);
            }
        }
    }

    @Override
    public void setBroker(Broker broker) {
        this.scheduler = broker.getScheduler();
    }

    @Override
    public void start() throws Exception {
        this.scheduler.executePeriodically(this.gcTask, 1000L);
    }

    @Override
    public void stop() throws Exception {
        this.scheduler.cancel(this.gcTask);
    }

    public void gc() {
        this.lastGCRun = System.currentTimeMillis();
        while (this.buffer.size() > 0) {
            TimestampWrapper timestampWrapper = this.buffer.get(0);
            if (this.lastGCRun <= timestampWrapper.timestamp + this.recoverDuration) break;
            this.buffer.remove(0);
        }
    }

    public long getRecoverDuration() {
        return this.recoverDuration;
    }

    public void setRecoverDuration(long recoverDuration) {
        this.recoverDuration = recoverDuration;
    }

    @Override
    public Message[] browse(ActiveMQDestination destination) throws Exception {
        ArrayList<Message> result = new ArrayList<Message>();
        ArrayList<TimestampWrapper> copy = new ArrayList<TimestampWrapper>(this.buffer);
        DestinationFilter filter2 = DestinationFilter.parseFilter(destination);
        for (TimestampWrapper timestampWrapper : copy) {
            MessageReference ref = timestampWrapper.message;
            Message message = ref.getMessage();
            if (!filter2.matches(message.getDestination())) continue;
            result.add(message);
        }
        return result.toArray(new Message[result.size()]);
    }

    static class TimestampWrapper {
        public MessageReference message;
        public long timestamp;

        public TimestampWrapper(MessageReference message, long timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }
    }
}

