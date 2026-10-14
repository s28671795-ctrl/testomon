/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.cursors.VMPendingMessageCursor;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempQueue
extends Queue {
    private static final Logger LOG = LoggerFactory.getLogger(TempQueue.class);
    private final ActiveMQTempDestination tempDest;

    public TempQueue(BrokerService brokerService, ActiveMQDestination destination, MessageStore store, DestinationStatistics parentStats, TaskRunnerFactory taskFactory) throws Exception {
        super(brokerService, destination, store, parentStats, taskFactory);
        this.tempDest = (ActiveMQTempDestination)destination;
    }

    @Override
    public void initialize() throws Exception {
        this.messages = new VMPendingMessageCursor(false);
        this.messages.setMemoryUsageHighWaterMark(this.getCursorMemoryHighWaterMark());
        this.systemUsage = this.brokerService.getSystemUsage();
        this.memoryUsage.setParent(this.systemUsage.getMemoryUsage());
        this.taskRunner = this.taskFactory.createTaskRunner(this, "TempQueue:  " + this.destination.getPhysicalName());
    }

    @Override
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        if (!(context.isFaultTolerant() || context.isNetworkConnection() || this.tempDest.getConnectionId().equals(sub.getConsumerInfo().getConsumerId().getConnectionId()))) {
            this.tempDest.setConnectionId(sub.getConsumerInfo().getConsumerId().getConnectionId());
            LOG.debug("changed ownership of {} to {}", (Object)this, (Object)this.tempDest.getConnectionId());
        }
        super.addSubscription(context, sub);
    }

    @Override
    public void dispose(ConnectionContext context) throws IOException {
        if (this.destinationStatistics.getMessages().getCount() > 0L) {
            LOG.info("{} on dispose, purge of {} pending messages: {}", this.getActiveMQDestination().getQualifiedName(), this.destinationStatistics.getMessages().getCount(), this.messages);
        }
        try {
            this.purge();
        }
        catch (Exception e) {
            LOG.warn("Caught an exception purging Queue: {}", (Object)this.destination, (Object)e);
        }
        super.dispose(context);
    }
}

