/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v6;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v6.BaseCommandMarshaller;

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
        info.setConnectedBrokers(this.tightUnmarshalString(dataIn, bs));
        info.setReconnectTo(this.tightUnmarshalString(dataIn, bs));
        info.setRebalanceConnection(bs.readBoolean());
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
        rc += this.tightMarshalString1(info.getConnectedBrokers(), bs);
        bs.writeBoolean(info.isRebalanceConnection());
        return (rc += this.tightMarshalString1(info.getReconnectTo(), bs)) + 0;
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
        this.tightMarshalString2(info.getConnectedBrokers(), dataOut, bs);
        this.tightMarshalString2(info.getReconnectTo(), dataOut, bs);
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
        info.setConnectedBrokers(this.looseUnmarshalString(dataIn));
        info.setReconnectTo(this.looseUnmarshalString(dataIn));
        info.setRebalanceConnection(dataIn.readBoolean());
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
        this.looseMarshalString(info.getConnectedBrokers(), dataOut);
        this.looseMarshalString(info.getReconnectTo(), dataOut);
        dataOut.writeBoolean(info.isRebalanceConnection());
    }
}

