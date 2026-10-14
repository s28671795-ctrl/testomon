/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.security.JaasAuthenticationPlugin;
import org.apache.activemq.security.JaasDualAuthenticationBroker;

public class JaasDualAuthenticationPlugin
extends JaasAuthenticationPlugin {
    private String sslConfiguration = "activemq-ssl-domain";

    @Override
    public Broker installPlugin(Broker broker) {
        this.initialiseJaas();
        return new JaasDualAuthenticationBroker(broker, this.configuration, this.sslConfiguration);
    }

    public void setSslConfiguration(String sslConfiguration) {
        this.sslConfiguration = sslConfiguration;
    }

    public String getSslConfiguration() {
        return this.sslConfiguration;
    }
}

