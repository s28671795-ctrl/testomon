/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v9;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.command.TransactionInfo;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v9.BaseCommandMarshaller;

public class TransactionInfoMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 7;
    }

    @Override
    public DataStructure createObject() {
        return new TransactionInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        TransactionInfo info = (TransactionInfo)o;
        info.setConnectionId((ConnectionId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setTransactionId((TransactionId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setType(dataIn.readByte());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        TransactionInfo info = (TransactionInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getConnectionId(), bs);
        return (rc += this.tightMarshalCachedObject1(wireFormat, info.getTransactionId(), bs)) + 1;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        TransactionInfo info = (TransactionInfo)o;
        this.tightMarshalCachedObject2(wireFormat, info.getConnectionId(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getTransactionId(), dataOut, bs);
        dataOut.writeByte(info.getType());
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        TransactionInfo info = (TransactionInfo)o;
        info.setConnectionId((ConnectionId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setTransactionId((TransactionId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setType(dataIn.readByte());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        TransactionInfo info = (TransactionInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getConnectionId(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getTransactionId(), dataOut);
        dataOut.writeByte(info.getType());
    }
}

