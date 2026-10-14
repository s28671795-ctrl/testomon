/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.io.ByteBufferPool;

public class NoOpGenerator
extends FrameGenerator {
    public NoOpGenerator() {
        super(null);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        return 0;
    }
}

