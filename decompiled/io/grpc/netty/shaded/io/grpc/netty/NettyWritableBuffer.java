/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.internal.WritableBuffer;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;

class NettyWritableBuffer
implements WritableBuffer {
    private final ByteBuf bytebuf;

    NettyWritableBuffer(ByteBuf bytebuf) {
        this.bytebuf = bytebuf;
    }

    @Override
    public void write(byte[] src, int srcIndex, int length) {
        this.bytebuf.writeBytes(src, srcIndex, length);
    }

    @Override
    public void write(byte b) {
        this.bytebuf.writeByte(b);
    }

    @Override
    public int writableBytes() {
        return this.bytebuf.writableBytes();
    }

    @Override
    public int readableBytes() {
        return this.bytebuf.readableBytes();
    }

    @Override
    public void release() {
        this.bytebuf.release();
    }

    ByteBuf bytebuf() {
        return this.bytebuf;
    }
}

