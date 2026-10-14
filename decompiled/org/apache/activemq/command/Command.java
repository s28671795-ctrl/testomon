/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public interface Command
extends DataStructure {
    public void setCommandId(int var1);

    public int getCommandId();

    public void setResponseRequired(boolean var1);

    public boolean isResponseRequired();

    public boolean isResponse();

    public boolean isMessageDispatch();

    public boolean isBrokerInfo();

    public boolean isWireFormatInfo();

    public boolean isMessage();

    public boolean isMessageAck();

    public boolean isMessageDispatchNotification();

    public boolean isShutdownInfo();

    public boolean isConnectionControl();

    public boolean isConsumerControl();

    public Response visit(CommandVisitor var1) throws Exception;

    public Endpoint getFrom();

    public void setFrom(Endpoint var1);

    public Endpoint getTo();

    public void setTo(Endpoint var1);
}

