/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v3.BaseDataStreamMarshaller;

public class WireFormatInfoMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 1;
    }

    @Override
    public DataStructure createObject() {
        return new WireFormatInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        WireFormatInfo info = (WireFormatInfo)o;
        info.beforeUnmarshall(wireFormat);
        info.setMagic(this.tightUnmarshalConstByteArray(dataIn, bs, 8));
        info.setVersion(dataIn.readInt());
        info.setMarshalledProperties(this.tightUnmarshalByteSequence(dataIn, bs));
        info.afterUnmarshall(wireFormat);
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        WireFormatInfo info = (WireFormatInfo)o;
        info.beforeMarshall(wireFormat);
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalConstByteArray1(info.getMagic(), bs, 8);
        return (rc += this.tightMarshalByteSequence1(info.getMarshalledProperties(), bs)) + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        WireFormatInfo info = (WireFormatInfo)o;
        this.tightMarshalConstByteArray2(info.getMagic(), dataOut, bs, 8);
        dataOut.writeInt(info.getVersion());
        this.tightMarshalByteSequence2(info.getMarshalledProperties(), dataOut, bs);
        info.afterMarshall(wireFormat);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        WireFormatInfo info = (WireFormatInfo)o;
        info.beforeUnmarshall(wireFormat);
        info.setMagic(this.looseUnmarshalConstByteArray(dataIn, 8));
        info.setVersion(dataIn.readInt());
        info.setMarshalledProperties(this.looseUnmarshalByteSequence(dataIn));
        info.afterUnmarshall(wireFormat);
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        WireFormatInfo info = (WireFormatInfo)o;
        info.beforeMarshall(wireFormat);
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalConstByteArray(wireFormat, info.getMagic(), dataOut, 8);
        dataOut.writeInt(info.getVersion());
        this.looseMarshalByteSequence(wireFormat, info.getMarshalledProperties(), dataOut);
    }
}

