/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.io;

import java.nio.ByteBuffer;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class NullByteBufferPool
implements ByteBufferPool {
    private final RetainableByteBufferPool _retainableByteBufferPool = RetainableByteBufferPool.from(this);

    @Override
    public ByteBuffer acquire(int size, boolean direct) {
        if (direct) {
            return BufferUtil.allocateDirect(size);
        }
        return BufferUtil.allocate(size);
    }

    @Override
    public void release(ByteBuffer buffer) {
        BufferUtil.clear(buffer);
    }

    @Override
    public RetainableByteBufferPool asRetainableByteBufferPool() {
        return this._retainableByteBufferPool;
    }
}

