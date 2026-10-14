/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import java.io.IOException;
import java.util.Set;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.broker.region.RegionBroker;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.SubscriptionInfo;

public abstract class DestinationFactory {
    public abstract Destination createDestination(ConnectionContext var1, ActiveMQDestination var2, DestinationStatistics var3) throws Exception;

    public abstract void removeDestination(Destination var1);

    public abstract Set<ActiveMQDestination> getDestinations();

    public abstract SubscriptionInfo[] getAllDurableSubscriptions(ActiveMQTopic var1) throws IOException;

    public abstract long getLastMessageBrokerSequenceId() throws IOException;

    public abstract void setRegionBroker(RegionBroker var1);
}

