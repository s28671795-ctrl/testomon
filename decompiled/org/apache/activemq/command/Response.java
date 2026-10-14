/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.state.CommandVisitor;

public class Response
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 30;
    int correlationId;

    @Override
    public byte getDataStructureType() {
        return 30;
    }

    public int getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(int responseId) {
        this.correlationId = responseId;
    }

    @Override
    public boolean isResponse() {
        return true;
    }

    public boolean isException() {
        return false;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return null;
    }
}

