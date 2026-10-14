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
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v10.BaseCommandMarshaller;

public class MessageDispatchMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 21;
    }

    @Override
    public DataStructure createObject() {
        return new MessageDispatch();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        MessageDispatch info = (MessageDispatch)o;
        info.setConsumerId((ConsumerId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setDestination((ActiveMQDestination)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setMessage((Message)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
        info.setRedeliveryCounter(dataIn.readInt());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        MessageDispatch info = (MessageDispatch)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getConsumerId(), bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getDestination(), bs);
        return (rc += this.tightMarshalNestedObject1(wireFormat, info.getMessage(), bs)) + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        MessageDispatch info = (MessageDispatch)o;
        this.tightMarshalCachedObject2(wireFormat, info.getConsumerId(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getDestination(), dataOut, bs);
        this.tightMarshalNestedObject2(wireFormat, info.getMessage(), dataOut, bs);
        dataOut.writeInt(info.getRedeliveryCounter());
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        MessageDispatch info = (MessageDispatch)o;
        info.setConsumerId((ConsumerId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setDestination((ActiveMQDestination)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setMessage((Message)this.looseUnmarsalNestedObject(wireFormat, dataIn));
        info.setRedeliveryCounter(dataIn.readInt());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        MessageDispatch info = (MessageDispatch)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getConsumerId(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getDestination(), dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getMessage(), dataOut);
        dataOut.writeInt(info.getRedeliveryCounter());
    }
}

