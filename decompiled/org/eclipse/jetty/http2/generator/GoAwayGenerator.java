/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.GoAwayFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class GoAwayGenerator
extends FrameGenerator {
    public GoAwayGenerator(HeaderGenerator headerGenerator) {
        super(headerGenerator);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        GoAwayFrame goAwayFrame = (GoAwayFrame)frame;
        return this.generateGoAway(lease, goAwayFrame.getLastStreamId(), goAwayFrame.getError(), goAwayFrame.getPayload());
    }

    public int generateGoAway(ByteBufferPool.Lease lease, int lastStreamId, int error, byte[] payload) {
        if (lastStreamId < 0) {
            lastStreamId = 0;
        }
        int fixedLength = 8;
        int maxPayloadLength = 16384 - fixedLength;
        if (payload != null && payload.length > maxPayloadLength) {
            payload = Arrays.copyOfRange(payload, 0, maxPayloadLength);
        }
        int length = fixedLength + (payload != null ? payload.length : 0);
        ByteBuffer header = this.generateHeader(lease, FrameType.GO_AWAY, length, 0, 0);
        header.putInt(lastStreamId);
        header.putInt(error);
        if (payload != null) {
            header.put(payload);
        }
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        return 9 + length;
    }
}

