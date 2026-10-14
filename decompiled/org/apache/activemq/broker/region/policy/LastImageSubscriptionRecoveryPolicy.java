/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.DestinationFilter;

public class LastImageSubscriptionRecoveryPolicy
implements SubscriptionRecoveryPolicy {
    private volatile MessageReference lastImage;

    @Override
    public boolean add(ConnectionContext context, MessageReference node) throws Exception {
        this.lastImage = node;
        return true;
    }

    @Override
    public void recover(ConnectionContext context, Topic topic, SubscriptionRecovery sub) throws Exception {
        MessageReference node = this.lastImage;
        if (node != null) {
            sub.addRecoveredMessage(context, node);
        }
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public Message[] browse(ActiveMQDestination destination) throws Exception {
        DestinationFilter filter2;
        ArrayList<Message> result = new ArrayList<Message>();
        if (this.lastImage != null && (filter2 = DestinationFilter.parseFilter(destination)).matches(this.lastImage.getMessage().getDestination())) {
            result.add(this.lastImage.getMessage());
        }
        return result.toArray(new Message[result.size()]);
    }

    @Override
    public SubscriptionRecoveryPolicy copy() {
        return new LastImageSubscriptionRecoveryPolicy();
    }

    @Override
    public void setBroker(Broker broker) {
    }
}

