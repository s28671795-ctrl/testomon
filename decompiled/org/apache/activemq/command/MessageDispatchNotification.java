/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class MessageDispatchNotification
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 90;
    protected ConsumerId consumerId;
    protected ActiveMQDestination destination;
    protected MessageId messageId;
    protected long deliverySequenceId;

    @Override
    public byte getDataStructureType() {
        return 90;
    }

    @Override
    public boolean isMessageDispatchNotification() {
        return true;
    }

    public ConsumerId getConsumerId() {
        return this.consumerId;
    }

    public void setConsumerId(ConsumerId consumerId) {
        this.consumerId = consumerId;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public long getDeliverySequenceId() {
        return this.deliverySequenceId;
    }

    public void setDeliverySequenceId(long deliverySequenceId) {
        this.deliverySequenceId = deliverySequenceId;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processMessageDispatchNotification(this);
    }

    public MessageId getMessageId() {
        return this.messageId;
    }

    public void setMessageId(MessageId messageId) {
        this.messageId = messageId;
    }
}

