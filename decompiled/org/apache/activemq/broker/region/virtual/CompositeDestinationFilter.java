/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFilter;
import org.apache.activemq.broker.region.virtual.FilteredDestination;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;

public class CompositeDestinationFilter
extends DestinationFilter {
    private Collection forwardDestinations;
    private boolean forwardOnly;
    private boolean concurrentSend = false;

    public CompositeDestinationFilter(Destination next, Collection forwardDestinations, boolean forwardOnly, boolean concurrentSend) {
        super(next);
        this.forwardDestinations = forwardDestinations;
        this.forwardOnly = forwardOnly;
        this.concurrentSend = concurrentSend;
    }

    @Override
    public void send(final ProducerBrokerExchange context, final Message message) throws Exception {
        NonCachedMessageEvaluationContext messageContext = null;
        LinkedList<ActiveMQDestination> matchingDestinations = new LinkedList<ActiveMQDestination>();
        Iterator iter = this.forwardDestinations.iterator();
        while (iter.hasNext()) {
            ActiveMQDestination destination = null;
            Object value = iter.next();
            if (value instanceof FilteredDestination) {
                FilteredDestination filteredDestination = (FilteredDestination)value;
                if (messageContext == null) {
                    messageContext = new NonCachedMessageEvaluationContext();
                    ((MessageEvaluationContext)messageContext).setMessageReference(message);
                }
                messageContext.setDestination(filteredDestination.getDestination());
                if (filteredDestination.matches(messageContext)) {
                    destination = filteredDestination.getDestination();
                }
            } else if (value instanceof ActiveMQDestination) {
                destination = (ActiveMQDestination)value;
            }
            if (destination == null) continue;
            matchingDestinations.add(destination);
        }
        final CountDownLatch concurrent = new CountDownLatch(this.concurrentSend ? matchingDestinations.size() : 0);
        final AtomicReference exceptionAtomicReference = new AtomicReference();
        final BrokerService brokerService = context.getConnectionContext().getBroker().getBrokerService();
        for (final ActiveMQDestination destination : matchingDestinations) {
            if (concurrent.getCount() > 0L) {
                brokerService.getTaskRunnerFactory().execute(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            if (exceptionAtomicReference.get() == null) {
                                CompositeDestinationFilter.this.doForward(context.copy(), message, brokerService.getRegionBroker(), destination);
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
            this.doForward(context, message, brokerService.getRegionBroker(), destination);
        }
        if (!this.forwardOnly) {
            super.send(context, message);
        }
        concurrent.await();
        if (exceptionAtomicReference.get() != null) {
            throw (Exception)exceptionAtomicReference.get();
        }
    }

    private void doForward(ProducerBrokerExchange context, Message message, Broker regionBroker, ActiveMQDestination destination) throws Exception {
        Message forwardedMessage = message.copy();
        forwardedMessage.setMemoryUsage(null);
        forwardedMessage.setOriginalDestination(message.getDestination());
        forwardedMessage.setDestination(destination);
        context.setMutable(true);
        regionBroker.send(context, forwardedMessage);
    }
}

