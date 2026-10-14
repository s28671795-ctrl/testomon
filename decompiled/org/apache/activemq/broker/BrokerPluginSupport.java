/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.MutableBrokerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BrokerPluginSupport
extends MutableBrokerFilter
implements BrokerPlugin {
    private static final Logger LOG = LoggerFactory.getLogger(BrokerPluginSupport.class);

    public BrokerPluginSupport() {
        super(null);
    }

    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        this.setNext(broker);
        return this;
    }

    @Override
    public void start() throws Exception {
        super.start();
        LOG.info("Broker Plugin {} started", (Object)this.getClass().getName());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        LOG.info("Broker Plugin {} stopped", (Object)this.getClass().getName());
    }
}

