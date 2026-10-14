/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v9;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v9.ActiveMQMessageMarshaller;

public class ActiveMQBlobMessageMarshaller
extends ActiveMQMessageMarshaller {
    @Override
    public byte getDataStructureType() {
        return 29;
    }

    @Override
    public DataStructure createObject() {
        return new ActiveMQBlobMessage();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ActiveMQBlobMessage info = (ActiveMQBlobMessage)o;
        info.setRemoteBlobUrl(this.tightUnmarshalString(dataIn, bs));
        info.setMimeType(this.tightUnmarshalString(dataIn, bs));
        info.setDeletedByBroker(bs.readBoolean());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ActiveMQBlobMessage info = (ActiveMQBlobMessage)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalString1(info.getRemoteBlobUrl(), bs);
        bs.writeBoolean(info.isDeletedByBroker());
        return (rc += this.tightMarshalString1(info.getMimeType(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ActiveMQBlobMessage info = (ActiveMQBlobMessage)o;
        this.tightMarshalString2(info.getRemoteBlobUrl(), dataOut, bs);
        this.tightMarshalString2(info.getMimeType(), dataOut, bs);
        bs.readBoolean();
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ActiveMQBlobMessage info = (ActiveMQBlobMessage)o;
        info.setRemoteBlobUrl(this.looseUnmarshalString(dataIn));
        info.setMimeType(this.looseUnmarshalString(dataIn));
        info.setDeletedByBroker(dataIn.readBoolean());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ActiveMQBlobMessage info = (ActiveMQBlobMessage)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getRemoteBlobUrl(), dataOut);
        this.looseMarshalString(info.getMimeType(), dataOut);
        dataOut.writeBoolean(info.isDeletedByBroker());
    }
}

