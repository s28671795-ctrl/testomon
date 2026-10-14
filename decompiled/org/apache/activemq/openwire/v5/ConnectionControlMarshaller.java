/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v5;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v5.BaseCommandMarshaller;

public class ConnectionControlMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 18;
    }

    @Override
    public DataStructure createObject() {
        return new ConnectionControl();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ConnectionControl info = (ConnectionControl)o;
        info.setClose(bs.readBoolean());
        info.setExit(bs.readBoolean());
        info.setFaultTolerant(bs.readBoolean());
        info.setResume(bs.readBoolean());
        info.setSuspend(bs.readBoolean());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ConnectionControl info = (ConnectionControl)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        bs.writeBoolean(info.isClose());
        bs.writeBoolean(info.isExit());
        bs.writeBoolean(info.isFaultTolerant());
        bs.writeBoolean(info.isResume());
        bs.writeBoolean(info.isSuspend());
        return rc + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ConnectionControl info = (ConnectionControl)o;
        bs.readBoolean();
        bs.readBoolean();
        bs.readBoolean();
        bs.readBoolean();
        bs.readBoolean();
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ConnectionControl info = (ConnectionControl)o;
        info.setClose(dataIn.readBoolean());
        info.setExit(dataIn.readBoolean());
        info.setFaultTolerant(dataIn.readBoolean());
        info.setResume(dataIn.readBoolean());
        info.setSuspend(dataIn.readBoolean());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ConnectionControl info = (ConnectionControl)o;
        super.looseMarshal(wireFormat, o, dataOut);
        dataOut.writeBoolean(info.isClose());
        dataOut.writeBoolean(info.isExit());
        dataOut.writeBoolean(info.isFaultTolerant());
        dataOut.writeBoolean(info.isResume());
        dataOut.writeBoolean(info.isSuspend());
    }
}

