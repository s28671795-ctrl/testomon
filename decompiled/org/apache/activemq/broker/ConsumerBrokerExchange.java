/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Region;
import org.apache.activemq.broker.region.Subscription;

public class ConsumerBrokerExchange {
    private ConnectionContext connectionContext;
    private Destination regionDestination;
    private Region region;
    private Subscription subscription;
    private boolean wildcard;

    public ConnectionContext getConnectionContext() {
        return this.connectionContext;
    }

    public void setConnectionContext(ConnectionContext connectionContext) {
        this.connectionContext = connectionContext;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Destination getRegionDestination() {
        return this.regionDestination;
    }

    public void setRegionDestination(Destination regionDestination) {
        this.regionDestination = regionDestination;
    }

    public Subscription getSubscription() {
        return this.subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public boolean isWildcard() {
        return this.wildcard;
    }

    public void setWildcard(boolean wildcard) {
        this.wildcard = wildcard;
    }
}

