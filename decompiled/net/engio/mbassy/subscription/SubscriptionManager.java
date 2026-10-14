/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.subscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.engio.mbassy.bus.BusRuntime;
import net.engio.mbassy.common.ReflectionUtils;
import net.engio.mbassy.common.StrongConcurrentSet;
import net.engio.mbassy.listener.MessageHandler;
import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.subscription.Subscription;
import net.engio.mbassy.subscription.SubscriptionFactory;

public class SubscriptionManager {
    private final MetadataReader metadataReader;
    private final Map<Class, ArrayList<Subscription>> subscriptionsPerMessage;
    private final Map<Class, Subscription[]> subscriptionsPerListener;
    private final StrongConcurrentSet<Class> nonListeners = new StrongConcurrentSet();
    private final SubscriptionFactory subscriptionFactory;
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final BusRuntime runtime;

    public SubscriptionManager(MetadataReader metadataReader, SubscriptionFactory subscriptionFactory, BusRuntime runtime) {
        this.metadataReader = metadataReader;
        this.subscriptionFactory = subscriptionFactory;
        this.runtime = runtime;
        this.subscriptionsPerMessage = new HashMap<Class, ArrayList<Subscription>>(256);
        this.subscriptionsPerListener = new HashMap<Class, Subscription[]>(256);
    }

    public boolean unsubscribe(Object listener) {
        if (listener == null) {
            return false;
        }
        Subscription[] subscriptions = this.getSubscriptionsByListener(listener);
        if (subscriptions == null) {
            return false;
        }
        boolean isRemoved = true;
        for (Subscription subscription : subscriptions) {
            isRemoved &= subscription.unsubscribe(listener);
        }
        return isRemoved;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Subscription[] getSubscriptionsByListener(Object listener) {
        Subscription[] subscriptions;
        ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
        try {
            readLock.lock();
            subscriptions = this.subscriptionsPerListener.get(listener.getClass());
        }
        finally {
            readLock.unlock();
        }
        return subscriptions;
    }

    public void subscribe(Object listener) {
        try {
            Class<?> listenerClass = listener.getClass();
            if (this.nonListeners.contains(listenerClass)) {
                return;
            }
            Subscription[] subscriptionsByListener = this.getSubscriptionsByListener(listener);
            if (subscriptionsByListener == null) {
                MessageHandler[] messageHandlers = this.metadataReader.getMessageListener(listenerClass).getHandlers();
                int length = messageHandlers.length;
                if (length == 0) {
                    this.nonListeners.add(listenerClass);
                    return;
                }
                subscriptionsByListener = new Subscription[length];
                for (int i = 0; i < length; ++i) {
                    MessageHandler messageHandler = messageHandlers[i];
                    subscriptionsByListener[i] = this.subscriptionFactory.createSubscription(this.runtime, messageHandler);
                }
                this.subscribe(listener, subscriptionsByListener);
            } else {
                for (Subscription sub : subscriptionsByListener) {
                    sub.subscribe(listener);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void subscribe(Object listener, Subscription[] subscriptions) {
        ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
        try {
            writeLock.lock();
            Subscription[] subscriptionsByListener = this.getSubscriptionsByListener(listener);
            if (subscriptionsByListener == null) {
                int n = subscriptions.length;
                for (int i = 0; i < n; ++i) {
                    Subscription subscription = subscriptions[i];
                    subscription.subscribe(listener);
                    for (Class messageType : subscription.getHandledMessageTypes()) {
                        ArrayList<Subscription> subscriptions2 = this.subscriptionsPerMessage.get(messageType);
                        if (subscriptions2 == null) {
                            subscriptions2 = new ArrayList(8);
                            this.subscriptionsPerMessage.put(messageType, subscriptions2);
                        }
                        subscriptions2.add(subscription);
                    }
                }
                this.subscriptionsPerListener.put(listener.getClass(), subscriptions);
            } else {
                for (Subscription existingSubscription : subscriptionsByListener) {
                    existingSubscription.subscribe(listener);
                }
            }
        }
        finally {
            writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Collection<Subscription> getSubscriptionsByMessageType(Class messageType) {
        TreeSet<Subscription> subscriptions = new TreeSet<Subscription>(Subscription.SubscriptionByPriorityDesc);
        ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
        try {
            readLock.lock();
            ArrayList<Subscription> subsPerMessage = this.subscriptionsPerMessage.get(messageType);
            if (subsPerMessage != null) {
                subscriptions.addAll(subsPerMessage);
            }
            for (Class eventSuperType : ReflectionUtils.getSuperTypes(messageType)) {
                ArrayList<Subscription> subs = this.subscriptionsPerMessage.get(eventSuperType);
                if (subs == null) continue;
                int m4 = subs.size();
                for (int j = 0; j < m4; ++j) {
                    Subscription subscription = subs.get(j);
                    if (!subscription.handlesMessageType(messageType)) continue;
                    subscriptions.add(subscription);
                }
            }
        }
        finally {
            readLock.unlock();
        }
        return subscriptions;
    }
}

