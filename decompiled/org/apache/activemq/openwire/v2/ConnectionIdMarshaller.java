/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v2.BaseDataStreamMarshaller;

public class ConnectionIdMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 120;
    }

    @Override
    public DataStructure createObject() {
        return new ConnectionId();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ConnectionId info = (ConnectionId)o;
        info.setValue(this.tightUnmarshalString(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ConnectionId info = (ConnectionId)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalString1(info.getValue(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ConnectionId info = (ConnectionId)o;
        this.tightMarshalString2(info.getValue(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ConnectionId info = (ConnectionId)o;
        info.setValue(this.looseUnmarshalString(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ConnectionId info = (ConnectionId)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getValue(), dataOut);
    }
}

