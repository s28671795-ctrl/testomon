/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import java.nio.ByteBuffer;

public interface ByteBufferPool {
    public ByteBuffer getByteBuffer(int var1);

    public void releaseByteBuffer(ByteBuffer var1);

    public int activeCount();
}

