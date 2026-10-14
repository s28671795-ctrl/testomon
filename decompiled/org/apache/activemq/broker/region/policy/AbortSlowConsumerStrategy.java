/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.SlowConsumerEntry;
import org.apache.activemq.broker.region.policy.SlowConsumerStrategy;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.state.CommandVisitor;
import org.apache.activemq.thread.Scheduler;
import org.apache.activemq.transport.InactivityIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbortSlowConsumerStrategy
implements SlowConsumerStrategy,
Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(AbortSlowConsumerStrategy.class);
    protected String name = "AbortSlowConsumerStrategy@" + this.hashCode();
    protected Scheduler scheduler;
    protected Broker broker;
    protected final AtomicBoolean taskStarted = new AtomicBoolean(false);
    protected final Map<Subscription, SlowConsumerEntry> slowConsumers = new ConcurrentHashMap<Subscription, SlowConsumerEntry>();
    private long maxSlowCount = -1L;
    private long maxSlowDuration = 30000L;
    private long checkPeriod = 30000L;
    private boolean abortConnection = false;
    private boolean ignoreNetworkConsumers = true;

    @Override
    public void setBrokerService(Broker broker) {
        this.scheduler = broker.getScheduler();
        this.broker = broker;
    }

    @Override
    public void slowConsumer(ConnectionContext context, Subscription subs) {
        if (this.maxSlowCount < 0L && this.maxSlowDuration < 0L) {
            LOG.info("no limits set, slowConsumer strategy has nothing to do");
            return;
        }
        if (this.taskStarted.compareAndSet(false, true)) {
            this.scheduler.executePeriodically(this, this.checkPeriod);
        }
        if (!this.slowConsumers.containsKey(subs)) {
            this.slowConsumers.put(subs, new SlowConsumerEntry(context));
        } else if (this.maxSlowCount > 0L) {
            this.slowConsumers.get(subs).slow();
        }
    }

    @Override
    public void run() {
        if (this.maxSlowDuration > 0L) {
            for (SlowConsumerEntry entry : this.slowConsumers.values()) {
                entry.mark();
            }
        }
        HashMap<Subscription, SlowConsumerEntry> toAbort = new HashMap<Subscription, SlowConsumerEntry>();
        for (Map.Entry<Subscription, SlowConsumerEntry> entry : this.slowConsumers.entrySet()) {
            Subscription subscription = entry.getKey();
            if (this.isIgnoreNetworkSubscriptions() && subscription.getConsumerInfo().isNetworkSubscription()) {
                if (this.slowConsumers.remove(subscription) == null) continue;
                LOG.info("network sub: {} is no longer slow", (Object)subscription.getConsumerInfo().getConsumerId());
                continue;
            }
            if (entry.getKey().isSlowConsumer()) {
                if ((this.maxSlowDuration <= 0L || (long)entry.getValue().markCount * this.checkPeriod < this.maxSlowDuration) && (this.maxSlowCount <= 0L || (long)entry.getValue().slowCount < this.maxSlowCount)) continue;
                toAbort.put(entry.getKey(), entry.getValue());
                this.slowConsumers.remove(entry.getKey());
                continue;
            }
            LOG.info("sub: " + entry.getKey().getConsumerInfo().getConsumerId() + " is no longer slow");
            this.slowConsumers.remove(entry.getKey());
        }
        this.abortSubscription(toAbort, this.abortConnection);
    }

    protected void abortSubscription(Map<Subscription, SlowConsumerEntry> toAbort, boolean abortSubscriberConnection) {
        HashMap abortMap = new HashMap();
        for (Map.Entry<Subscription, SlowConsumerEntry> entry : toAbort.entrySet()) {
            ConnectionContext connectionContext = entry.getValue().context;
            if (connectionContext == null) continue;
            Connection connection = connectionContext.getConnection();
            if (connection == null) {
                LOG.debug("slowConsumer abort ignored, no connection in context:" + connectionContext);
            }
            if (!abortMap.containsKey(connection)) {
                abortMap.put(connection, new ArrayList());
            }
            ((List)abortMap.get(connection)).add(entry.getKey());
        }
        for (Map.Entry<Subscription, SlowConsumerEntry> entry : abortMap.entrySet()) {
            final Connection connection = (Connection)((Object)entry.getKey());
            final List subscriptions = (List)((Object)entry.getValue());
            if (abortSubscriberConnection) {
                LOG.info("aborting connection:{} with {} slow consumers", (Object)connection.getConnectionId(), (Object)subscriptions.size());
                if (LOG.isTraceEnabled()) {
                    for (Subscription subscription : subscriptions) {
                        LOG.trace("Connection {} being aborted because of slow consumer: {} on destination: {}", connection.getConnectionId(), subscription.getConsumerInfo().getConsumerId(), subscription.getActiveMQDestination());
                    }
                }
                try {
                    this.scheduler.executeAfterDelay(new Runnable(){

                        @Override
                        public void run() {
                            connection.serviceException(new InactivityIOException(subscriptions.size() + " Consumers was slow too often (>" + AbortSlowConsumerStrategy.this.maxSlowCount + ") or too long (>" + AbortSlowConsumerStrategy.this.maxSlowDuration + "): "));
                        }
                    }, 0L);
                }
                catch (Exception e) {
                    LOG.info("exception on aborting connection {} with {} slow consumers", (Object)connection.getConnectionId(), (Object)subscriptions.size());
                }
                continue;
            }
            Iterator iterator2 = subscriptions.iterator();
            while (iterator2.hasNext()) {
                Subscription subscription;
                final Subscription subToClose = subscription = (Subscription)iterator2.next();
                LOG.info("aborting slow consumer: {} for destination:{}", (Object)subscription.getConsumerInfo().getConsumerId(), (Object)subscription.getActiveMQDestination());
                try {
                    ConsumerControl stopConsumer = new ConsumerControl();
                    stopConsumer.setConsumerId(subscription.getConsumerInfo().getConsumerId());
                    stopConsumer.setClose(true);
                    connection.dispatchAsync(stopConsumer);
                }
                catch (Exception e) {
                    LOG.info("exception on aborting slow consumer: {}", (Object)subscription.getConsumerInfo().getConsumerId(), (Object)e);
                }
                try {
                    this.scheduler.executeAfterDelay(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                RemoveInfo removeCommand = subToClose.getConsumerInfo().createRemoveCommand();
                                if (connection instanceof CommandVisitor) {
                                    removeCommand.visit((CommandVisitor)((Object)connection));
                                } else {
                                    connection.service(removeCommand);
                                }
                            }
                            catch (IllegalStateException removeCommand) {
                            }
                            catch (Exception e) {
                                LOG.info("exception on local remove of slow consumer: {}", (Object)subToClose.getConsumerInfo().getConsumerId(), (Object)e);
                            }
                        }
                    }, 1000L);
                }
                catch (Exception e) {
                    LOG.info("exception on local remove of slow consumer: {}", (Object)subscription.getConsumerInfo().getConsumerId(), (Object)e);
                }
            }
        }
    }

    public void abortConsumer(Subscription sub, boolean abortSubscriberConnection) {
        if (sub != null) {
            SlowConsumerEntry entry = this.slowConsumers.remove(sub);
            if (entry != null) {
                HashMap<Subscription, SlowConsumerEntry> toAbort = new HashMap<Subscription, SlowConsumerEntry>();
                toAbort.put(sub, entry);
                this.abortSubscription(toAbort, abortSubscriberConnection);
            } else {
                LOG.warn("cannot abort subscription as it no longer exists in the map of slow consumers: " + sub);
            }
        }
    }

    public long getMaxSlowCount() {
        return this.maxSlowCount;
    }

    public void setMaxSlowCount(long maxSlowCount) {
        this.maxSlowCount = maxSlowCount;
    }

    public long getMaxSlowDuration() {
        return this.maxSlowDuration;
    }

    public void setMaxSlowDuration(long maxSlowDuration) {
        this.maxSlowDuration = maxSlowDuration;
    }

    public long getCheckPeriod() {
        return this.checkPeriod;
    }

    public void setCheckPeriod(long checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    public boolean isAbortConnection() {
        return this.abortConnection;
    }

    public void setAbortConnection(boolean abortConnection) {
        this.abortConnection = abortConnection;
    }

    public boolean isIgnoreNetworkSubscriptions() {
        return this.ignoreNetworkConsumers;
    }

    public void setIgnoreNetworkConsumers(boolean ignoreNetworkConsumers) {
        this.ignoreNetworkConsumers = ignoreNetworkConsumers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Map<Subscription, SlowConsumerEntry> getSlowConsumers() {
        return this.slowConsumers;
    }

    @Override
    public void addDestination(Destination destination) {
    }
}

