/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.transport.udp.ByteBufferPool;
import org.apache.activemq.transport.udp.SimpleBufferPool;

public class DefaultBufferPool
extends SimpleBufferPool
implements ByteBufferPool {
    private List<ByteBuffer> buffers = new ArrayList<ByteBuffer>();
    private Object lock = new Object();

    public DefaultBufferPool() {
        super(true);
    }

    public DefaultBufferPool(boolean useDirect) {
        super(useDirect);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized ByteBuffer borrowBuffer() {
        Object object = this.lock;
        synchronized (object) {
            int size = this.buffers.size();
            if (size > 0) {
                return this.buffers.remove(size - 1);
            }
        }
        return this.createBuffer();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void returnBuffer(ByteBuffer buffer) {
        Object object = this.lock;
        synchronized (object) {
            this.buffers.add(buffer);
        }
    }

    @Override
    public void start() throws Exception {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws Exception {
        Object object = this.lock;
        synchronized (object) {
            this.buffers.clear();
        }
    }
}

