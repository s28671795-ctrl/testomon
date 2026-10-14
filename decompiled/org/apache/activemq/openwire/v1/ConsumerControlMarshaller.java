/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire.v1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.openwire.v1.BaseCommandMarshaller;

public class ConsumerControlMarshaller
extends BaseCommandMarshaller {
    @Override
    public byte getDataStructureType() {
        return 17;
    }

    @Override
    public DataStructure createObject() {
        return new ConsumerControl();
    }

    @Override
    public void tightUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn, BooleanStream bs) throws IOException {
        super.tightUnmarshal(wireFormat, o, dataIn, bs);
        ConsumerControl info = (ConsumerControl)o;
        info.setClose(bs.readBoolean());
        info.setConsumerId((ConsumerId)this.tightUnmarsalNestedObject(wireFormat, dataIn, bs));
        info.setPrefetch(dataIn.readInt());
    }

    @Override
    public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {
        ConsumerControl info = (ConsumerControl)o;
        int rc = super.tightMarshal1(wireFormat, o, bs);
        bs.writeBoolean(info.isClose());
        return (rc += this.tightMarshalNestedObject1(wireFormat, info.getConsumerId(), bs)) + 4;
    }

    @Override
    public void tightMarshal2(OpenWireFormat wireFormat, Object o, DataOutput dataOut, BooleanStream bs) throws IOException {
        super.tightMarshal2(wireFormat, o, dataOut, bs);
        ConsumerControl info = (ConsumerControl)o;
        bs.readBoolean();
        this.tightMarshalNestedObject2(wireFormat, info.getConsumerId(), dataOut, bs);
        dataOut.writeInt(info.getPrefetch());
    }

    @Override
    public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
        super.looseUnmarshal(wireFormat, o, dataIn);
        ConsumerControl info = (ConsumerControl)o;
        info.setClose(dataIn.readBoolean());
        info.setConsumerId((ConsumerId)this.looseUnmarsalNestedObject(wireFormat, dataIn));
        info.setPrefetch(dataIn.readInt());
    }

    @Override
    public void looseMarshal(OpenWireFormat wireFormat, Object o, DataOutput dataOut) throws IOException {
        ConsumerControl info = (ConsumerControl)o;
        super.looseMarshal(wireFormat, o, dataOut);
        dataOut.writeBoolean(info.isClose());
        this.looseMarshalNestedObject(wireFormat, info.getConsumerId(), dataOut);
        dataOut.writeInt(info.getPrefetch());
    }
}

