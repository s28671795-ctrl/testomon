/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v11;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.NetworkBridgeFilter;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v11.BaseDataStreamMarshaller;

public class NetworkBridgeFilterMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 91;
    }

    @Override
    public DataStructure createObject() {
        return new NetworkBridgeFilter();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        NetworkBridgeFilter info = (NetworkBridgeFilter)o;
        info.setNetworkBrokerId((BrokerId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setMessageTTL(dataIn.readInt());
        info.setConsumerTTL(dataIn.readInt());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        NetworkBridgeFilter info = (NetworkBridgeFilter)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalCachedObject1(wireFormat, info.getNetworkBrokerId(), bs)) + 8;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        NetworkBridgeFilter info = (NetworkBridgeFilter)o;
        this.tightMarshalCachedObject2(wireFormat, info.getNetworkBrokerId(), dataOut, bs);
        dataOut.writeInt(info.getMessageTTL());
        dataOut.writeInt(info.getConsumerTTL());
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        NetworkBridgeFilter info = (NetworkBridgeFilter)o;
        info.setNetworkBrokerId((BrokerId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setMessageTTL(dataIn.readInt());
        info.setConsumerTTL(dataIn.readInt());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        NetworkBridgeFilter info = (NetworkBridgeFilter)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getNetworkBrokerId(), dataOut);
        dataOut.writeInt(info.getMessageTTL());
        dataOut.writeInt(info.getConsumerTTL());
    }
}

