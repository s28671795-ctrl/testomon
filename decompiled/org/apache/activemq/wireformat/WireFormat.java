/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.wireformat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.activemq.util.ByteSequence;

public interface WireFormat {
    public ByteSequence marshal(Object var1) throws IOException;

    public Object unmarshal(ByteSequence var1) throws IOException;

    public void marshal(Object var1, DataOutput var2) throws IOException;

    public Object unmarshal(DataInput var1) throws IOException;

    public void setVersion(int var1);

    public int getVersion();
}

