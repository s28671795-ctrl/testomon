/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.ReadableBuffer;
import java.nio.ByteBuffer;

public abstract class AbstractReadableBuffer
implements ReadableBuffer {
    @Override
    public final int readInt() {
        this.checkReadable(4);
        int b1 = this.readUnsignedByte();
        int b2 = this.readUnsignedByte();
        int b3 = this.readUnsignedByte();
        int b4 = this.readUnsignedByte();
        return b1 << 24 | b2 << 16 | b3 << 8 | b4;
    }

    @Override
    public boolean hasArray() {
        return false;
    }

    @Override
    public byte[] array() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void mark() {
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean byteBufferSupported() {
        return false;
    }

    @Override
    public ByteBuffer getByteBuffer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
    }

    protected final void checkReadable(int length) {
        if (this.readableBytes() < length) {
            throw new IndexOutOfBoundsException();
        }
    }
}

