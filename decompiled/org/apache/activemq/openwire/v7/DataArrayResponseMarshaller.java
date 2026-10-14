/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v7;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataArrayResponse;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v7.ResponseMarshaller;

public class DataArrayResponseMarshaller
extends ResponseMarshaller {
    @Override
    public byte getDataStructureType() {
        return 33;
    }

    @Override
    public DataStructure createObject() {
        return new DataArrayResponse();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        DataArrayResponse info = (DataArrayResponse)o;
        if (bs.readBoolean()) {
            int size = dataIn.readShort();
            DataStructure[] value = new DataStructure[size];
            for (int i = 0; i < size; ++i) {
                value[i] = this.tightUnmarsalNestedObject(wireFormat, dataIn, bs);
            }
            info.setData(value);
        } else {
            info.setData(null);
        }
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        DataArrayResponse info = (DataArrayResponse)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalObjectArray1(wireFormat, info.getData(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        DataArrayResponse info = (DataArrayResponse)o;
        this.tightMarshalObjectArray2(wireFormat, info.getData(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        DataArrayResponse info = (DataArrayResponse)o;
        if (dataIn.readBoolean()) {
            int size = dataIn.readShort();
            DataStructure[] value = new DataStructure[size];
            for (int i = 0; i < size; ++i) {
                value[i] = this.looseUnmarsalNestedObject(wireFormat, dataIn);
            }
            info.setData(value);
        } else {
            info.setData(null);
        }
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        DataArrayResponse info = (DataArrayResponse)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalObjectArray(wireFormat, info.getData(), dataOut);
    }
}

