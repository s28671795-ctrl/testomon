/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import java.util.Set;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.BaseDestination;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationFilter;
import org.apache.activemq.broker.region.IndirectMessageReference;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.util.SubscriptionKey;

public class MappedQueueFilter
extends DestinationFilter {
    private final ActiveMQDestination virtualDestination;

    public MappedQueueFilter(ActiveMQDestination virtualDestination, Destination destination) {
        super(destination);
        this.virtualDestination = virtualDestination;
    }

    @Override
    public synchronized void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        boolean noSubs = this.getConsumers().isEmpty();
        int match = sub.getActiveMQDestination().compareTo(this.next.getActiveMQDestination());
        if (match == 0 || !this.next.getActiveMQDestination().isPattern() && match == 1) {
            super.addSubscription(context, sub);
        }
        if (noSubs && !this.getConsumers().isEmpty()) {
            RegionBroker regionBroker = (RegionBroker)context.getBroker().getAdaptor(RegionBroker.class);
            Set<Destination> virtualDests = regionBroker.getDestinations(this.virtualDestination);
            ActiveMQDestination newDestination = sub.getActiveMQDestination();
            BaseDestination regionDest = null;
            for (Destination virtualDest : virtualDests) {
                Message[] messages;
                Topic topic;
                if (!virtualDest.getActiveMQDestination().isTopic() || !virtualDest.isAlwaysRetroactive() && !sub.getConsumerInfo().isRetroactive() || (topic = (Topic)this.getBaseDestination(virtualDest)) == null) continue;
                for (Message message : messages = topic.getSubscriptionRecoveryPolicy().browse(topic.getActiveMQDestination())) {
                    Message copy = message.copy();
                    copy.setOriginalDestination(message.getDestination());
                    copy.setDestination(newDestination);
                    if (regionDest == null) {
                        regionDest = this.getBaseDestination((Destination)regionBroker.getDestinations(newDestination).toArray()[0]);
                    }
                    copy.setRegionDestination(regionDest);
                    sub.addRecoveredMessage(context, newDestination.isQueue() ? new IndirectMessageReference(copy) : copy);
                }
            }
        }
    }

    private BaseDestination getBaseDestination(Destination virtualDest) {
        if (virtualDest instanceof BaseDestination) {
            return (BaseDestination)virtualDest;
        }
        if (virtualDest instanceof DestinationFilter) {
            return ((DestinationFilter)virtualDest).getAdaptor(BaseDestination.class);
        }
        return null;
    }

    @Override
    public synchronized void removeSubscription(ConnectionContext context, Subscription sub, long lastDeliveredSequenceId) throws Exception {
        super.removeSubscription(context, sub, lastDeliveredSequenceId);
    }

    @Override
    public synchronized void deleteSubscription(ConnectionContext context, SubscriptionKey key) throws Exception {
        super.deleteSubscription(context, key);
    }

    public String toString() {
        return "MappedQueueFilter[" + this.virtualDestination + ", " + this.next + "]";
    }
}

