/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v5;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v5.BaseDataStreamMarshaller;

public class BrokerIdMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 124;
    }

    @Override
    public DataStructure createObject() {
        return new BrokerId();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        BrokerId info = (BrokerId)o;
        info.setValue(this.tightUnmarshalString(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        BrokerId info = (BrokerId)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalString1(info.getValue(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        BrokerId info = (BrokerId)o;
        this.tightMarshalString2(info.getValue(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        BrokerId info = (BrokerId)o;
        info.setValue(this.looseUnmarshalString(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        BrokerId info = (BrokerId)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getValue(), dataOut);
    }
}

