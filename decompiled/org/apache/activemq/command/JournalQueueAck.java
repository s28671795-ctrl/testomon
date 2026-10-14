/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.util.IntrospectionSupport;

public class JournalQueueAck
implements DataStructure {
    public static final byte DATA_STRUCTURE_TYPE = 52;
    ActiveMQDestination destination;
    MessageAck messageAck;

    @Override
    public byte getDataStructureType() {
        return 52;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public MessageAck getMessageAck() {
        return this.messageAck;
    }

    public void setMessageAck(MessageAck messageAck) {
        this.messageAck = messageAck;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    public String toString() {
        return IntrospectionSupport.toString(this, JournalQueueAck.class);
    }
}

