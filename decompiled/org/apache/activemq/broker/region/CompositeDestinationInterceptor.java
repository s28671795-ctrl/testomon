/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.command.ActiveMQDestination;

public class CompositeDestinationInterceptor
implements DestinationInterceptor {
    private volatile DestinationInterceptor[] interceptors;

    public CompositeDestinationInterceptor(DestinationInterceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public Destination intercept(Destination destination) {
        for (int i = 0; i < this.interceptors.length; ++i) {
            destination = this.interceptors[i].intercept(destination);
        }
        return destination;
    }

    @Override
    public void remove(Destination destination) {
        for (int i = 0; i < this.interceptors.length; ++i) {
            this.interceptors[i].remove(destination);
        }
    }

    @Override
    public void create(Broker broker, ConnectionContext context, ActiveMQDestination destination) throws Exception {
        for (int i = 0; i < this.interceptors.length; ++i) {
            this.interceptors[i].create(broker, context, destination);
        }
    }

    public void setInterceptors(DestinationInterceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    public DestinationInterceptor[] getInterceptors() {
        return this.interceptors;
    }
}

