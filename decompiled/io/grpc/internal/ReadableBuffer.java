/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public interface ReadableBuffer
extends Closeable {
    public int readableBytes();

    public int readUnsignedByte();

    public int readInt();

    public void skipBytes(int var1);

    public void readBytes(byte[] var1, int var2, int var3);

    public void readBytes(ByteBuffer var1);

    public void readBytes(OutputStream var1, int var2) throws IOException;

    public ReadableBuffer readBytes(int var1);

    public boolean hasArray();

    public byte[] array();

    public int arrayOffset();

    default public void touch() {
    }

    public boolean markSupported();

    public void mark();

    public void reset();

    public boolean byteBufferSupported();

    @Nullable
    public ByteBuffer getByteBuffer();

    @Override
    public void close();
}

