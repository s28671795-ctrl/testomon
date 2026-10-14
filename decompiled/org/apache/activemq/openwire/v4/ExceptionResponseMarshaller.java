/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v4.ResponseMarshaller;

public class ExceptionResponseMarshaller
extends ResponseMarshaller {
    @Override
    public byte getDataStructureType() {
        return 31;
    }

    @Override
    public DataStructure createObject() {
        return new ExceptionResponse();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ExceptionResponse info = (ExceptionResponse)o;
        info.setException(this.tightUnmarsalThrowable(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ExceptionResponse info = (ExceptionResponse)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalThrowable1(wireFormat, info.getException(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ExceptionResponse info = (ExceptionResponse)o;
        this.tightMarshalThrowable2(wireFormat, info.getException(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ExceptionResponse info = (ExceptionResponse)o;
        info.setException(this.looseUnmarsalThrowable(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ExceptionResponse info = (ExceptionResponse)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalThrowable(wireFormat, info.getException(), dataOut);
    }
}

