/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;
import org.apache.activemq.transport.TransmitCallback;

public class MessageDispatch
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 21;
    protected ConsumerId consumerId;
    protected ActiveMQDestination destination;
    protected Message message;
    protected int redeliveryCounter;
    protected transient long deliverySequenceId;
    protected transient Object consumer;
    protected transient TransmitCallback transmitCallback;
    protected transient Throwable rollbackCause;

    @Override
    public byte getDataStructureType() {
        return 21;
    }

    @Override
    public boolean isMessageDispatch() {
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

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getDeliverySequenceId() {
        return this.deliverySequenceId;
    }

    public void setDeliverySequenceId(long deliverySequenceId) {
        this.deliverySequenceId = deliverySequenceId;
    }

    public int getRedeliveryCounter() {
        return this.redeliveryCounter;
    }

    public void setRedeliveryCounter(int deliveryCounter) {
        this.redeliveryCounter = deliveryCounter;
    }

    public Object getConsumer() {
        return this.consumer;
    }

    public void setConsumer(Object consumer) {
        this.consumer = consumer;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processMessageDispatch(this);
    }

    public TransmitCallback getTransmitCallback() {
        return this.transmitCallback;
    }

    public void setTransmitCallback(TransmitCallback transmitCallback) {
        this.transmitCallback = transmitCallback;
    }

    public Throwable getRollbackCause() {
        return this.rollbackCause;
    }

    public void setRollbackCause(Throwable rollbackCause) {
        this.rollbackCause = rollbackCause;
    }
}

