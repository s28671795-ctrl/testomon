/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.store.TopicMessageStore;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunnerFactory;

public class TempTopic
extends Topic
implements Task {
    private final ActiveMQTempDestination tempDest;

    public TempTopic(BrokerService brokerService, ActiveMQDestination destination, TopicMessageStore store, DestinationStatistics parentStats, TaskRunnerFactory taskFactory) throws Exception {
        super(brokerService, destination, store, parentStats, taskFactory);
        this.tempDest = (ActiveMQTempDestination)destination;
    }

    @Override
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        if (!(context.isFaultTolerant() || context.isNetworkConnection() || this.tempDest.getConnectionId().equals(sub.getConsumerInfo().getConsumerId().getConnectionId()))) {
            this.tempDest.setConnectionId(sub.getConsumerInfo().getConsumerId().getConnectionId());
            if (LOG.isDebugEnabled()) {
                LOG.debug(" changed ownership of " + this + " to " + this.tempDest.getConnectionId());
            }
        }
        super.addSubscription(context, sub);
    }

    @Override
    public void initialize() {
    }
}

