/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import javax.jms.JMSException;
import javax.jms.TemporaryQueue;
import org.apache.activemq.command.ActiveMQTempDestination;
import org.apache.activemq.command.ConnectionId;

public class ActiveMQTempQueue
extends ActiveMQTempDestination
implements TemporaryQueue {
    public static final byte DATA_STRUCTURE_TYPE = 102;
    private static final long serialVersionUID = 6683049467527633867L;

    public ActiveMQTempQueue() {
    }

    public ActiveMQTempQueue(String name) {
        super(name);
    }

    public ActiveMQTempQueue(ConnectionId connectionId, long sequenceId) {
        super(connectionId.getValue(), sequenceId);
    }

    @Override
    public byte getDataStructureType() {
        return 102;
    }

    @Override
    public boolean isQueue() {
        return true;
    }

    @Override
    public String getQueueName() throws JMSException {
        return this.getPhysicalName();
    }

    @Override
    public byte getDestinationType() {
        return 5;
    }

    @Override
    protected String getQualifiedPrefix() {
        return "temp-queue://";
    }
}

