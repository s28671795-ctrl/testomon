/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v3.BaseCommandMarshaller;

public class ProducerAckMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 19;
    }

    @Override
    public DataStructure createObject() {
        return new ProducerAck();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ProducerAck info = (ProducerAck)o;
        info.setProducerId((ProducerId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
        info.setSize(dataIn.readInt());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ProducerAck info = (ProducerAck)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalNestedObject1(wireFormat, info.getProducerId(), bs)) + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ProducerAck info = (ProducerAck)o;
        this.tightMarshalNestedObject2(wireFormat, info.getProducerId(), dataOut, bs);
        dataOut.writeInt(info.getSize());
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ProducerAck info = (ProducerAck)o;
        info.setProducerId((ProducerId)this.looseUnmarsalNestedObject(wireFormat, dataIn));
        info.setSize(dataIn.readInt());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ProducerAck info = (ProducerAck)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getProducerId(), dataOut);
        dataOut.writeInt(info.getSize());
    }
}

