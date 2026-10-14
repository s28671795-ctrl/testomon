/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class ReplayCommand
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 65;
    private String producerId;
    private int firstAckNumber;
    private int lastAckNumber;
    private int firstNakNumber;
    private int lastNakNumber;

    @Override
    public byte getDataStructureType() {
        return 65;
    }

    public String getProducerId() {
        return this.producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public int getFirstAckNumber() {
        return this.firstAckNumber;
    }

    public void setFirstAckNumber(int firstSequenceNumber) {
        this.firstAckNumber = firstSequenceNumber;
    }

    public int getLastAckNumber() {
        return this.lastAckNumber;
    }

    public void setLastAckNumber(int lastSequenceNumber) {
        this.lastAckNumber = lastSequenceNumber;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return null;
    }

    public int getFirstNakNumber() {
        return this.firstNakNumber;
    }

    public void setFirstNakNumber(int firstNakNumber) {
        this.firstNakNumber = firstNakNumber;
    }

    public int getLastNakNumber() {
        return this.lastNakNumber;
    }

    public void setLastNakNumber(int lastNakNumber) {
        this.lastNakNumber = lastNakNumber;
    }

    @Override
    public String toString() {
        return "ReplayCommand {commandId = " + this.getCommandId() + ", firstNakNumber = " + this.getFirstNakNumber() + ", lastNakNumber = " + this.getLastNakNumber() + "}";
    }
}

