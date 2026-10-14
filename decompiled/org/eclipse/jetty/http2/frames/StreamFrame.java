/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public abstract class StreamFrame
extends Frame {
    private final int streamId;

    public StreamFrame(FrameType type, int streamId) {
        super(type);
        this.streamId = streamId;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public abstract StreamFrame withStreamId(int var1);
}

