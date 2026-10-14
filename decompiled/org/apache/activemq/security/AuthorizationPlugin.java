/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.security.AuthorizationBroker;
import org.apache.activemq.security.AuthorizationMap;

public class AuthorizationPlugin
implements BrokerPlugin {
    private AuthorizationMap map;

    public AuthorizationPlugin() {
    }

    public AuthorizationPlugin(AuthorizationMap map) {
        this.map = map;
    }

    @Override
    public Broker installPlugin(Broker broker) {
        if (this.map == null) {
            throw new IllegalArgumentException("You must configure a 'map' property");
        }
        return new AuthorizationBroker(broker, this.map);
    }

    public AuthorizationMap getMap() {
        return this.map;
    }

    public void setMap(AuthorizationMap map) {
        this.map = map;
    }
}

