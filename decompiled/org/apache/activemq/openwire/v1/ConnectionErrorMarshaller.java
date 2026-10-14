/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConnectionError;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v1.BaseCommandMarshaller;

public class ConnectionErrorMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 16;
    }

    @Override
    public DataStructure createObject() {
        return new ConnectionError();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ConnectionError info = (ConnectionError)o;
        info.setException(this.tightUnmarsalThrowable(wireFormat, dataIn, bs));
        info.setConnectionId((ConnectionId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ConnectionError info = (ConnectionError)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalThrowable1(wireFormat, info.getException(), bs);
        return (rc += this.tightMarshalNestedObject1(wireFormat, info.getConnectionId(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ConnectionError info = (ConnectionError)o;
        this.tightMarshalThrowable2(wireFormat, info.getException(), dataOut, bs);
        this.tightMarshalNestedObject2(wireFormat, info.getConnectionId(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ConnectionError info = (ConnectionError)o;
        info.setException(this.looseUnmarsalThrowable(wireFormat, dataIn));
        info.setConnectionId((ConnectionId)this.looseUnmarsalNestedObject(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ConnectionError info = (ConnectionError)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalThrowable(wireFormat, info.getException(), dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getConnectionId(), dataOut);
    }
}

