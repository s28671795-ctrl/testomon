/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.net.URL;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.security.JaasAuthenticationBroker;

public class JaasAuthenticationPlugin
implements BrokerPlugin {
    protected String configuration = "activemq-domain";
    protected boolean discoverLoginConfig = true;

    @Override
    public Broker installPlugin(Broker broker) {
        this.initialiseJaas();
        return new JaasAuthenticationBroker(broker, this.configuration);
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(String jaasConfiguration) {
        this.configuration = jaasConfiguration;
    }

    public boolean isDiscoverLoginConfig() {
        return this.discoverLoginConfig;
    }

    public void setDiscoverLoginConfig(boolean discoverLoginConfig) {
        this.discoverLoginConfig = discoverLoginConfig;
    }

    protected void initialiseJaas() {
        String path;
        if (this.discoverLoginConfig && (path = System.getProperty("java.security.auth.login.config")) == null) {
            URL resource = null;
            if (resource == null) {
                resource = this.getClass().getClassLoader().getResource("login.config");
            }
            if (resource != null) {
                path = resource.getFile();
                System.setProperty("java.security.auth.login.config", path);
            }
        }
    }
}

