/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import javax.jms.JMSException;
import javax.jms.Topic;
import org.apache.activemq.command.ActiveMQDestination;

public class ActiveMQTopic
extends ActiveMQDestination
implements Topic {
    public static final byte DATA_STRUCTURE_TYPE = 101;
    private static final long serialVersionUID = 7300307405896488588L;

    public ActiveMQTopic() {
    }

    public ActiveMQTopic(String name) {
        super(name);
    }

    @Override
    public byte getDataStructureType() {
        return 101;
    }

    @Override
    public boolean isTopic() {
        return true;
    }

    @Override
    public String getTopicName() throws JMSException {
        return this.getPhysicalName();
    }

    @Override
    public byte getDestinationType() {
        return 2;
    }

    @Override
    protected String getQualifiedPrefix() {
        return "topic://";
    }
}

