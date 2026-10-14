/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class ContinuationFrame
extends Frame {
    private final int streamId;
    private final boolean endHeaders;

    public ContinuationFrame(int streamId, boolean endHeaders) {
        super(FrameType.CONTINUATION);
        this.streamId = streamId;
        this.endHeaders = endHeaders;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public boolean isEndHeaders() {
        return this.endHeaders;
    }

    @Override
    public String toString() {
        return String.format("%s#%d{end=%b}", super.toString(), this.getStreamId(), this.isEndHeaders());
    }
}

