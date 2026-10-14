/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v10;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v10.BaseCommandMarshaller;

public class DestinationInfoMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 8;
    }

    @Override
    public DataStructure createObject() {
        return new DestinationInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        DestinationInfo info = (DestinationInfo)o;
        info.setConnectionId((ConnectionId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setDestination((ActiveMQDestination)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setOperationType(dataIn.readByte());
        info.setTimeout(this.tightUnmarshalLong(wireFormat, dataIn, bs));
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
        DestinationInfo info = (DestinationInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getConnectionId(), bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getDestination(), bs);
        rc += this.tightMarshalLong1(wireFormat, info.getTimeout(), bs);
        return (rc += this.tightMarshalObjectArray1(wireFormat, info.getBrokerPath(), bs)) + 1;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        DestinationInfo info = (DestinationInfo)o;
        this.tightMarshalCachedObject2(wireFormat, info.getConnectionId(), dataOut, bs);
        this.tightMarshalCachedObject2(wireFormat, info.getDestination(), dataOut, bs);
        dataOut.writeByte(info.getOperationType());
        this.tightMarshalLong2(wireFormat, info.getTimeout(), dataOut, bs);
        this.tightMarshalObjectArray2(wireFormat, info.getBrokerPath(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        DestinationInfo info = (DestinationInfo)o;
        info.setConnectionId((ConnectionId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setDestination((ActiveMQDestination)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setOperationType(dataIn.readByte());
        info.setTimeout(this.looseUnmarshalLong(wireFormat, dataIn));
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
        DestinationInfo info = (DestinationInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getConnectionId(), dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getDestination(), dataOut);
        dataOut.writeByte(info.getOperationType());
        this.looseMarshalLong(wireFormat, info.getTimeout(), dataOut);
        this.looseMarshalObjectArray(wireFormat, info.getBrokerPath(), dataOut);
    }
}

