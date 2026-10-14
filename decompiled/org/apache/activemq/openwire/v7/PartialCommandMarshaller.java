/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v7;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.PartialCommand;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v7.BaseDataStreamMarshaller;

public class PartialCommandMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 60;
    }

    @Override
    public DataStructure createObject() {
        return new PartialCommand();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        PartialCommand info = (PartialCommand)o;
        info.setCommandId(dataIn.readInt());
        info.setData(this.tightUnmarshalByteArray(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        PartialCommand info = (PartialCommand)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalByteArray1(info.getData(), bs)) + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        PartialCommand info = (PartialCommand)o;
        dataOut.writeInt(info.getCommandId());
        this.tightMarshalByteArray2(info.getData(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        PartialCommand info = (PartialCommand)o;
        info.setCommandId(dataIn.readInt());
        info.setData(this.looseUnmarshalByteArray(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        PartialCommand info = (PartialCommand)o;
        super.looseMarshal(wireFormat, o, dataOut);
        dataOut.writeInt(info.getCommandId());
        this.looseMarshalByteArray(wireFormat, info.getData(), dataOut);
    }
}

