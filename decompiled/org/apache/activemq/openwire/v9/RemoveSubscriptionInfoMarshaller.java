/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v9;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.RemoveSubscriptionInfo;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v9.BaseCommandMarshaller;

public class RemoveSubscriptionInfoMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 9;
    }

    @Override
    public DataStructure createObject() {
        return new RemoveSubscriptionInfo();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        RemoveSubscriptionInfo info = (RemoveSubscriptionInfo)o;
        info.setConnectionId((ConnectionId)this.tightUnmarsalCachedObject(wireFormat, dataIn, bs));
        info.setSubcriptionName(this.tightUnmarshalString(dataIn, bs));
        info.setClientId(this.tightUnmarshalString(dataIn, bs));
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        RemoveSubscriptionInfo info = (RemoveSubscriptionInfo)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        rc += this.tightMarshalCachedObject1(wireFormat, info.getConnectionId(), bs);
        rc += this.tightMarshalString1(info.getSubcriptionName(), bs);
        return (rc += this.tightMarshalString1(info.getClientId(), bs)) + 0;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        RemoveSubscriptionInfo info = (RemoveSubscriptionInfo)o;
        this.tightMarshalCachedObject2(wireFormat, info.getConnectionId(), dataOut, bs);
        this.tightMarshalString2(info.getSubcriptionName(), dataOut, bs);
        this.tightMarshalString2(info.getClientId(), dataOut, bs);
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        RemoveSubscriptionInfo info = (RemoveSubscriptionInfo)o;
        info.setConnectionId((ConnectionId)this.looseUnmarsalCachedObject(wireFormat, dataIn));
        info.setSubcriptionName(this.looseUnmarshalString(dataIn));
        info.setClientId(this.looseUnmarshalString(dataIn));
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        RemoveSubscriptionInfo info = (RemoveSubscriptionInfo)o;
        super.looseMarshal(wireFormat, o, dataOut);
        this.looseMarshalCachedObject(wireFormat, info.getConnectionId(), dataOut);
        this.looseMarshalString(info.getSubcriptionName(), dataOut);
        this.looseMarshalString(info.getClientId(), dataOut);
    }
}

