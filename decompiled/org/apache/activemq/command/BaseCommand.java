/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.util.Map;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.util.IntrospectionSupport;

public abstract class BaseCommand
implements Command {
    protected int commandId;
    protected boolean responseRequired;
    private transient Endpoint from;
    private transient Endpoint to;

    public void copy(BaseCommand copy) {
        copy.commandId = this.commandId;
        copy.responseRequired = this.responseRequired;
    }

    @Override
    public int getCommandId() {
        return this.commandId;
    }

    @Override
    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    @Override
    public boolean isResponseRequired() {
        return this.responseRequired;
    }

    @Override
    public void setResponseRequired(boolean responseRequired) {
        this.responseRequired = responseRequired;
    }

    public String toString() {
        return this.toString(null);
    }

    public String toString(Map<String, Object> overrideFields) {
        return IntrospectionSupport.toString(this, BaseCommand.class, overrideFields);
    }

    @Override
    public boolean isWireFormatInfo() {
        return false;
    }

    @Override
    public boolean isBrokerInfo() {
        return false;
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
    public boolean isMarshallAware() {
        return false;
    }

    @Override
    public boolean isMessageAck() {
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
    public boolean isConnectionControl() {
        return false;
    }

    @Override
    public boolean isConsumerControl() {
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
}

