/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class ConnectionInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 3;
    protected ConnectionId connectionId;
    protected String clientId;
    protected String clientIp;
    protected String userName;
    protected String password;
    protected BrokerId[] brokerPath;
    protected boolean brokerMasterConnector;
    protected boolean manageable;
    protected boolean clientMaster = true;
    protected boolean faultTolerant = false;
    protected boolean failoverReconnect;
    protected transient Object transportContext;

    public ConnectionInfo() {
    }

    public ConnectionInfo(ConnectionId connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public byte getDataStructureType() {
        return 3;
    }

    public ConnectionInfo copy() {
        ConnectionInfo copy = new ConnectionInfo();
        this.copy(copy);
        return copy;
    }

    private void copy(ConnectionInfo copy) {
        super.copy(copy);
        copy.connectionId = this.connectionId;
        copy.clientId = this.clientId;
        copy.userName = this.userName;
        copy.password = this.password;
        copy.brokerPath = this.brokerPath;
        copy.brokerMasterConnector = this.brokerMasterConnector;
        copy.manageable = this.manageable;
        copy.clientMaster = this.clientMaster;
        copy.transportContext = this.transportContext;
        copy.faultTolerant = this.faultTolerant;
        copy.clientIp = this.clientIp;
    }

    public ConnectionId getConnectionId() {
        return this.connectionId;
    }

    public void setConnectionId(ConnectionId connectionId) {
        this.connectionId = connectionId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public RemoveInfo createRemoveCommand() {
        RemoveInfo command = new RemoveInfo(this.getConnectionId());
        command.setResponseRequired(this.isResponseRequired());
        return command;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BrokerId[] getBrokerPath() {
        return this.brokerPath;
    }

    public void setBrokerPath(BrokerId[] brokerPath) {
        this.brokerPath = brokerPath;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processAddConnection(this);
    }

    public boolean isBrokerMasterConnector() {
        return this.brokerMasterConnector;
    }

    public void setBrokerMasterConnector(boolean slaveBroker) {
        this.brokerMasterConnector = slaveBroker;
    }

    public boolean isManageable() {
        return this.manageable;
    }

    public void setManageable(boolean manageable) {
        this.manageable = manageable;
    }

    public Object getTransportContext() {
        return this.transportContext;
    }

    public void setTransportContext(Object transportContext) {
        this.transportContext = transportContext;
    }

    public boolean isClientMaster() {
        return this.clientMaster;
    }

    public void setClientMaster(boolean clientMaster) {
        this.clientMaster = clientMaster;
    }

    public boolean isFaultTolerant() {
        return this.faultTolerant;
    }

    public void setFaultTolerant(boolean faultTolerant) {
        this.faultTolerant = faultTolerant;
    }

    public boolean isFailoverReconnect() {
        return this.failoverReconnect;
    }

    public void setFailoverReconnect(boolean failoverReconnect) {
        this.failoverReconnect = failoverReconnect;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}

