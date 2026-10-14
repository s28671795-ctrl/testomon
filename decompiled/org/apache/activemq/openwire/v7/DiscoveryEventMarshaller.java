/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v7;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v7.BaseDataStreamMarshaller;

public class DiscoveryEventMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 40;
    }

    @Override
    public DataStructure createObject() {
        return new DiscoveryEvent();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        DiscoveryEvent info = (DiscoveryEvent)o;
        info.setServiceName(this.tightUnmarshalString(dataIn, bs));
        info.setBrokerName(this.tightUnmarshalString(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        DiscoveryEvent info = (DiscoveryEvent)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalString1(info.getServiceName(), bs);
        return (rc += this.tightMarshalString1(info.getBrokerName(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        DiscoveryEvent info = (DiscoveryEvent)o;
        this.tightMarshalString2(info.getServiceName(), dataOut, bs);
        this.tightMarshalString2(info.getBrokerName(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        DiscoveryEvent info = (DiscoveryEvent)o;
        info.setServiceName(this.looseUnmarshalString(dataIn));
        info.setBrokerName(this.looseUnmarshalString(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        DiscoveryEvent info = (DiscoveryEvent)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getServiceName(), dataOut);
        this.looseMarshalString(info.getBrokerName(), dataOut);
    }
}

