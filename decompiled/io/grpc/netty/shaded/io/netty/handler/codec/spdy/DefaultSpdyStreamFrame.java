/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.spdy;

import io.grpc.netty.shaded.io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

public abstract class DefaultSpdyStreamFrame
implements SpdyStreamFrame {
    private int streamId;
    private boolean last;

    protected DefaultSpdyStreamFrame(int streamId) {
        this.setStreamId(streamId);
    }

    @Override
    public int streamId() {
        return this.streamId;
    }

    @Override
    public SpdyStreamFrame setStreamId(int streamId) {
        ObjectUtil.checkPositive(streamId, "streamId");
        this.streamId = streamId;
        return this;
    }

    @Override
    public boolean isLast() {
        return this.last;
    }

    @Override
    public SpdyStreamFrame setLast(boolean last) {
        this.last = last;
        return this;
    }
}

