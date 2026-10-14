/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.security.JaasAuthenticationPlugin;
import org.apache.activemq.security.JaasCertificateAuthenticationBroker;

public class JaasCertificateAuthenticationPlugin
extends JaasAuthenticationPlugin {
    @Override
    public Broker installPlugin(Broker broker) {
        this.initialiseJaas();
        return new JaasCertificateAuthenticationBroker(broker, this.configuration);
    }
}

