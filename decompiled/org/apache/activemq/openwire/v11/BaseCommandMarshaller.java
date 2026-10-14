/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v11;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v11.BaseDataStreamMarshaller;

public abstract class BaseCommandMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        BaseCommand info = (BaseCommand)o;
        info.setCommandId(dataIn.readInt());
        info.setResponseRequired(bs.readBoolean());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        BaseCommand info = (BaseCommand)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        bs.writeBoolean(info.isResponseRequired());
        return rc + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        BaseCommand info = (BaseCommand)o;
        dataOut.writeInt(info.getCommandId());
        bs.readBoolean();
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        BaseCommand info = (BaseCommand)o;
        info.setCommandId(dataIn.readInt());
        info.setResponseRequired(dataIn.readBoolean());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        BaseCommand info = (BaseCommand)o;
        super.looseMarshal(wireFormat, o, dataOut);
        dataOut.writeInt(info.getCommandId());
        dataOut.writeBoolean(info.isResponseRequired());
    }
}

