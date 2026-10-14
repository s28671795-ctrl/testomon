/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v8;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.JournalTrace;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v8.BaseDataStreamMarshaller;

public class JournalTraceMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 53;
    }

    @Override
    public DataStructure createObject() {
        return new JournalTrace();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        JournalTrace info = (JournalTrace)o;
        info.setMessage(this.tightUnmarshalString(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        JournalTrace info = (JournalTrace)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalString1(info.getMessage(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        JournalTrace info = (JournalTrace)o;
        this.tightMarshalString2(info.getMessage(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        JournalTrace info = (JournalTrace)o;
        info.setMessage(this.looseUnmarshalString(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        JournalTrace info = (JournalTrace)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getMessage(), dataOut);
    }
}

