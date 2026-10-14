/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.policy.SubscriptionRecoveryPolicy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;

public class NoSubscriptionRecoveryPolicy
implements SubscriptionRecoveryPolicy {
    @Override
    public SubscriptionRecoveryPolicy copy() {
        return this;
    }

    @Override
    public boolean add(ConnectionContext context, MessageReference node) throws Exception {
        return true;
    }

    @Override
    public void recover(ConnectionContext context, Topic topic, SubscriptionRecovery sub) throws Exception {
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public Message[] browse(ActiveMQDestination dest) throws Exception {
        return new Message[0];
    }

    @Override
    public void setBroker(Broker broker) {
    }
}

