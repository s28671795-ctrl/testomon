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

public class FakeFileChannel
extends FileChannel {
    public static final FakeFileChannel INSTANCE = new FakeFileChannel();

    private FakeFileChannel() {
    }

    @Override
    protected void implCloseChannel() throws IOException {
        throw new IOException();
    }

    @Override
    public FileLock lock(long l, long l2, boolean bl) throws IOException {
        throw new IOException();
    }

    @Override
    public MappedByteBuffer map(FileChannel.MapMode mapMode, long l, long l2) throws IOException {
        throw new IOException();
    }

    @Override
    public long position() throws IOException {
        throw new IOException();
    }

    @Override
    public FileChannel position(long l) throws IOException {
        throw new IOException();
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        throw new IOException();
    }

    @Override
    public int read(ByteBuffer byteBuffer, long l) throws IOException {
        throw new IOException();
    }

    @Override
    public long read(ByteBuffer[] byteBufferArray, int n, int n2) throws IOException {
        throw new IOException();
    }

    @Override
    public long size() throws IOException {
        throw new IOException();
    }

    @Override
    public long transferFrom(ReadableByteChannel readableByteChannel, long l, long l2) throws IOException {
        throw new IOException();
    }

    @Override
    public long transferTo(long l, long l2, WritableByteChannel writableByteChannel) throws IOException {
        throw new IOException();
    }

    @Override
    public FileChannel truncate(long l) throws IOException {
        throw new IOException();
    }

    @Override
    public FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        throw new IOException();
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        throw new IOException();
    }

    @Override
    public int write(ByteBuffer byteBuffer, long l) throws IOException {
        throw new IOException();
    }

    @Override
    public long write(ByteBuffer[] byteBufferArray, int n, int n2) throws IOException {
        throw new IOException();
    }

    @Override
    public void force(boolean bl) throws IOException {
        throw new IOException();
    }
}

