/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.NetworkBridgeFilter;
import org.apache.activemq.command.SubscriptionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemandSubscription {
    private static final Logger LOG = LoggerFactory.getLogger(DemandSubscription.class);
    private final ConsumerInfo remoteInfo;
    private final ConsumerInfo localInfo;
    private final Set<ConsumerId> remoteSubsIds = new CopyOnWriteArraySet<ConsumerId>();
    private final AtomicInteger dispatched = new AtomicInteger(0);
    private final AtomicBoolean activeWaiter = new AtomicBoolean();
    private final Set<SubscriptionInfo> durableRemoteSubs = new CopyOnWriteArraySet<SubscriptionInfo>();
    private final Set<ConsumerId> forcedDurableConsumers = new CopyOnWriteArraySet<ConsumerId>();
    private SubscriptionInfo localDurableSubscriber;
    private NetworkBridgeFilter networkBridgeFilter;
    private boolean staticallyIncluded;

    DemandSubscription(ConsumerInfo info) {
        this.remoteInfo = info;
        this.localInfo = info.copy();
        this.localInfo.setNetworkSubscription(true);
        this.remoteSubsIds.add(info.getConsumerId());
    }

    public String toString() {
        return "DemandSub{" + this.localInfo.getConsumerId() + ",remotes:" + this.remoteSubsIds + "}";
    }

    public boolean add(ConsumerId id) {
        return this.remoteSubsIds.add(id);
    }

    public boolean remove(ConsumerId id) {
        return this.remoteSubsIds.remove(id);
    }

    public Set<SubscriptionInfo> getDurableRemoteSubs() {
        return this.durableRemoteSubs;
    }

    public boolean isEmpty() {
        return this.remoteSubsIds.isEmpty();
    }

    public int size() {
        return this.remoteSubsIds.size();
    }

    public ConsumerInfo getLocalInfo() {
        return this.localInfo;
    }

    public ConsumerInfo getRemoteInfo() {
        return this.remoteInfo;
    }

    public boolean addForcedDurableConsumer(ConsumerId id) {
        return this.forcedDurableConsumers.add(id);
    }

    public boolean removeForcedDurableConsumer(ConsumerId id) {
        return this.forcedDurableConsumers.remove(id);
    }

    public int getForcedDurableConsumersSize() {
        return this.forcedDurableConsumers.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void waitForCompletion() {
        if (this.dispatched.get() > 0) {
            LOG.debug("Waiting for completion for sub: {}, dispatched: {}", (Object)this.localInfo.getConsumerId(), (Object)this.dispatched.get());
            this.activeWaiter.set(true);
            if (this.dispatched.get() > 0) {
                AtomicBoolean atomicBoolean = this.activeWaiter;
                synchronized (atomicBoolean) {
                    try {
                        this.activeWaiter.wait(TimeUnit.SECONDS.toMillis(30L));
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                if (this.dispatched.get() > 0) {
                    LOG.warn("demand sub interrupted or timedout while waiting for outstanding responses, expect potentially {} duplicate forwards", (Object)this.dispatched.get());
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void decrementOutstandingResponses() {
        if (this.dispatched.decrementAndGet() == 0 && this.activeWaiter.get()) {
            AtomicBoolean atomicBoolean = this.activeWaiter;
            synchronized (atomicBoolean) {
                this.activeWaiter.notifyAll();
            }
        }
    }

    public boolean incrementOutstandingResponses() {
        this.dispatched.incrementAndGet();
        if (this.activeWaiter.get()) {
            this.decrementOutstandingResponses();
            return false;
        }
        return true;
    }

    public NetworkBridgeFilter getNetworkBridgeFilter() {
        return this.networkBridgeFilter;
    }

    public void setNetworkBridgeFilter(NetworkBridgeFilter networkBridgeFilter) {
        this.networkBridgeFilter = networkBridgeFilter;
    }

    public SubscriptionInfo getLocalDurableSubscriber() {
        return this.localDurableSubscriber;
    }

    public void setLocalDurableSubscriber(SubscriptionInfo localDurableSubscriber) {
        this.localDurableSubscriber = localDurableSubscriber;
    }

    public boolean isStaticallyIncluded() {
        return this.staticallyIncluded;
    }

    public void setStaticallyIncluded(boolean staticallyIncluded) {
        this.staticallyIncluded = staticallyIncluded;
    }
}

