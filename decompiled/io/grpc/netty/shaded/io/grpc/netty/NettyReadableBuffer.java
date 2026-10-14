/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import io.grpc.internal.AbstractReadableBuffer;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

class NettyReadableBuffer
extends AbstractReadableBuffer {
    private final ByteBuf buffer;
    private boolean closed;

    NettyReadableBuffer(ByteBuf buffer) {
        this.buffer = Preconditions.checkNotNull(buffer, "buffer");
    }

    ByteBuf buffer() {
        return this.buffer;
    }

    @Override
    public int readableBytes() {
        return this.buffer.readableBytes();
    }

    @Override
    public void skipBytes(int length) {
        this.buffer.skipBytes(length);
    }

    @Override
    public int readUnsignedByte() {
        return this.buffer.readUnsignedByte();
    }

    @Override
    public void readBytes(byte[] dest, int index, int length) {
        this.buffer.readBytes(dest, index, length);
    }

    @Override
    public void readBytes(ByteBuffer dest) {
        this.buffer.readBytes(dest);
    }

    @Override
    public void readBytes(OutputStream dest, int length) {
        try {
            this.buffer.readBytes(dest, length);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NettyReadableBuffer readBytes(int length) {
        return new NettyReadableBuffer(this.buffer.readRetainedSlice(length));
    }

    @Override
    public boolean hasArray() {
        return this.buffer.hasArray();
    }

    @Override
    public byte[] array() {
        return this.buffer.array();
    }

    @Override
    public int arrayOffset() {
        return this.buffer.arrayOffset() + this.buffer.readerIndex();
    }

    @Override
    public void touch() {
        this.buffer.touch();
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark() {
        this.buffer.markReaderIndex();
    }

    @Override
    public void reset() {
        this.buffer.resetReaderIndex();
    }

    @Override
    public boolean byteBufferSupported() {
        return this.buffer.nioBufferCount() > 0;
    }

    @Override
    public ByteBuffer getByteBuffer() {
        return this.buffer.nioBufferCount() == 1 ? this.buffer.nioBuffer() : this.buffer.nioBuffers()[0];
    }

    @Override
    public void close() {
        if (!this.closed) {
            this.closed = true;
            this.buffer.release();
        }
    }
}

