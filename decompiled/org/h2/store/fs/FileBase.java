/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public abstract class FileBase
extends FileChannel {
    @Override
    public synchronized int read(ByteBuffer byteBuffer, long l) throws IOException {
        long l2 = this.position();
        this.position(l);
        int n = this.read(byteBuffer);
        this.position(l2);
        return n;
    }

    @Override
    public synchronized int write(ByteBuffer byteBuffer, long l) throws IOException {
        long l2 = this.position();
        this.position(l);
        int n = this.write(byteBuffer);
        this.position(l2);
        return n;
    }

    @Override
    public void force(boolean bl) throws IOException {
    }

    @Override
    protected void implCloseChannel() throws IOException {
    }

    @Override
    public FileLock lock(long l, long l2, boolean bl) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public MappedByteBuffer map(FileChannel.MapMode mapMode, long l, long l2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long read(ByteBuffer[] byteBufferArray, int n, int n2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long transferFrom(ReadableByteChannel readableByteChannel, long l, long l2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long transferTo(long l, long l2, WritableByteChannel writableByteChannel) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long write(ByteBuffer[] byteBufferArray, int n, int n2) throws IOException {
        throw new UnsupportedOperationException();
    }
}

