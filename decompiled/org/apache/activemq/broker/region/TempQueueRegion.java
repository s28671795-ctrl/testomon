/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import javax.jms.JMSException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.AbstractTempRegion;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.QueueBrowserSubscription;
import org.apache.activemq.broker.region.QueueSubscription;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageDispatchNotification;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.SystemUsage;

public class TempQueueRegion
extends AbstractTempRegion {
    public TempQueueRegion(RegionBroker broker, DestinationStatistics destinationStatistics, SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        super(broker, destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    @Override
    protected Subscription createSubscription(ConnectionContext context, ConsumerInfo info) throws JMSException {
        if (info.isBrowser()) {
            return new QueueBrowserSubscription(this.broker, this.usageManager, context, info);
        }
        return new QueueSubscription(this.broker, this.usageManager, context, info);
    }

    public String toString() {
        return "TempQueueRegion: destinations=" + this.destinations.size() + ", subscriptions=" + this.subscriptions.size() + ", memory=" + this.usageManager.getMemoryUsage().getPercentUsage() + "%";
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        if (timeout2 == 0L) {
            timeout2 = 1L;
        }
        super.removeDestination(context, destination, timeout2);
    }

    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        this.processDispatchNotificationViaDestination(messageDispatchNotification);
    }
}

