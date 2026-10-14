/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class ResetFrame
extends Frame {
    public static final int RESET_LENGTH = 4;
    private final int streamId;
    private final int error;

    public ResetFrame(int streamId, int error) {
        super(FrameType.RST_STREAM);
        this.streamId = streamId;
        this.error = error;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public int getError() {
        return this.error;
    }

    @Override
    public String toString() {
        return String.format("%s#%d{%s}", super.toString(), this.streamId, ErrorCode.toString(this.error, null));
    }
}

