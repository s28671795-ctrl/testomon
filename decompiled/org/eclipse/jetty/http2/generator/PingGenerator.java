/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.PingFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class PingGenerator
extends FrameGenerator {
    public PingGenerator(HeaderGenerator headerGenerator) {
        super(headerGenerator);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        PingFrame pingFrame = (PingFrame)frame;
        return this.generatePing(lease, pingFrame.getPayload(), pingFrame.isReply());
    }

    public int generatePing(ByteBufferPool.Lease lease, byte[] payload, boolean reply) {
        if (payload.length != 8) {
            throw new IllegalArgumentException("Invalid payload length: " + payload.length);
        }
        ByteBuffer header = this.generateHeader(lease, FrameType.PING, 8, reply ? 1 : 0, 0);
        header.put(payload);
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        return 17;
    }
}

