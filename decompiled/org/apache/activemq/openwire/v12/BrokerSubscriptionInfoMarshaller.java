/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v12;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerSubscriptionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v12.BaseCommandMarshaller;

public class BrokerSubscriptionInfoMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 92;
    }

    @Override
    public DataStructure createObject() {
        return new BrokerSubscriptionInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        BrokerSubscriptionInfo info = (BrokerSubscriptionInfo)o;
        info.setBrokerId((BrokerId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
        info.setBrokerName(this.tightUnmarshalString(dataIn, bs));
        if (bs.readBoolean()) {
            int size = dataIn.readShort();
            ConsumerInfo[] value = new ConsumerInfo[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (ConsumerInfo)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs);
            }
            info.setSubscriptionInfos(value);
        } else {
            info.setSubscriptionInfos(null);
        }
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        BrokerSubscriptionInfo info = (BrokerSubscriptionInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalNestedObject1(wireFormat, info.getBrokerId(), bs);
        rc += this.tightMarshalString1(info.getBrokerName(), bs);
        return (rc += this.tightMarshalObjectArray1(wireFormat, info.getSubscriptionInfos(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        BrokerSubscriptionInfo info = (BrokerSubscriptionInfo)o;
        this.tightMarshalNestedObject2(wireFormat, info.getBrokerId(), dataOut, bs);
        this.tightMarshalString2(info.getBrokerName(), dataOut, bs);
        this.tightMarshalObjectArray2(wireFormat, info.getSubscriptionInfos(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        BrokerSubscriptionInfo info = (BrokerSubscriptionInfo)o;
        info.setBrokerId((BrokerId)this.looseUnmarsalNestedObject(wireFormat, dataIn));
        info.setBrokerName(this.looseUnmarshalString(dataIn));
        if (dataIn.readBoolean()) {
            int size = dataIn.readShort();
            ConsumerInfo[] value = new ConsumerInfo[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (ConsumerInfo)this.looseUnmarsalNestedObject(wireFormat, dataIn);
            }
            info.setSubscriptionInfos(value);
        } else {
            info.setSubscriptionInfos(null);
        }
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        BrokerSubscriptionInfo info = (BrokerSubscriptionInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalNestedObject(wireFormat, info.getBrokerId(), dataOut);
        this.looseMarshalString(info.getBrokerName(), dataOut);
        this.looseMarshalObjectArray(wireFormat, info.getSubscriptionInfos(), dataOut);
    }
}

