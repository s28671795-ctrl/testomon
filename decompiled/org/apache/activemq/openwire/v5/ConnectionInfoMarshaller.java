/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v5;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v5.BaseCommandMarshaller;

public class ConnectionInfoMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 3;
    }

    @Override
    public DataStructure createObject() {
        return new ConnectionInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ConnectionInfo info = (ConnectionInfo)o;
        info.setConnectionId((ConnectionId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setClientId(this.tightUnmarshalString(dataIn, bs));
        info.setPassword(this.tightUnmarshalString(dataIn, bs));
        info.setUserName(this.tightUnmarshalString(dataIn, bs));
        if (bs.readBoolean()) {
            int size = dataIn.readShort();
            BrokerId[] value = new BrokerId[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (BrokerId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs);
            }
            info.setBrokerPath(value);
        } else {
            info.setBrokerPath(null);
        }
        info.setBrokerMasterConnector(bs.readBoolean());
        info.setManageable(bs.readBoolean());
        info.setClientMaster(bs.readBoolean());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ConnectionInfo info = (ConnectionInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getConnectionId(), bs);
        rc += this.tightMarshalString1(info.getClientId(), bs);
        rc += this.tightMarshalString1(info.getPassword(), bs);
        rc += this.tightMarshalString1(info.getUserName(), bs);
        bs.writeBoolean(info.isBrokerMasterConnector());
        bs.writeBoolean(info.isManageable());
        bs.writeBoolean(info.isClientMaster());
        return (rc += this.tightMarshalObjectArray1(wireFormat, info.getBrokerPath(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ConnectionInfo info = (ConnectionInfo)o;
        this.tightMarshalCachedObject2(wireFormat, info.getConnectionId(), dataOut, bs);
        this.tightMarshalString2(info.getClientId(), dataOut, bs);
        this.tightMarshalString2(info.getPassword(), dataOut, bs);
        this.tightMarshalString2(info.getUserName(), dataOut, bs);
        this.tightMarshalObjectArray2(wireFormat, info.getBrokerPath(), dataOut, bs);
        bs.readBoolean();
        bs.readBoolean();
        bs.readBoolean();
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ConnectionInfo info = (ConnectionInfo)o;
        info.setConnectionId((ConnectionId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setClientId(this.looseUnmarshalString(dataIn));
        info.setPassword(this.looseUnmarshalString(dataIn));
        info.setUserName(this.looseUnmarshalString(dataIn));
        if (dataIn.readBoolean()) {
            int size = dataIn.readShort();
            BrokerId[] value = new BrokerId[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (BrokerId)this.looseUnmarsalNestedObject(wireFormat, dataIn);
            }
            info.setBrokerPath(value);
        } else {
            info.setBrokerPath(null);
        }
        info.setBrokerMasterConnector(dataIn.readBoolean());
        info.setManageable(dataIn.readBoolean());
        info.setClientMaster(dataIn.readBoolean());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ConnectionInfo info = (ConnectionInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getConnectionId(), dataOut);
        this.looseMarshalString(info.getClientId(), dataOut);
        this.looseMarshalString(info.getPassword(), dataOut);
        this.looseMarshalString(info.getUserName(), dataOut);
        this.looseMarshalObjectArray(wireFormat, info.getBrokerPath(), dataOut);
        dataOut.writeBoolean(info.isBrokerMasterConnector());
        dataOut.writeBoolean(info.isManageable());
        dataOut.writeBoolean(info.isClientMaster());
    }
}

