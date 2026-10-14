/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v7;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v7.BaseDataStreamMarshaller;

public class ProducerIdMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 123;
    }

    @Override
    public DataStructure createObject() {
        return new ProducerId();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ProducerId info = (ProducerId)o;
        info.setConnectionId(this.tightUnmarshalString(dataIn, bs));
        info.setValue(this.tightUnmarshalLong(wireFormat, dataIn, bs));
        info.setSessionId(this.tightUnmarshalLong(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ProducerId info = (ProducerId)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalString1(info.getConnectionId(), bs);
        rc += this.tightMarshalLong1(wireFormat, info.getValue(), bs);
        return (rc += this.tightMarshalLong1(wireFormat, info.getSessionId(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ProducerId info = (ProducerId)o;
        this.tightMarshalString2(info.getConnectionId(), dataOut, bs);
        this.tightMarshalLong2(wireFormat, info.getValue(), dataOut, bs);
        this.tightMarshalLong2(wireFormat, info.getSessionId(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ProducerId info = (ProducerId)o;
        info.setConnectionId(this.looseUnmarshalString(dataIn));
        info.setValue(this.looseUnmarshalLong(wireFormat, dataIn));
        info.setSessionId(this.looseUnmarshalLong(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ProducerId info = (ProducerId)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getConnectionId(), dataOut);
        this.looseMarshalLong(wireFormat, info.getValue(), dataOut);
        this.looseMarshalLong(wireFormat, info.getSessionId(), dataOut);
    }
}

