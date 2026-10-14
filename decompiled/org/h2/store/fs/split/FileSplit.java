/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.split;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.h2.message.DbException;
import org.h2.mvstore.DataUtils;
import org.h2.store.fs.FileBaseDefault;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.split.FilePathSplit;

class FileSplit
extends FileBaseDefault {
    private final FilePathSplit filePath;
    private final String mode;
    private final long maxLength;
    private FileChannel[] list;
    private volatile long length;

    FileSplit(FilePathSplit filePathSplit, String string, FileChannel[] fileChannelArray, long l, long l2) {
        this.filePath = filePathSplit;
        this.mode = string;
        this.list = fileChannelArray;
        this.length = l;
        this.maxLength = l2;
    }

    @Override
    public synchronized void implCloseChannel() throws IOException {
        for (FileChannel fileChannel : this.list) {
            fileChannel.close();
        }
    }

    @Override
    public long size() {
        return this.length;
    }

    @Override
    public synchronized int read(ByteBuffer byteBuffer, long l) throws IOException {
        int n = byteBuffer.remaining();
        if (n == 0) {
            return 0;
        }
        if ((n = (int)Math.min((long)n, this.length - l)) <= 0) {
            return -1;
        }
        long l2 = l % this.maxLength;
        n = (int)Math.min((long)n, this.maxLength - l2);
        FileChannel fileChannel = this.getFileChannel(l);
        return fileChannel.read(byteBuffer, l2);
    }

    private FileChannel getFileChannel(long l) throws IOException {
        int n = (int)(l / this.maxLength);
        while (n >= this.list.length) {
            int n2 = this.list.length;
            FileChannel[] fileChannelArray = new FileChannel[n2 + 1];
            System.arraycopy(this.list, 0, fileChannelArray, 0, n2);
            FilePath filePath = this.filePath.getBase(n2);
            fileChannelArray[n2] = filePath.open(this.mode);
            this.list = fileChannelArray;
        }
        return this.list[n];
    }

    @Override
    protected void implTruncate(long l) throws IOException {
        if (l >= this.length) {
            return;
        }
        int n = 1 + (int)(l / this.maxLength);
        if (n < this.list.length) {
            FileChannel[] fileChannelArray = new FileChannel[n];
            for (int i = this.list.length - 1; i >= n; --i) {
                this.list[i].truncate(0L);
                this.list[i].close();
                try {
                    this.filePath.getBase(i).delete();
                    continue;
                }
                catch (DbException dbException) {
                    throw DataUtils.convertToIOException(dbException);
                }
            }
            System.arraycopy(this.list, 0, fileChannelArray, 0, fileChannelArray.length);
            this.list = fileChannelArray;
        }
        long l2 = l - this.maxLength * (long)(n - 1);
        this.list[this.list.length - 1].truncate(l2);
        this.length = l;
    }

    @Override
    public synchronized void force(boolean bl) throws IOException {
        for (FileChannel fileChannel : this.list) {
            fileChannel.force(bl);
        }
    }

    @Override
    public synchronized int write(ByteBuffer byteBuffer, long l) throws IOException {
        long l2;
        if (l >= this.length && l > this.maxLength) {
            l2 = l;
            for (long i = this.length - this.length % this.maxLength + this.maxLength; i < l; i += this.maxLength) {
                if (i > this.length) {
                    this.position(i - 1L);
                    this.write(ByteBuffer.wrap(new byte[1]));
                }
                l = l2;
            }
        }
        l2 = l % this.maxLength;
        int n = byteBuffer.remaining();
        FileChannel fileChannel = this.getFileChannel(l);
        int n2 = (int)Math.min((long)n, this.maxLength - l2);
        if (n2 == n) {
            n2 = fileChannel.write(byteBuffer, l2);
        } else {
            int n3 = byteBuffer.limit();
            byteBuffer.limit(byteBuffer.position() + n2);
            n2 = fileChannel.write(byteBuffer, l2);
            byteBuffer.limit(n3);
        }
        this.length = Math.max(this.length, l + (long)n2);
        return n2;
    }

    @Override
    public synchronized FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        return this.list[0].tryLock(l, l2, bl);
    }

    public String toString() {
        return this.filePath.toString();
    }
}

