/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.IOException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class DestinationInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 8;
    public static final byte ADD_OPERATION_TYPE = 0;
    public static final byte REMOVE_OPERATION_TYPE = 1;
    protected ConnectionId connectionId;
    protected ActiveMQDestination destination;
    protected byte operationType;
    protected long timeout;
    protected BrokerId[] brokerPath;

    public DestinationInfo() {
    }

    public DestinationInfo(ConnectionId connectionId, byte operationType, ActiveMQDestination destination) {
        this.connectionId = connectionId;
        this.operationType = operationType;
        this.destination = destination;
    }

    @Override
    public byte getDataStructureType() {
        return 8;
    }

    public boolean isAddOperation() {
        return this.operationType == 0;
    }

    public boolean isRemoveOperation() {
        return this.operationType == 1;
    }

    public ConnectionId getConnectionId() {
        return this.connectionId;
    }

    public void setConnectionId(ConnectionId connectionId) {
        this.connectionId = connectionId;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public byte getOperationType() {
        return this.operationType;
    }

    public void setOperationType(byte operationType) {
        this.operationType = operationType;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public void setTimeout(long timeout2) {
        this.timeout = timeout2;
    }

    public BrokerId[] getBrokerPath() {
        return this.brokerPath;
    }

    public void setBrokerPath(BrokerId[] brokerPath) {
        this.brokerPath = brokerPath;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        if (this.isAddOperation()) {
            return visitor.processAddDestination(this);
        }
        if (this.isRemoveOperation()) {
            return visitor.processRemoveDestination(this);
        }
        throw new IOException("Unknown operation type: " + this.getOperationType());
    }

    public DestinationInfo copy() {
        DestinationInfo result = new DestinationInfo();
        super.copy(result);
        result.connectionId = this.connectionId;
        result.destination = this.destination;
        result.operationType = this.operationType;
        result.brokerPath = this.brokerPath;
        return result;
    }
}

