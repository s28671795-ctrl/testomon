/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.nio.ByteBuffer;
import org.apache.activemq.Service;

public interface ByteBufferPool
extends Service {
    public ByteBuffer borrowBuffer();

    public void returnBuffer(ByteBuffer var1);

    public void setDefaultSize(int var1);
}

