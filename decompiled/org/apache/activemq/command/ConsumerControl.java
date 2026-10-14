/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class ConsumerControl
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 17;
    protected ConsumerId consumerId;
    protected boolean close;
    protected boolean stop;
    protected boolean start;
    protected boolean flush;
    protected int prefetch;
    protected ActiveMQDestination destination;

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    @Override
    public byte getDataStructureType() {
        return 17;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processConsumerControl(this);
    }

    @Override
    public boolean isConsumerControl() {
        return true;
    }

    public boolean isClose() {
        return this.close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public ConsumerId getConsumerId() {
        return this.consumerId;
    }

    public void setConsumerId(ConsumerId consumerId) {
        this.consumerId = consumerId;
    }

    public int getPrefetch() {
        return this.prefetch;
    }

    public void setPrefetch(int prefetch) {
        this.prefetch = prefetch;
    }

    public boolean isFlush() {
        return this.flush;
    }

    public void setFlush(boolean flush) {
        this.flush = flush;
    }

    public boolean isStart() {
        return this.start;
    }

    public void setStart(boolean start2) {
        this.start = start2;
    }

    public boolean isStop() {
        return this.stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}

