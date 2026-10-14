/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataResponse;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v3.ResponseMarshaller;

public class DataResponseMarshaller
extends ResponseMarshaller {
    @Override
    public byte getDataStructureType() {
        return 32;
    }

    @Override
    public DataStructure createObject() {
        return new DataResponse();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        DataResponse info = (DataResponse)o;
        info.setData(this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        DataResponse info = (DataResponse)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalNestedObject1(wireFormat, info.getData(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        DataResponse info = (DataResponse)o;
        this.tightMarshalNestedObject2(wireFormat, info.getData(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        DataResponse info = (DataResponse)o;
        info.setData(this.looseUnmarsalNestedObject(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        DataResponse info = (DataResponse)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getData(), dataOut);
    }
}

