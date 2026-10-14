/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v11;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v11.BaseDataStreamMarshaller;

public abstract class ActiveMQDestinationMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ActiveMQDestination info = (ActiveMQDestination)o;
        info.setPhysicalName(this.tightUnmarshalString(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ActiveMQDestination info = (ActiveMQDestination)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        return (rc += this.tightMarshalString1(info.getPhysicalName(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ActiveMQDestination info = (ActiveMQDestination)o;
        this.tightMarshalString2(info.getPhysicalName(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ActiveMQDestination info = (ActiveMQDestination)o;
        info.setPhysicalName(this.looseUnmarshalString(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ActiveMQDestination info = (ActiveMQDestination)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getPhysicalName(), dataOut);
    }
}

