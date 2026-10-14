/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.niomem;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import org.h2.store.fs.FakeFileChannel;
import org.h2.store.fs.FileBaseDefault;
import org.h2.store.fs.niomem.FileNioMemData;

class FileNioMem
extends FileBaseDefault {
    final FileNioMemData data;
    private final boolean readOnly;
    private volatile boolean closed;

    FileNioMem(FileNioMemData fileNioMemData, boolean bl) {
        this.data = fileNioMemData;
        this.readOnly = bl;
    }

    @Override
    public long size() {
        return this.data.length();
    }

    @Override
    protected void implTruncate(long l) throws IOException {
        if (this.readOnly) {
            throw new NonWritableChannelException();
        }
        if (this.closed) {
            throw new ClosedChannelException();
        }
        if (l < this.size()) {
            this.data.touch(this.readOnly);
            this.data.truncate(l);
        }
    }

    @Override
    public int write(ByteBuffer byteBuffer, long l) throws IOException {
        if (this.closed) {
            throw new ClosedChannelException();
        }
        this.data.touch(this.readOnly);
        long l2 = this.data.readWrite(l, byteBuffer, 0, byteBuffer.remaining(), true);
        int n = (int)(l2 - l);
        byteBuffer.position(byteBuffer.position() + n);
        return n;
    }

    @Override
    public int read(ByteBuffer byteBuffer, long l) throws IOException {
        if (this.closed) {
            throw new ClosedChannelException();
        }
        int n = byteBuffer.remaining();
        if (n == 0) {
            return 0;
        }
        long l2 = this.data.readWrite(l, byteBuffer, byteBuffer.position(), n, false);
        n = (int)(l2 - l);
        if (n <= 0) {
            return -1;
        }
        byteBuffer.position(byteBuffer.position() + n);
        return n;
    }

    @Override
    public void implCloseChannel() throws IOException {
        this.closed = true;
    }

    @Override
    public void force(boolean bl) throws IOException {
    }

    @Override
    public FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        if (this.closed) {
            throw new ClosedChannelException();
        }
        if (bl ? !this.data.lockShared() : !this.data.lockExclusive()) {
            return null;
        }
        return new FileLock(FakeFileChannel.INSTANCE, l, l2, bl){

            @Override
            public boolean isValid() {
                return true;
            }

            @Override
            public void release() throws IOException {
                FileNioMem.this.data.unlock();
            }
        };
    }

    public String toString() {
        return this.closed ? "<closed>" : this.data.getName();
    }
}

