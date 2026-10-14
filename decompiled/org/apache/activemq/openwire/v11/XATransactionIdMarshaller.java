/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v11;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.XATransactionId;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v11.TransactionIdMarshaller;

public class XATransactionIdMarshaller
extends TransactionIdMarshaller {
    @Override
    public byte getDataStructureType() {
        return 112;
    }

    @Override
    public DataStructure createObject() {
        return new XATransactionId();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        XATransactionId info = (XATransactionId)o;
        info.setFormatId(dataIn.readInt());
        info.setGlobalTransactionId(this.tightUnmarshalByteArray(dataIn, bs));
        info.setBranchQualifier(this.tightUnmarshalByteArray(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        XATransactionId info = (XATransactionId)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalByteArray1(info.getGlobalTransactionId(), bs);
        return (rc += this.tightMarshalByteArray1(info.getBranchQualifier(), bs)) + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        XATransactionId info = (XATransactionId)o;
        dataOut.writeInt(info.getFormatId());
        this.tightMarshalByteArray2(info.getGlobalTransactionId(), dataOut, bs);
        this.tightMarshalByteArray2(info.getBranchQualifier(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        XATransactionId info = (XATransactionId)o;
        info.setFormatId(dataIn.readInt());
        info.setGlobalTransactionId(this.looseUnmarshalByteArray(dataIn));
        info.setBranchQualifier(this.looseUnmarshalByteArray(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        XATransactionId info = (XATransactionId)o;
        super.looseMarshal(wireFormat, o, dataOut);
        dataOut.writeInt(info.getFormatId());
        this.looseMarshalByteArray(wireFormat, info.getGlobalTransactionId(), dataOut);
        this.looseMarshalByteArray(wireFormat, info.getBranchQualifier(), dataOut);
    }
}

