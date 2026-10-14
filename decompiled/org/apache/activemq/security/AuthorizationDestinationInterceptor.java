/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationInterceptor;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.security.AuthorizationBroker;
import org.apache.activemq.security.AuthorizationDestinationFilter;

public class AuthorizationDestinationInterceptor
implements DestinationInterceptor {
    private final AuthorizationBroker broker;

    public AuthorizationDestinationInterceptor(AuthorizationBroker broker) {
        this.broker = broker;
    }

    @Override
    public Destination intercept(Destination destination) {
        return new AuthorizationDestinationFilter(destination, this.broker);
    }

    @Override
    public void remove(Destination destination) {
    }

    @Override
    public void create(Broker broker, ConnectionContext context, ActiveMQDestination destination) throws Exception {
    }
}

