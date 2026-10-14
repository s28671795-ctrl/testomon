/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v9;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v9.BaseDataStreamMarshaller;

public class SubscriptionInfoMarshaller
extends BaseDataStreamMarshaller {
    @Override
    public byte getDataStructureType() {
        return 55;
    }

    @Override
    public DataStructure createObject() {
        return new SubscriptionInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        SubscriptionInfo info = (SubscriptionInfo)o;
        info.setClientId(this.tightUnmarshalString(dataIn, bs));
        info.setDestination((ActiveMQDestination)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setSelector(this.tightUnmarshalString(dataIn, bs));
        info.setSubcriptionName(this.tightUnmarshalString(dataIn, bs));
        info.setSubscribedDestination((ActiveMQDestination)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        SubscriptionInfo info = (SubscriptionInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalString1(info.getClientId(), bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getDestination(), bs);
        rc += this.tightMarshalString1(info.getSelector(), bs);
        rc += this.tightMarshalString1(info.getSubcriptionName(), bs);
        return (rc += this.tightMarshalNestedObject1(wireFormat, info.getSubscribedDestination(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        SubscriptionInfo info = (SubscriptionInfo)o;
        this.tightMarshalString2(info.getClientId(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getDestination(), dataOut, bs);
        this.tightMarshalString2(info.getSelector(), dataOut, bs);
        this.tightMarshalString2(info.getSubcriptionName(), dataOut, bs);
        this.tightMarshalNestedObject2(wireFormat, info.getSubscribedDestination(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        SubscriptionInfo info = (SubscriptionInfo)o;
        info.setClientId(this.looseUnmarshalString(dataIn));
        info.setDestination((ActiveMQDestination)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setSelector(this.looseUnmarshalString(dataIn));
        info.setSubcriptionName(this.looseUnmarshalString(dataIn));
        info.setSubscribedDestination((ActiveMQDestination)this.looseUnmarsalNestedObject(wireFormat, dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        SubscriptionInfo info = (SubscriptionInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalString(info.getClientId(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getDestination(), dataOut);
        this.looseMarshalString(info.getSelector(), dataOut);
        this.looseMarshalString(info.getSubcriptionName(), dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getSubscribedDestination(), dataOut);
    }
}

