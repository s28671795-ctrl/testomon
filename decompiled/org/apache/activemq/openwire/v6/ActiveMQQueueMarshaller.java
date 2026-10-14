/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v6;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v6.ActiveMQDestinationMarshaller;

public class ActiveMQQueueMarshaller
extends ActiveMQDestinationMarshaller {
    @Override
    public byte getDataStructureType() {
        return 100;
    }

    @Override
    public DataStructure createObject() {
        return new ActiveMQQueue();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return rc + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        super.looseMarshal(wireFormat, o, dataOut);
    }
}

