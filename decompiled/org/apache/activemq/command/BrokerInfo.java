/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.IOException;
import java.util.Properties;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;
import org.apache.activemq.util.MarshallingSupport;

public class BrokerInfo
extends BaseCommand {
    private static final String PASSIVE_SLAVE_KEY = "passiveSlave";
    public static final byte DATA_STRUCTURE_TYPE = 2;
    BrokerId brokerId;
    String brokerURL;
    boolean slaveBroker;
    boolean masterBroker;
    boolean faultTolerantConfiguration;
    boolean networkConnection;
    boolean duplexConnection;
    BrokerInfo[] peerBrokerInfos;
    String brokerName;
    long connectionId;
    String brokerUploadUrl;
    String networkProperties;
    transient int refCount = 0;

    public BrokerInfo copy() {
        BrokerInfo copy = new BrokerInfo();
        this.copy(copy);
        return copy;
    }

    private void copy(BrokerInfo copy) {
        super.copy(copy);
        copy.brokerId = this.brokerId;
        copy.brokerURL = this.brokerURL;
        copy.slaveBroker = this.slaveBroker;
        copy.masterBroker = this.masterBroker;
        copy.faultTolerantConfiguration = this.faultTolerantConfiguration;
        copy.networkConnection = this.networkConnection;
        copy.duplexConnection = this.duplexConnection;
        copy.peerBrokerInfos = this.peerBrokerInfos;
        copy.brokerName = this.brokerName;
        copy.connectionId = this.connectionId;
        copy.brokerUploadUrl = this.brokerUploadUrl;
        copy.networkProperties = this.networkProperties;
    }

    @Override
    public boolean isBrokerInfo() {
        return true;
    }

    @Override
    public byte getDataStructureType() {
        return 2;
    }

    public BrokerId getBrokerId() {
        return this.brokerId;
    }

    public void setBrokerId(BrokerId brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerURL() {
        return this.brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public BrokerInfo[] getPeerBrokerInfos() {
        return this.peerBrokerInfos;
    }

    public void setPeerBrokerInfos(BrokerInfo[] peerBrokerInfos) {
        this.peerBrokerInfos = peerBrokerInfos;
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processBrokerInfo(this);
    }

    public boolean isSlaveBroker() {
        return this.slaveBroker;
    }

    public void setSlaveBroker(boolean slaveBroker) {
        this.slaveBroker = slaveBroker;
    }

    public boolean isMasterBroker() {
        return this.masterBroker;
    }

    public void setMasterBroker(boolean masterBroker) {
        this.masterBroker = masterBroker;
    }

    public boolean isFaultTolerantConfiguration() {
        return this.faultTolerantConfiguration;
    }

    public void setFaultTolerantConfiguration(boolean faultTolerantConfiguration) {
        this.faultTolerantConfiguration = faultTolerantConfiguration;
    }

    public boolean isDuplexConnection() {
        return this.duplexConnection;
    }

    public void setDuplexConnection(boolean duplexConnection) {
        this.duplexConnection = duplexConnection;
    }

    public boolean isNetworkConnection() {
        return this.networkConnection;
    }

    public void setNetworkConnection(boolean networkConnection) {
        this.networkConnection = networkConnection;
    }

    public long getConnectionId() {
        return this.connectionId;
    }

    public void setConnectionId(long connectionId) {
        this.connectionId = connectionId;
    }

    public String getBrokerUploadUrl() {
        return this.brokerUploadUrl;
    }

    public void setBrokerUploadUrl(String brokerUploadUrl) {
        this.brokerUploadUrl = brokerUploadUrl;
    }

    public String getNetworkProperties() {
        return this.networkProperties;
    }

    public void setNetworkProperties(String networkProperties) {
        this.networkProperties = networkProperties;
    }

    public boolean isPassiveSlave() {
        boolean result = false;
        Properties props = this.getProperties();
        if (props != null) {
            result = Boolean.parseBoolean(props.getProperty(PASSIVE_SLAVE_KEY, "false"));
        }
        return result;
    }

    public void setPassiveSlave(boolean value) {
        Properties props = new Properties();
        props.put(PASSIVE_SLAVE_KEY, Boolean.toString(value));
        try {
            this.networkProperties = MarshallingSupport.propertiesToString(props);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        Properties result = null;
        try {
            result = MarshallingSupport.stringToProperties(this.getNetworkProperties());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getRefCount() {
        return this.refCount;
    }

    public void incrementRefCount() {
        ++this.refCount;
    }

    public int decrementRefCount() {
        return --this.refCount;
    }
}

