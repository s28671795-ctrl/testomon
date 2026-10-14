/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v6;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ControlCommand;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v6.BaseCommandMarshaller;

public class ControlCommandMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 14;
    }

    @Override
    public DataStructure createObject() {
        return new ControlCommand();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ControlCommand info = (ControlCommand)o;
        info.setCommand(this.tightUnmarshalString(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ControlCommand info = (ControlCommand)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalString1(info.getCommand(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ControlCommand info = (ControlCommand)o;
        this.tightMarshalString2(info.getCommand(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ControlCommand info = (ControlCommand)o;
        info.setCommand(this.looseUnmarshalString(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ControlCommand info = (ControlCommand)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getCommand(), dataOut);
    }
}

