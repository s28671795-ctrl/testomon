/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.Service;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.SubscriptionRecovery;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;

public interface SubscriptionRecoveryPolicy
extends Service {
    public boolean add(ConnectionContext var1, MessageReference var2) throws Exception;

    public void recover(ConnectionContext var1, Topic var2, SubscriptionRecovery var3) throws Exception;

    public Message[] browse(ActiveMQDestination var1) throws Exception;

    public SubscriptionRecoveryPolicy copy();

    public void setBroker(Broker var1);
}

