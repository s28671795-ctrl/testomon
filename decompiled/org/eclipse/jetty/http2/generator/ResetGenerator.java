/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class ResetGenerator
extends FrameGenerator {
    public ResetGenerator(HeaderGenerator headerGenerator) {
        super(headerGenerator);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        ResetFrame resetFrame = (ResetFrame)frame;
        return this.generateReset(lease, resetFrame.getStreamId(), resetFrame.getError());
    }

    public int generateReset(ByteBufferPool.Lease lease, int streamId, int error) {
        if (streamId < 0) {
            throw new IllegalArgumentException("Invalid stream id: " + streamId);
        }
        ByteBuffer header = this.generateHeader(lease, FrameType.RST_STREAM, 4, 0, streamId);
        header.putInt(error);
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        return 13;
    }
}

