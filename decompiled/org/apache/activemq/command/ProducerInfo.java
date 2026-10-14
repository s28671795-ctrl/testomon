/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.state.CommandVisitor;

public class ProducerInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 6;
    protected ProducerId producerId;
    protected ActiveMQDestination destination;
    protected BrokerId[] brokerPath;
    protected boolean dispatchAsync;
    protected int windowSize;
    protected AtomicLong sentCount = new AtomicLong();

    public ProducerInfo() {
    }

    public ProducerInfo(ProducerId producerId) {
        this.producerId = producerId;
    }

    public ProducerInfo(SessionInfo sessionInfo, long producerId) {
        this.producerId = new ProducerId(sessionInfo.getSessionId(), producerId);
    }

    public ProducerInfo copy() {
        ProducerInfo info = new ProducerInfo();
        this.copy(info);
        return info;
    }

    public void copy(ProducerInfo info) {
        super.copy(info);
        info.producerId = this.producerId;
        info.destination = this.destination;
    }

    @Override
    public byte getDataStructureType() {
        return 6;
    }

    public ProducerId getProducerId() {
        return this.producerId;
    }

    public void setProducerId(ProducerId producerId) {
        this.producerId = producerId;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public RemoveInfo createRemoveCommand() {
        RemoveInfo command = new RemoveInfo(this.getProducerId());
        command.setResponseRequired(this.isResponseRequired());
        return command;
    }

    public BrokerId[] getBrokerPath() {
        return this.brokerPath;
    }

    public void setBrokerPath(BrokerId[] brokerPath) {
        this.brokerPath = brokerPath;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processAddProducer(this);
    }

    public boolean isDispatchAsync() {
        return this.dispatchAsync;
    }

    public void setDispatchAsync(boolean dispatchAsync) {
        this.dispatchAsync = dispatchAsync;
    }

    public int getWindowSize() {
        return this.windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public long getSentCount() {
        return this.sentCount.get();
    }

    public void incrementSentCount() {
        this.sentCount.incrementAndGet();
    }

    public void resetSentCount() {
        this.sentCount.set(0L);
    }
}

