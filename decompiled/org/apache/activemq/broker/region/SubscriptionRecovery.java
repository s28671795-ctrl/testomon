/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.ActiveMQDestination;

public interface SubscriptionRecovery {
    public boolean addRecoveredMessage(ConnectionContext var1, MessageReference var2) throws Exception;

    public ActiveMQDestination getActiveMQDestination();
}

