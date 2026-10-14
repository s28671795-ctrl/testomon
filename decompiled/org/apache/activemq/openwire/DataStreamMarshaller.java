/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;

public interface DataStreamMarshaller {
    public byte getDataStructureType();

    public DataStructure createObject();

    public int tightMarshal1(OpenWireFormat var1, Object var2, BooleanStream var3) throws IOException;

    public void tightMarshal2(OpenWireFormat var1, Object var2, DataOutput var3, BooleanStream var4) throws IOException;

    public void tightUnmarshal(OpenWireFormat var1, Object var2, DataInput var3, BooleanStream var4) throws IOException;

    public void looseMarshal(OpenWireFormat var1, Object var2, DataOutput var3) throws IOException;

    public void looseUnmarshal(OpenWireFormat var1, Object var2, DataInput var3) throws IOException;
}

