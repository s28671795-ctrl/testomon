/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class WindowUpdateFrame
extends Frame {
    public static final int WINDOW_UPDATE_LENGTH = 4;
    private final int streamId;
    private final int windowDelta;

    public WindowUpdateFrame(int streamId, int windowDelta) {
        super(FrameType.WINDOW_UPDATE);
        this.streamId = streamId;
        this.windowDelta = windowDelta;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public int getWindowDelta() {
        return this.windowDelta;
    }

    @Override
    public String toString() {
        return String.format("%s#%d,delta=%d", super.toString(), this.streamId, this.windowDelta);
    }
}

