/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import javax.jms.JMSException;
import javax.jms.TemporaryTopic;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ConnectionId;

public class ActiveMQTempTopic
extends ActiveMQTempDestination
implements TemporaryTopic {
    public static final byte DATA_STRUCTURE_TYPE = 103;
    private static final long serialVersionUID = -4325596784597300253L;

    public ActiveMQTempTopic() {
    }

    public ActiveMQTempTopic(String name) {
        super(name);
    }

    public ActiveMQTempTopic(ConnectionId connectionId, long sequenceId) {
        super(connectionId.getValue(), sequenceId);
    }

    @Override
    public byte getDataStructureType() {
        return 103;
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
        return 6;
    }

    @Override
    protected String getQualifiedPrefix() {
        return "temp-topic://";
    }
}

