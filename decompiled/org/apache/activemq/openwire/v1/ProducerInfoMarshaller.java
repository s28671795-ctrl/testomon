/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v1.BaseCommandMarshaller;

public class ProducerInfoMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 6;
    }

    @Override
    public DataStructure createObject() {
        return new ProducerInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ProducerInfo info = (ProducerInfo)o;
        info.setProducerId((ProducerId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setDestination((ActiveMQDestination)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        if (bs.readBoolean()) {
            int size = dataIn.readShort();
            BrokerId[] value = new BrokerId[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (BrokerId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs);
            }
            info.setBrokerPath(value);
        } else {
            info.setBrokerPath(null);
        }
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ProducerInfo info = (ProducerInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getProducerId(), bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getDestination(), bs);
        return (rc += this.tightMarshalObjectArray1(wireFormat, info.getBrokerPath(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ProducerInfo info = (ProducerInfo)o;
        this.tightMarshalCachedObject2(wireFormat, info.getProducerId(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getDestination(), dataOut, bs);
        this.tightMarshalObjectArray2(wireFormat, info.getBrokerPath(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ProducerInfo info = (ProducerInfo)o;
        info.setProducerId((ProducerId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setDestination((ActiveMQDestination)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        if (dataIn.readBoolean()) {
            int size = dataIn.readShort();
            BrokerId[] value = new BrokerId[size];
            for (int i = 0; i < size; ++i) {
                value[i] = (BrokerId)this.looseUnmarsalNestedObject(wireFormat, dataIn);
            }
            info.setBrokerPath(value);
        } else {
            info.setBrokerPath(null);
        }
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ProducerInfo info = (ProducerInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getProducerId(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getDestination(), dataOut);
        this.looseMarshalObjectArray(wireFormat, info.getBrokerPath(), dataOut);
    }
}

