/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class DataGenerator {
    private final HeaderGenerator headerGenerator;

    public DataGenerator(HeaderGenerator headerGenerator) {
        this.headerGenerator = headerGenerator;
    }

    public int generate(ByteBufferPool.Lease lease, DataFrame frame, int maxLength) {
        return this.generateData(lease, frame.getStreamId(), frame.getData(), frame.isEndStream(), maxLength);
    }

    public int generateData(ByteBufferPool.Lease lease, int streamId, ByteBuffer data, boolean last, int maxLength) {
        int maxFrameSize;
        if (streamId < 0) {
            throw new IllegalArgumentException("Invalid stream id: " + streamId);
        }
        int dataLength = data.remaining();
        int length = Math.min(dataLength, Math.min(maxFrameSize = this.headerGenerator.getMaxFrameSize(), maxLength));
        if (length == dataLength) {
            this.generateFrame(lease, streamId, data, last);
        } else {
            int limit = data.limit();
            int newLimit = data.position() + length;
            data.limit(newLimit);
            ByteBuffer slice = data.slice();
            data.position(newLimit);
            data.limit(limit);
            this.generateFrame(lease, streamId, slice, false);
        }
        return 9 + length;
    }

    private void generateFrame(ByteBufferPool.Lease lease, int streamId, ByteBuffer data, boolean last) {
        int length = data.remaining();
        int flags = 0;
        if (last) {
            flags |= 1;
        }
        ByteBuffer header = this.headerGenerator.generate(lease, FrameType.DATA, 9 + length, length, flags, streamId);
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        if (data.remaining() > 0) {
            lease.append(data, false);
        }
    }
}

