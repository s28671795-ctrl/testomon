/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import javax.jms.JMSException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.AbstractTempRegion;
import org.apache.activemq.broker.region.DestinationFactory;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.TopicSubscription;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.usage.SystemUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempTopicRegion
extends AbstractTempRegion {
    private static final Logger LOG = LoggerFactory.getLogger(TempTopicRegion.class);

    public TempTopicRegion(RegionBroker broker, DestinationStatistics destinationStatistics, SystemUsage memoryManager, TaskRunnerFactory taskRunnerFactory, DestinationFactory destinationFactory) {
        super(broker, destinationStatistics, memoryManager, taskRunnerFactory, destinationFactory);
    }

    @Override
    protected Subscription createSubscription(ConnectionContext context, ConsumerInfo info) throws JMSException {
        if (info.isDurable()) {
            throw new JMSException("A durable subscription cannot be created for a temporary topic.");
        }
        try {
            PolicyEntry entry;
            TopicSubscription answer = new TopicSubscription(this.broker, context, info, this.usageManager);
            ActiveMQDestination destination = info.getDestination();
            if (destination != null && this.broker.getDestinationPolicy() != null && (entry = this.broker.getDestinationPolicy().getEntryFor(destination)) != null) {
                entry.configure((Broker)this.broker, this.usageManager, answer);
            }
            answer.init();
            return answer;
        }
        catch (Exception e) {
            LOG.error("Failed to create TopicSubscription ", e);
            JMSException jmsEx = new JMSException("Couldn't create TopicSubscription");
            jmsEx.setLinkedException(e);
            throw jmsEx;
        }
    }

    public String toString() {
        return "TempTopicRegion: destinations=" + this.destinations.size() + ", subscriptions=" + this.subscriptions.size() + ", memory=" + this.usageManager.getMemoryUsage().getPercentUsage() + "%";
    }

    @Override
    public void removeDestination(ConnectionContext context, ActiveMQDestination destination, long timeout2) throws Exception {
        if (timeout2 == 0L) {
            timeout2 = 1L;
        }
        super.removeDestination(context, destination, timeout2);
    }
}

