/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v7;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.Response;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v7.BaseCommandMarshaller;

public class ResponseMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 30;
    }

    @Override
    public DataStructure createObject() {
        return new Response();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        Response info = (Response)o;
        info.setCorrelationId(dataIn.readInt());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        Response info = (Response)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return rc + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        Response info = (Response)o;
        dataOut.writeInt(info.getCorrelationId());
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        Response info = (Response)o;
        info.setCorrelationId(dataIn.readInt());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        Response info = (Response)o;
        super.looseMarshal(wireFormat, o, dataOut);
        dataOut.writeInt(info.getCorrelationId());
    }
}

