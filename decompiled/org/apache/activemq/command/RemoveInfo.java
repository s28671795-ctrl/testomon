/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.IOException;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.state.CommandVisitor;

public class RemoveInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 12;
    public static final int LAST_DELIVERED_UNSET = -1;
    public static final int LAST_DELIVERED_UNKNOWN = -2;
    protected DataStructure objectId;
    protected long lastDeliveredSequenceId = -2L;

    public RemoveInfo() {
    }

    public RemoveInfo(DataStructure objectId) {
        this.objectId = objectId;
    }

    @Override
    public byte getDataStructureType() {
        return 12;
    }

    public DataStructure getObjectId() {
        return this.objectId;
    }

    public void setObjectId(DataStructure objectId) {
        this.objectId = objectId;
    }

    public long getLastDeliveredSequenceId() {
        return this.lastDeliveredSequenceId;
    }

    public void setLastDeliveredSequenceId(long lastDeliveredSequenceId) {
        this.lastDeliveredSequenceId = lastDeliveredSequenceId;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        switch (this.objectId.getDataStructureType()) {
            case 120: {
                return visitor.processRemoveConnection((ConnectionId)this.objectId, this.lastDeliveredSequenceId);
            }
            case 121: {
                return visitor.processRemoveSession((SessionId)this.objectId, this.lastDeliveredSequenceId);
            }
            case 122: {
                return visitor.processRemoveConsumer((ConsumerId)this.objectId, this.lastDeliveredSequenceId);
            }
            case 123: {
                return visitor.processRemoveProducer((ProducerId)this.objectId);
            }
        }
        throw new IOException("Unknown remove command type: " + this.objectId.getDataStructureType());
    }

    public boolean isConnectionRemove() {
        return this.objectId.getDataStructureType() == 120;
    }

    public boolean isSessionRemove() {
        return this.objectId.getDataStructureType() == 121;
    }

    public boolean isConsumerRemove() {
        return this.objectId.getDataStructureType() == 122;
    }

    public boolean isProducerRemove() {
        return this.objectId.getDataStructureType() == 123;
    }
}

