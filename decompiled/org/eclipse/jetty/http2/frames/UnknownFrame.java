/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.Frame;

public class UnknownFrame
extends Frame {
    private final int frameType;

    public UnknownFrame(int frameType) {
        super(null);
        this.frameType = frameType;
    }

    @Override
    public String toString() {
        return String.format("%s,t=%d", super.toString(), this.frameType);
    }
}

