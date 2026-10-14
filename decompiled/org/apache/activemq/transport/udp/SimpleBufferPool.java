/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.nio.ByteBuffer;
import org.apache.activemq.transport.udp.ByteBufferPool;

public class SimpleBufferPool
implements ByteBufferPool {
    private int defaultSize;
    private boolean useDirect;

    public SimpleBufferPool() {
        this(false);
    }

    public SimpleBufferPool(boolean useDirect) {
        this.useDirect = useDirect;
    }

    @Override
    public synchronized ByteBuffer borrowBuffer() {
        return this.createBuffer();
    }

    @Override
    public void returnBuffer(ByteBuffer buffer) {
    }

    @Override
    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public boolean isUseDirect() {
        return this.useDirect;
    }

    public void setUseDirect(boolean useDirect) {
        this.useDirect = useDirect;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    protected ByteBuffer createBuffer() {
        if (this.useDirect) {
            return ByteBuffer.allocateDirect(this.defaultSize);
        }
        return ByteBuffer.allocate(this.defaultSize);
    }
}

