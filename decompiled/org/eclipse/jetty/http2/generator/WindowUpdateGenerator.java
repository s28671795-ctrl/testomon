/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class WindowUpdateGenerator
extends FrameGenerator {
    public WindowUpdateGenerator(HeaderGenerator headerGenerator) {
        super(headerGenerator);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        WindowUpdateFrame windowUpdateFrame = (WindowUpdateFrame)frame;
        return this.generateWindowUpdate(lease, windowUpdateFrame.getStreamId(), windowUpdateFrame.getWindowDelta());
    }

    public int generateWindowUpdate(ByteBufferPool.Lease lease, int streamId, int windowUpdate) {
        if (windowUpdate < 0) {
            throw new IllegalArgumentException("Invalid window update: " + windowUpdate);
        }
        ByteBuffer header = this.generateHeader(lease, FrameType.WINDOW_UPDATE, 4, 0, streamId);
        header.putInt(windowUpdate);
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        return 13;
    }
}

