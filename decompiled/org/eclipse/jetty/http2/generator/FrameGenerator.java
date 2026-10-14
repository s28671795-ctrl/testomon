/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.http2.hpack.HpackEncoder;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.io.ByteBufferPool;

public abstract class FrameGenerator {
    private final HeaderGenerator headerGenerator;

    protected FrameGenerator(HeaderGenerator headerGenerator) {
        this.headerGenerator = headerGenerator;
    }

    public abstract int generate(ByteBufferPool.Lease var1, Frame var2) throws HpackException;

    protected ByteBuffer generateHeader(ByteBufferPool.Lease lease, FrameType frameType, int length, int flags, int streamId) {
        return this.headerGenerator.generate(lease, frameType, 9 + length, length, flags, streamId);
    }

    public int getMaxFrameSize() {
        return this.headerGenerator.getMaxFrameSize();
    }

    public boolean isUseDirectByteBuffers() {
        return this.headerGenerator.isUseDirectByteBuffers();
    }

    protected ByteBuffer encode(HpackEncoder encoder, ByteBufferPool.Lease lease, MetaData metaData, int maxFrameSize) throws HpackException {
        ByteBuffer hpacked = lease.acquire(maxFrameSize, this.isUseDirectByteBuffers());
        try {
            encoder.encode(hpacked, metaData);
            return hpacked;
        }
        catch (HpackException x) {
            lease.release(hpacked);
            throw x;
        }
    }
}

