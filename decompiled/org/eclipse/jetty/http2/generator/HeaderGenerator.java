/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.io.ByteBufferPool;

public class HeaderGenerator {
    private int maxFrameSize = 16384;
    private final boolean useDirectByteBuffers;

    public HeaderGenerator() {
        this(true);
    }

    public HeaderGenerator(boolean useDirectByteBuffers) {
        this.useDirectByteBuffers = useDirectByteBuffers;
    }

    public boolean isUseDirectByteBuffers() {
        return this.useDirectByteBuffers;
    }

    public ByteBuffer generate(ByteBufferPool.Lease lease, FrameType frameType, int capacity, int length, int flags, int streamId) {
        ByteBuffer header = lease.acquire(capacity, this.isUseDirectByteBuffers());
        header.put((byte)((length & 0xFF0000) >>> 16));
        header.put((byte)((length & 0xFF00) >>> 8));
        header.put((byte)(length & 0xFF));
        header.put((byte)frameType.getType());
        header.put((byte)flags);
        header.putInt(streamId);
        return header;
    }

    public int getMaxFrameSize() {
        return this.maxFrameSize;
    }

    public void setMaxFrameSize(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }
}

