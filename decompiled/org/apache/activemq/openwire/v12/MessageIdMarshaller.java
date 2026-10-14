/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v12;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v12.BaseDataStreamMarshaller;

public class MessageIdMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 110;
    }

    @Override
    public DataStructure createObject() {
        return new MessageId();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        MessageId info = (MessageId)o;
        info.setTextView(this.tightUnmarshalString(dataIn, bs));
        info.setProducerId((ProducerId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setProducerSequenceId(this.tightUnmarshalLong(wireFormat, dataIn, bs));
        info.setBrokerSequenceId(this.tightUnmarshalLong(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        MessageId info = (MessageId)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalString1(info.getTextView(), bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getProducerId(), bs);
        rc += this.tightMarshalLong1(wireFormat, info.getProducerSequenceId(), bs);
        return (rc += this.tightMarshalLong1(wireFormat, info.getBrokerSequenceId(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        MessageId info = (MessageId)o;
        this.tightMarshalString2(info.getTextView(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getProducerId(), dataOut, bs);
        this.tightMarshalLong2(wireFormat, info.getProducerSequenceId(), dataOut, bs);
        this.tightMarshalLong2(wireFormat, info.getBrokerSequenceId(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        MessageId info = (MessageId)o;
        info.setTextView(this.looseUnmarshalString(dataIn));
        info.setProducerId((ProducerId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setProducerSequenceId(this.looseUnmarshalLong(wireFormat, dataIn));
        info.setBrokerSequenceId(this.looseUnmarshalLong(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        MessageId info = (MessageId)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getTextView(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getProducerId(), dataOut);
        this.looseMarshalLong(wireFormat, info.getProducerSequenceId(), dataOut);
        this.looseMarshalLong(wireFormat, info.getBrokerSequenceId(), dataOut);
    }
}

