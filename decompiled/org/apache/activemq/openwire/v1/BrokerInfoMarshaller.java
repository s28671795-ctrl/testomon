/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v1.BaseCommandMarshaller;

public class BrokerInfoMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 2;
    }

    @Override
    public DataStructure createObject() {
        return new BrokerInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        BrokerInfo info = (BrokerInfo)o;
        info.setBrokerId((BrokerId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setBrokerURL(this.tightUnmarshalString(dataIn, bs));
        if (bs.readBoolean()) {
            int size = dataIn.readShort();
            BrokerInfo[] value = new BrokerInfo[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (BrokerInfo)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs);
            }
            info.setPeerBrokerInfos(value);
        } else {
            info.setPeerBrokerInfos(null);
        }
        info.setBrokerName(this.tightUnmarshalString(dataIn, bs));
        info.setSlaveBroker(bs.readBoolean());
        info.setMasterBroker(bs.readBoolean());
        info.setFaultTolerantConfiguration(bs.readBoolean());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        BrokerInfo info = (BrokerInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getBrokerId(), bs);
        rc += this.tightMarshalString1(info.getBrokerURL(), bs);
        rc += this.tightMarshalObjectArray1(wireFormat, info.getPeerBrokerInfos(), bs);
        bs.writeBoolean(info.isSlaveBroker());
        bs.writeBoolean(info.isMasterBroker());
        bs.writeBoolean(info.isFaultTolerantConfiguration());
        return (rc += this.tightMarshalString1(info.getBrokerName(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        BrokerInfo info = (BrokerInfo)o;
        this.tightMarshalCachedObject2(wireFormat, info.getBrokerId(), dataOut, bs);
        this.tightMarshalString2(info.getBrokerURL(), dataOut, bs);
        this.tightMarshalObjectArray2(wireFormat, info.getPeerBrokerInfos(), dataOut, bs);
        this.tightMarshalString2(info.getBrokerName(), dataOut, bs);
        bs.readBoolean();
        bs.readBoolean();
        bs.readBoolean();
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        BrokerInfo info = (BrokerInfo)o;
        info.setBrokerId((BrokerId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setBrokerURL(this.looseUnmarshalString(dataIn));
        if (dataIn.readBoolean()) {
            int size = dataIn.readShort();
            BrokerInfo[] value = new BrokerInfo[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (BrokerInfo)this.looseUnmarsalNestedObject(wireFormat, dataIn);
            }
            info.setPeerBrokerInfos(value);
        } else {
            info.setPeerBrokerInfos(null);
        }
        info.setBrokerName(this.looseUnmarshalString(dataIn));
        info.setSlaveBroker(dataIn.readBoolean());
        info.setMasterBroker(dataIn.readBoolean());
        info.setFaultTolerantConfiguration(dataIn.readBoolean());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        BrokerInfo info = (BrokerInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getBrokerId(), dataOut);
        this.looseMarshalString(info.getBrokerURL(), dataOut);
        this.looseMarshalObjectArray(wireFormat, info.getPeerBrokerInfos(), dataOut);
        this.looseMarshalString(info.getBrokerName(), dataOut);
        dataOut.writeBoolean(info.isSlaveBroker());
        dataOut.writeBoolean(info.isMasterBroker());
        dataOut.writeBoolean(info.isFaultTolerantConfiguration());
    }
}

