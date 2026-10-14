/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v6;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.IntegerResponse;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v6.ResponseMarshaller;

public class IntegerResponseMarshaller
extends ResponseMarshaller {
    @Override
    public byte getDataStructureType() {
        return 34;
    }

    @Override
    public DataStructure createObject() {
        return new IntegerResponse();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        IntegerResponse info = (IntegerResponse)o;
        info.setResult(dataIn.readInt());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        IntegerResponse info = (IntegerResponse)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return rc + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        IntegerResponse info = (IntegerResponse)o;
        dataOut.writeInt(info.getResult());
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        IntegerResponse info = (IntegerResponse)o;
        info.setResult(dataIn.readInt());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        IntegerResponse info = (IntegerResponse)o;
        super.looseMarshal(wireFormat, o, dataOut);
        dataOut.writeInt(info.getResult());
    }
}

