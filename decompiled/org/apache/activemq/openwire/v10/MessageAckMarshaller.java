/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v10;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v10.BaseCommandMarshaller;

public class MessageAckMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 22;
    }

    @Override
    public DataStructure createObject() {
        return new MessageAck();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        MessageAck info = (MessageAck)o;
        info.setDestination((ActiveMQDestination)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setTransactionId((TransactionId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setConsumerId((ConsumerId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setAckType(dataIn.readByte());
        info.setFirstMessageId((MessageId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
        info.setLastMessageId((MessageId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
        info.setMessageCount(dataIn.readInt());
        info.setPoisonCause(this.tightUnmarsalThrowable(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        MessageAck info = (MessageAck)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getDestination(), bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getTransactionId(), bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getConsumerId(), bs);
        rc += this.tightMarshalNestedObject1(wireFormat, info.getFirstMessageId(), bs);
        rc += this.tightMarshalNestedObject1(wireFormat, info.getLastMessageId(), bs);
        return (rc += this.tightMarshalThrowable1(wireFormat, info.getPoisonCause(), bs)) + 5;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        MessageAck info = (MessageAck)o;
        this.tightMarshalCachedObject2(wireFormat, info.getDestination(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getTransactionId(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getConsumerId(), dataOut, bs);
        dataOut.writeByte(info.getAckType());
        this.tightMarshalNestedObject2(wireFormat, info.getFirstMessageId(), dataOut, bs);
        this.tightMarshalNestedObject2(wireFormat, info.getLastMessageId(), dataOut, bs);
        dataOut.writeInt(info.getMessageCount());
        this.tightMarshalThrowable2(wireFormat, info.getPoisonCause(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        MessageAck info = (MessageAck)o;
        info.setDestination((ActiveMQDestination)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setTransactionId((TransactionId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setConsumerId((ConsumerId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setAckType(dataIn.readByte());
        info.setFirstMessageId((MessageId)this.looseUnmarsalNestedObject(wireFormat, dataIn));
        info.setLastMessageId((MessageId)this.looseUnmarsalNestedObject(wireFormat, dataIn));
        info.setMessageCount(dataIn.readInt());
        info.setPoisonCause(this.looseUnmarsalThrowable(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        MessageAck info = (MessageAck)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getDestination(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getTransactionId(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getConsumerId(), dataOut);
        dataOut.writeByte(info.getAckType());
        this.looseMarshalNestedObject(wireFormat, info.getFirstMessageId(), dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getLastMessageId(), dataOut);
        dataOut.writeInt(info.getMessageCount());
        this.looseMarshalThrowable(wireFormat, info.getPoisonCause(), dataOut);
    }
}

