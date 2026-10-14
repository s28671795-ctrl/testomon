/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.command.ActiveMQDestination;

public interface DestinationInterceptor {
    public Destination intercept(Destination var1);

    public void remove(Destination var1);

    public void create(Broker var1, ConnectionContext var2, ActiveMQDestination var3) throws Exception;
}

