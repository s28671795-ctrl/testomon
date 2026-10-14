/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.util.Iterator;
import java.util.Set;
import javax.jms.JMSException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.AbstractRegion;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.QueueBrowserSubscription;
import org.apache.activemq.broker.region.QueueSubscription;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.SystemUsage;

public class QueueRegion
extends AbstractRegion {
    public QueueRegion(RegionBroker broker, DestinationStatistics destinationStatistics, SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        super(broker, destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    public String toString() {
        return "QueueRegion: destinations=" + this.destinations.size() + ", subscriptions=" + this.subscriptions.size() + ", memory=" + this.usageManager.getMemoryUsage().getPercentUsage() + "%";
    }

    @Override
    protected Subscription createSubscription(ConnectionContext context, ConsumerInfo info) throws JMSException {
        ActiveMQDestination destination = info.getDestination();
        PolicyEntry entry = null;
        if (destination != null && this.broker.getDestinationPolicy() != null) {
            entry = this.broker.getDestinationPolicy().getEntryFor(destination);
        }
        if (info.isBrowser()) {
            QueueBrowserSubscription sub = new QueueBrowserSubscription(this.broker, this.usageManager, context, info);
            if (entry != null) {
                entry.configure((Broker)this.broker, this.usageManager, sub);
            }
            return sub;
        }
        QueueSubscription sub = new QueueSubscription(this.broker, this.usageManager, context, info);
        if (entry != null) {
            entry.configure((Broker)this.broker, this.usageManager, sub);
        }
        return sub;
    }

    @Override
    protected Set<ActiveMQDestination> getInactiveDestinations() {
        Set<ActiveMQDestination> inactiveDestinations = super.getInactiveDestinations();
        Iterator<ActiveMQDestination> iter = inactiveDestinations.iterator();
        while (iter.hasNext()) {
            ActiveMQDestination dest = iter.next();
            if (dest.isQueue()) continue;
            iter.remove();
        }
        return inactiveDestinations;
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        this.processDispatchNotificationViaDestination(messageDispatchNotification);
    }
}

