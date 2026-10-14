/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.PrefaceFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.io.ByteBufferPool;

public class PrefaceGenerator
extends FrameGenerator {
    public PrefaceGenerator() {
        super(null);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        lease.append(ByteBuffer.wrap(PrefaceFrame.PREFACE_BYTES), false);
        return PrefaceFrame.PREFACE_BYTES.length;
    }
}

