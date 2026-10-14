/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

public interface WritableBuffer {
    public void write(byte[] var1, int var2, int var3);

    public void write(byte var1);

    public int writableBytes();

    public int readableBytes();

    public void release();
}

