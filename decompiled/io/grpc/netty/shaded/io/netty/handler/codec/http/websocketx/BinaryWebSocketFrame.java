/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class BinaryWebSocketFrame
extends WebSocketFrame {
    public BinaryWebSocketFrame() {
        super(Unpooled.buffer(0));
    }

    public BinaryWebSocketFrame(ByteBuf binaryData) {
        super(binaryData);
    }

    public BinaryWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }

    @Override
    public BinaryWebSocketFrame copy() {
        return (BinaryWebSocketFrame)super.copy();
    }

    @Override
    public BinaryWebSocketFrame duplicate() {
        return (BinaryWebSocketFrame)super.duplicate();
    }

    @Override
    public BinaryWebSocketFrame retainedDuplicate() {
        return (BinaryWebSocketFrame)super.retainedDuplicate();
    }

    @Override
    public BinaryWebSocketFrame replace(ByteBuf content) {
        return new BinaryWebSocketFrame(this.isFinalFragment(), this.rsv(), content);
    }

    @Override
    public BinaryWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryWebSocketFrame retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public BinaryWebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override
    public BinaryWebSocketFrame touch(Object hint) {
        super.touch(hint);
        return this;
    }
}

