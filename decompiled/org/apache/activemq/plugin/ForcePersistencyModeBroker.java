/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.plugin;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForcePersistencyModeBroker
extends BrokerFilter {
    public static Logger log = LoggerFactory.getLogger(ForcePersistencyModeBroker.class);
    private boolean persistence = false;

    public boolean isPersistent() {
        return this.persistence;
    }

    public void setPersistenceFlag(boolean mode) {
        this.persistence = mode;
    }

    public ForcePersistencyModeBroker(Broker next) {
        super(next);
        System.out.println(this.getBrokerSequenceId());
    }

    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        messageSend.getMessage().setPersistent(this.isPersistent());
        this.next.send(producerExchange, messageSend);
    }
}

