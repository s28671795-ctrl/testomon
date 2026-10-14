/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.internal.ReadableBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public abstract class ForwardingReadableBuffer
implements ReadableBuffer {
    private final ReadableBuffer buf;

    protected ForwardingReadableBuffer(ReadableBuffer buf) {
        this.buf = Preconditions.checkNotNull(buf, "buf");
    }

    @Override
    public int readableBytes() {
        return this.buf.readableBytes();
    }

    @Override
    public int readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }

    @Override
    public int readInt() {
        return this.buf.readInt();
    }

    @Override
    public void skipBytes(int length) {
        this.buf.skipBytes(length);
    }

    @Override
    public void readBytes(byte[] dest, int destOffset, int length) {
        this.buf.readBytes(dest, destOffset, length);
    }

    @Override
    public void readBytes(ByteBuffer dest) {
        this.buf.readBytes(dest);
    }

    @Override
    public void readBytes(OutputStream dest, int length) throws IOException {
        this.buf.readBytes(dest, length);
    }

    @Override
    public ReadableBuffer readBytes(int length) {
        return this.buf.readBytes(length);
    }

    @Override
    public boolean hasArray() {
        return this.buf.hasArray();
    }

    @Override
    public byte[] array() {
        return this.buf.array();
    }

    @Override
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }

    @Override
    public void touch() {
        this.buf.touch();
    }

    @Override
    public boolean markSupported() {
        return this.buf.markSupported();
    }

    @Override
    public void mark() {
        this.buf.mark();
    }

    @Override
    public void reset() {
        this.buf.reset();
    }

    @Override
    public boolean byteBufferSupported() {
        return this.buf.byteBufferSupported();
    }

    @Override
    @Nullable
    public ByteBuffer getByteBuffer() {
        return this.buf.getByteBuffer();
    }

    @Override
    public void close() {
        this.buf.close();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.buf).toString();
    }
}

