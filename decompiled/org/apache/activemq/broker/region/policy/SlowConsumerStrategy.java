/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;

public interface SlowConsumerStrategy {
    public void slowConsumer(ConnectionContext var1, Subscription var2);

    public void setBrokerService(Broker var1);

    public void addDestination(Destination var1);
}

