/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import javax.jms.JMSException;
import javax.management.ObjectName;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.TopicRegion;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.SystemUsage;

public class ManagedTopicRegion
extends TopicRegion {
    private final ManagedRegionBroker regionBroker;

    public ManagedTopicRegion(ManagedRegionBroker broker, DestinationStatistics destinationStatistics, SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        super(broker, destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
        this.regionBroker = broker;
    }

    @Override
    protected Subscription createSubscription(ConnectionContext context, ConsumerInfo info) throws JMSException {
        Subscription sub = super.createSubscription(context, info);
        ObjectName name = this.regionBroker.registerSubscription(context, sub);
        sub.setObjectName(name);
        return sub;
    }

    @Override
    protected void destroySubscription(Subscription sub) {
        this.regionBroker.unregisterSubscription(sub);
        super.destroySubscription(sub);
    }

    @Override
    protected Destination createDestination(ConnectionContext context, ActiveMQDestination destination) throws Exception {
        Destination rc = super.createDestination(context, destination);
        this.regionBroker.register(destination, rc);
        return rc;
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        super.removeDestination(context, destination, timeout2);
        this.regionBroker.unregister(destination);
    }
}

