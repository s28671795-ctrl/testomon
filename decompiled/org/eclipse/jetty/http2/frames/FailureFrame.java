/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;

public class FailureFrame
extends Frame {
    private final int error;
    private final String reason;
    private final Throwable failure;

    public FailureFrame(int error, String reason, Throwable failure) {
        super(FrameType.FAILURE);
        this.error = error;
        this.reason = reason;
        this.failure = failure;
    }

    public int getError() {
        return this.error;
    }

    public String getReason() {
        return this.reason;
    }

    public Throwable getFailure() {
        return this.failure;
    }
}

