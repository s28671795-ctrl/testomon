/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import org.apache.activemq.advisory.VirtualDestinationMatcher;
import org.apache.activemq.broker.region.virtual.CompositeDestination;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.broker.region.virtual.VirtualTopic;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.filter.DestinationFilter;

public class DestinationFilterVirtualDestinationMatcher
implements VirtualDestinationMatcher {
    @Override
    public boolean matches(VirtualDestination virtualDestination, ActiveMQDestination activeMQDest) {
        DestinationFilter filter2;
        return virtualDestination instanceof CompositeDestination ? (filter2 = DestinationFilter.parseFilter(virtualDestination.getMappedDestinations())).matches(activeMQDest) : virtualDestination instanceof VirtualTopic && (filter = DestinationFilter.parseFilter(new ActiveMQQueue(((VirtualTopic)virtualDestination).getPrefix() + ">"))).matches(activeMQDest);
    }
}

