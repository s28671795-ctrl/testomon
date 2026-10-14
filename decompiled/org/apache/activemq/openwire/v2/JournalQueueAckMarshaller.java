/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.JournalQueueAck;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v2.BaseDataStreamMarshaller;

public class JournalQueueAckMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 52;
    }

    @Override
    public DataStructure createObject() {
        return new JournalQueueAck();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        JournalQueueAck info = (JournalQueueAck)o;
        info.setDestination((ActiveMQDestination)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
        info.setMessageAck((MessageAck)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        JournalQueueAck info = (JournalQueueAck)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalNestedObject1(wireFormat, info.getDestination(), bs);
        return (rc += this.tightMarshalNestedObject1(wireFormat, info.getMessageAck(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        JournalQueueAck info = (JournalQueueAck)o;
        this.tightMarshalNestedObject2(wireFormat, info.getDestination(), dataOut, bs);
        this.tightMarshalNestedObject2(wireFormat, info.getMessageAck(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        JournalQueueAck info = (JournalQueueAck)o;
        info.setDestination((ActiveMQDestination)this.looseUnmarsalNestedObject(wireFormat, dataIn));
        info.setMessageAck((MessageAck)this.looseUnmarsalNestedObject(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        JournalQueueAck info = (JournalQueueAck)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getDestination(), dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getMessageAck(), dataOut);
    }
}

