/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.command.ActiveMQDestination;

public interface VirtualDestination
extends DestinationInterceptor {
    public ActiveMQDestination getVirtualDestination();

    @Override
    public Destination intercept(Destination var1);

    public ActiveMQDestination getMappedDestinations();

    public Destination interceptMappedDestination(Destination var1);
}

