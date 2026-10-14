/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFilter;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.virtual.VirtualTopic;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.LocalTransactionId;
import org.apache.activemq.command.Message;
import org.apache.activemq.transaction.Transaction;
import org.apache.activemq.util.LRUCache;

public class VirtualTopicInterceptor
extends DestinationFilter {
    private final String prefix;
    private final String postfix;
    private final boolean local;
    private final boolean concurrentSend;
    private final boolean transactedSend;
    private final LRUCache<ActiveMQDestination, ActiveMQQueue> cache = new LRUCache();

    public VirtualTopicInterceptor(Destination next, VirtualTopic virtualTopic) {
        super(next);
        this.prefix = virtualTopic.getPrefix();
        this.postfix = virtualTopic.getPostfix();
        this.local = virtualTopic.isLocal();
        this.concurrentSend = virtualTopic.isConcurrentSend();
        this.transactedSend = virtualTopic.isTransactedSend();
    }

    public Topic getTopic() {
        return (Topic)this.next;
    }

    @Override
    public void send(ProducerBrokerExchange context, Message message) throws Exception {
        if (!(message.isAdvisory() || this.local && message.getBrokerPath() != null)) {
            ActiveMQDestination queueConsumers = this.getQueueConsumersWildcard(message.getDestination());
            this.send(context, message, queueConsumers);
        }
        super.send(context, message);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void send(final ProducerBrokerExchange context, final Message message, ActiveMQDestination destination) throws Exception {
        Broker broker = context.getConnectionContext().getBroker();
        Set<Destination> destinations = broker.getDestinations(destination);
        int numDestinations = destinations.size();
        LocalTransactionId localBrokerTransactionToCoalesceJournalSync = this.beginLocalTransaction(numDestinations, context.getConnectionContext(), message);
        try {
            if (this.concurrentSend && numDestinations > 1) {
                final CountDownLatch concurrent = new CountDownLatch(destinations.size());
                final AtomicReference exceptionAtomicReference = new AtomicReference();
                BrokerService brokerService = broker.getBrokerService();
                for (final Destination dest : destinations) {
                    if (this.shouldDispatch(broker, message, dest)) {
                        brokerService.getTaskRunnerFactory().execute(new Runnable(){

                            @Override
                            public void run() {
                                try {
                                    if (exceptionAtomicReference.get() == null) {
                                        dest.send(context, VirtualTopicInterceptor.this.copy(message, dest.getActiveMQDestination()));
                                    }
                                }
                                catch (Exception e) {
                                    exceptionAtomicReference.set(e);
                                }
                                finally {
                                    concurrent.countDown();
                                }
                            }
                        });
                        continue;
                    }
                    concurrent.countDown();
                }
                concurrent.await();
                if (exceptionAtomicReference.get() != null) {
                    throw (Exception)exceptionAtomicReference.get();
                }
            } else {
                for (Destination dest : destinations) {
                    if (!this.shouldDispatch(broker, message, dest)) continue;
                    dest.send(context, this.copy(message, dest.getActiveMQDestination()));
                }
            }
        }
        finally {
            this.commit(localBrokerTransactionToCoalesceJournalSync, context.getConnectionContext(), message);
        }
    }

    private Message copy(Message original, ActiveMQDestination target) {
        Message msg = original.copy();
        msg.setDestination(target);
        msg.setOriginalDestination(original.getDestination());
        return msg;
    }

    private LocalTransactionId beginLocalTransaction(int numDestinations, ConnectionContext connectionContext, Message message) throws Exception {
        LocalTransactionId result = null;
        if (this.transactedSend && numDestinations > 1 && message.isPersistent() && message.getTransactionId() == null) {
            result = new LocalTransactionId(new ConnectionId(message.getMessageId().getProducerId().toString()), message.getMessageId().getProducerSequenceId());
            connectionContext.getBroker().beginTransaction(connectionContext, result);
            connectionContext.setTransaction((Transaction)connectionContext.getTransactions().get(result));
            message.setTransactionId(result);
        }
        return result;
    }

    private void commit(LocalTransactionId tx, ConnectionContext connectionContext, Message message) throws Exception {
        if (tx != null) {
            connectionContext.getBroker().commitTransaction(connectionContext, tx, true);
            connectionContext.getTransactions().remove(tx);
            connectionContext.setTransaction(null);
            message.setTransactionId(null);
        }
    }

    protected boolean shouldDispatch(Broker broker, Message message, Destination dest) throws IOException {
        return this.prefix.contains(".*") && !this.prefix.startsWith("*") ? dest.getName().startsWith(this.prefix.substring(0, this.prefix.indexOf(".*"))) : true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected ActiveMQDestination getQueueConsumersWildcard(ActiveMQDestination original) {
        ActiveMQQueue queue;
        LRUCache<ActiveMQDestination, ActiveMQQueue> lRUCache = this.cache;
        synchronized (lRUCache) {
            queue = (ActiveMQQueue)this.cache.get(original);
            if (queue == null) {
                queue = new ActiveMQQueue(this.prefix + original.getPhysicalName() + this.postfix);
                this.cache.put(original, queue);
            }
        }
        return queue;
    }
}

