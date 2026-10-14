/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v8;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.LocalTransactionId;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v8.TransactionIdMarshaller;

public class LocalTransactionIdMarshaller
extends TransactionIdMarshaller {
    @Override
    public byte getDataStructureType() {
        return 111;
    }

    @Override
    public DataStructure createObject() {
        return new LocalTransactionId();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        LocalTransactionId info = (LocalTransactionId)o;
        info.setValue(this.tightUnmarshalLong(wireFormat, dataIn, bs));
        info.setConnectionId((ConnectionId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        LocalTransactionId info = (LocalTransactionId)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalLong1(wireFormat, info.getValue(), bs);
        return (rc += this.tightMarshalCachedObject1(wireFormat, info.getConnectionId(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        LocalTransactionId info = (LocalTransactionId)o;
        this.tightMarshalLong2(wireFormat, info.getValue(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getConnectionId(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        LocalTransactionId info = (LocalTransactionId)o;
        info.setValue(this.looseUnmarshalLong(wireFormat, dataIn));
        info.setConnectionId((ConnectionId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        LocalTransactionId info = (LocalTransactionId)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalLong(wireFormat, info.getValue(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getConnectionId(), dataOut);
    }
}

