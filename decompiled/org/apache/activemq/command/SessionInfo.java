/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionId;
import org.apache.activemq.state.CommandVisitor;

public class SessionInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 4;
    protected SessionId sessionId;

    public SessionInfo() {
        this.sessionId = new SessionId();
    }

    public SessionInfo(ConnectionInfo connectionInfo, long sessionId) {
        this.sessionId = new SessionId(connectionInfo.getConnectionId(), sessionId);
    }

    public SessionInfo(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public byte getDataStructureType() {
        return 4;
    }

    public SessionId getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    public RemoveInfo createRemoveCommand() {
        RemoveInfo command = new RemoveInfo(this.getSessionId());
        command.setResponseRequired(this.isResponseRequired());
        return command;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processAddSession(this);
    }
}

