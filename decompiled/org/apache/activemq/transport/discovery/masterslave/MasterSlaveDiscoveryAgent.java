/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery.masterslave;

import java.net.URI;
import org.apache.activemq.transport.discovery.simple.SimpleDiscoveryAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MasterSlaveDiscoveryAgent
extends SimpleDiscoveryAgent {
    private static final Logger LOG = LoggerFactory.getLogger(MasterSlaveDiscoveryAgent.class);
    private String[] msServices = new String[0];

    @Override
    public String[] getServices() {
        return this.msServices;
    }

    @Override
    public void setServices(String services) {
        this.msServices = services.split(",");
        this.configureServices();
    }

    @Override
    public void setServices(String[] services) {
        this.msServices = services;
        this.configureServices();
    }

    @Override
    public void setServices(URI[] services) {
        this.msServices = new String[services.length];
        for (int i = 0; i < services.length; ++i) {
            this.msServices[i] = services[i].toString();
        }
        this.configureServices();
    }

    protected void configureServices() {
        if (this.msServices == null || this.msServices.length < 2) {
            LOG.error("masterSlave requires at least 2 URIs");
            this.msServices = new String[0];
            throw new IllegalArgumentException("Expecting at least 2 arguments");
        }
        StringBuffer buf = new StringBuffer();
        buf.append("failover:(");
        for (int i = 0; i < this.msServices.length - 1; ++i) {
            buf.append(this.msServices[i]);
            buf.append(',');
        }
        buf.append(this.msServices[this.msServices.length - 1]);
        buf.append(")?randomize=false&maxReconnectAttempts=0");
        super.setServices(new String[]{buf.toString()});
    }
}

