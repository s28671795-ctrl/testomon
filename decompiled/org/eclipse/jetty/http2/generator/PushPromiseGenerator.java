/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.http2.hpack.HpackEncoder;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class PushPromiseGenerator
extends FrameGenerator {
    private final HpackEncoder encoder;

    public PushPromiseGenerator(HeaderGenerator headerGenerator, HpackEncoder encoder) {
        super(headerGenerator);
        this.encoder = encoder;
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) throws HpackException {
        PushPromiseFrame pushPromiseFrame = (PushPromiseFrame)frame;
        return this.generatePushPromise(lease, pushPromiseFrame.getStreamId(), pushPromiseFrame.getPromisedStreamId(), pushPromiseFrame.getMetaData());
    }

    public int generatePushPromise(ByteBufferPool.Lease lease, int streamId, int promisedStreamId, MetaData metaData) throws HpackException {
        if (streamId < 0) {
            throw new IllegalArgumentException("Invalid stream id: " + streamId);
        }
        if (promisedStreamId < 0) {
            throw new IllegalArgumentException("Invalid promised stream id: " + promisedStreamId);
        }
        int maxFrameSize = this.getMaxFrameSize();
        int extraSpace = 4;
        ByteBuffer hpacked = this.encode(this.encoder, lease, metaData, maxFrameSize -= extraSpace);
        int hpackedLength = hpacked.position();
        BufferUtil.flipToFlush(hpacked, 0);
        int length = hpackedLength + extraSpace;
        int flags = 4;
        ByteBuffer header = this.generateHeader(lease, FrameType.PUSH_PROMISE, length, flags, streamId);
        header.putInt(promisedStreamId);
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        lease.append(hpacked, true);
        return 9 + length;
    }
}

