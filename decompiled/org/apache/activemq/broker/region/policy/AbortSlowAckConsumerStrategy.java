/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.AbortSlowConsumerStrategy;
import org.apache.activemq.broker.region.policy.SlowConsumerEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbortSlowAckConsumerStrategy
extends AbortSlowConsumerStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(AbortSlowAckConsumerStrategy.class);
    private final Map<String, Destination> destinations = new ConcurrentHashMap<String, Destination>();
    private long maxTimeSinceLastAck = 30000L;
    private boolean ignoreIdleConsumers = true;

    public AbortSlowAckConsumerStrategy() {
        this.name = "AbortSlowAckConsumerStrategy@" + this.hashCode();
    }

    @Override
    public void setBrokerService(Broker broker) {
        super.setBrokerService(broker);
        if (this.taskStarted.compareAndSet(false, true)) {
            this.scheduler.executePeriodically(this, this.getCheckPeriod());
        }
    }

    @Override
    public void slowConsumer(ConnectionContext context, Subscription subs) {
    }

    @Override
    public void run() {
        if (this.maxTimeSinceLastAck < 0L) {
            LOG.info("no limit set, slowConsumer strategy has nothing to do");
            return;
        }
        if (this.getMaxSlowDuration() > 0L) {
            for (SlowConsumerEntry entry : this.slowConsumers.values()) {
                entry.mark();
            }
        }
        ArrayList<Destination> disposed = new ArrayList<Destination>();
        for (Destination destination : this.destinations.values()) {
            if (destination.isDisposed()) {
                disposed.add(destination);
                continue;
            }
            List<Subscription> subscribers = destination.getConsumers();
            this.updateSlowConsumersList(subscribers);
        }
        for (Destination destination : disposed) {
            this.destinations.remove(destination.getName());
        }
        this.abortAllQualifiedSlowConsumers();
    }

    private void updateSlowConsumersList(List<Subscription> subscribers) {
        for (Subscription subscriber : subscribers) {
            if (this.isIgnoreNetworkSubscriptions() && subscriber.getConsumerInfo().isNetworkSubscription()) {
                if (this.slowConsumers.remove(subscriber) == null) continue;
                LOG.info("network sub: {} is no longer slow", (Object)subscriber.getConsumerInfo().getConsumerId());
                continue;
            }
            if (this.isIgnoreIdleConsumers() && subscriber.getDispatchedQueueSize() == 0) {
                if (this.slowConsumers.remove(subscriber) == null) continue;
                LOG.info("idle sub: {} is no longer slow", (Object)subscriber.getConsumerInfo().getConsumerId());
                continue;
            }
            long lastAckTime = subscriber.getTimeOfLastMessageAck();
            long timeDelta = System.currentTimeMillis() - lastAckTime;
            if (timeDelta > this.maxTimeSinceLastAck) {
                if (!this.slowConsumers.containsKey(subscriber)) {
                    LOG.debug("sub: {} is now slow", (Object)subscriber.getConsumerInfo().getConsumerId());
                    SlowConsumerEntry entry = new SlowConsumerEntry(subscriber.getContext());
                    entry.mark();
                    this.slowConsumers.put(subscriber, entry);
                    continue;
                }
                if (this.getMaxSlowCount() <= 0L) continue;
                ((SlowConsumerEntry)this.slowConsumers.get(subscriber)).slow();
                continue;
            }
            if (this.slowConsumers.remove(subscriber) == null) continue;
            LOG.info("sub: {} is no longer slow", (Object)subscriber.getConsumerInfo().getConsumerId());
        }
    }

    private void abortAllQualifiedSlowConsumers() {
        HashMap<Subscription, SlowConsumerEntry> toAbort = new HashMap<Subscription, SlowConsumerEntry>();
        for (Map.Entry entry : this.slowConsumers.entrySet()) {
            if (this.getMaxSlowDuration() > 0L && (long)((SlowConsumerEntry)entry.getValue()).markCount * this.getCheckPeriod() >= this.getMaxSlowDuration() || this.getMaxSlowCount() > 0L && (long)((SlowConsumerEntry)entry.getValue()).slowCount >= this.getMaxSlowCount()) {
                LOG.trace("Transferring consumer {} to the abort list: {} slow duration = {}, slow count = {}", ((Subscription)entry.getKey()).getConsumerInfo().getConsumerId(), toAbort, (long)((SlowConsumerEntry)entry.getValue()).markCount * this.getCheckPeriod(), ((SlowConsumerEntry)entry.getValue()).getSlowCount());
                toAbort.put((Subscription)entry.getKey(), (SlowConsumerEntry)entry.getValue());
                this.slowConsumers.remove(entry.getKey());
                continue;
            }
            LOG.trace("Not yet time to abort consumer {}: slow duration = {}, slow count = {}", ((Subscription)entry.getKey()).getConsumerInfo().getConsumerId(), (long)((SlowConsumerEntry)entry.getValue()).markCount * this.getCheckPeriod(), ((SlowConsumerEntry)entry.getValue()).slowCount);
        }
        this.abortSubscription(toAbort, this.isAbortConnection());
    }

    @Override
    public void addDestination(Destination destination) {
        this.destinations.put(destination.getName(), destination);
    }

    public long getMaxTimeSinceLastAck() {
        return this.maxTimeSinceLastAck;
    }

    public void setMaxTimeSinceLastAck(long maxTimeSinceLastAck) {
        this.maxTimeSinceLastAck = maxTimeSinceLastAck;
    }

    public boolean isIgnoreIdleConsumers() {
        return this.ignoreIdleConsumers;
    }

    public void setIgnoreIdleConsumers(boolean ignoreIdleConsumers) {
        this.ignoreIdleConsumers = ignoreIdleConsumers;
    }
}

