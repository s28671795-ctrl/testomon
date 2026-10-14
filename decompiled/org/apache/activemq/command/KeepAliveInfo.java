/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;
import org.apache.activemq.util.IntrospectionSupport;

public class KeepAliveInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 10;
    private transient Endpoint from;
    private transient Endpoint to;

    @Override
    public byte getDataStructureType() {
        return 10;
    }

    @Override
    public boolean isResponse() {
        return false;
    }

    @Override
    public boolean isMessageDispatch() {
        return false;
    }

    @Override
    public boolean isMessage() {
        return false;
    }

    @Override
    public boolean isMessageAck() {
        return false;
    }

    @Override
    public boolean isBrokerInfo() {
        return false;
    }

    @Override
    public boolean isWireFormatInfo() {
        return false;
    }

    @Override
    public Endpoint getFrom() {
        return this.from;
    }

    @Override
    public void setFrom(Endpoint from) {
        this.from = from;
    }

    @Override
    public Endpoint getTo() {
        return this.to;
    }

    @Override
    public void setTo(Endpoint to) {
        this.to = to;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processKeepAlive(this);
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    @Override
    public boolean isMessageDispatchNotification() {
        return false;
    }

    @Override
    public boolean isShutdownInfo() {
        return false;
    }

    @Override
    public String toString() {
        return IntrospectionSupport.toString(this, KeepAliveInfo.class);
    }
}

