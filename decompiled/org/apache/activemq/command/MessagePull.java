/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.TransientInitializer;
import org.apache.activemq.state.CommandVisitor;

public class MessagePull
extends BaseCommand
implements TransientInitializer {
    public static final byte DATA_STRUCTURE_TYPE = 20;
    protected ConsumerId consumerId;
    protected ActiveMQDestination destination;
    protected long timeout;
    private MessageId messageId;
    private String correlationId;
    private transient int quantity = 1;
    private transient boolean alwaysSignalDone;
    private transient boolean tracked = false;

    @Override
    public byte getDataStructureType() {
        return 20;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processMessagePull(this);
    }

    public void configure(ConsumerInfo info) {
        this.setConsumerId(info.getConsumerId());
        this.setDestination(info.getDestination());
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

    public long getTimeout() {
        return this.timeout;
    }

    public void setTimeout(long timeout2) {
        this.timeout = timeout2;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public MessageId getMessageId() {
        return this.messageId;
    }

    public void setMessageId(MessageId messageId) {
        this.messageId = messageId;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }

    public boolean isTracked() {
        return this.tracked;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAlwaysSignalDone() {
        return this.alwaysSignalDone;
    }

    public void setAlwaysSignalDone(boolean alwaysSignalDone) {
        this.alwaysSignalDone = alwaysSignalDone;
    }

    @Override
    public void initTransients() {
        this.quantity = 1;
        this.alwaysSignalDone = false;
        this.tracked = false;
    }
}

