/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.rec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import org.h2.store.fs.FileBase;
import org.h2.store.fs.rec.FilePathRec;

class FileRec
extends FileBase {
    private final FilePathRec rec;
    private final FileChannel channel;
    private final String name;

    FileRec(FilePathRec filePathRec, FileChannel fileChannel, String string) {
        this.rec = filePathRec;
        this.channel = fileChannel;
        this.name = string;
    }

    @Override
    public void implCloseChannel() throws IOException {
        this.channel.close();
    }

    @Override
    public long position() throws IOException {
        return this.channel.position();
    }

    @Override
    public long size() throws IOException {
        return this.channel.size();
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        return this.channel.read(byteBuffer);
    }

    @Override
    public int read(ByteBuffer byteBuffer, long l) throws IOException {
        return this.channel.read(byteBuffer, l);
    }

    @Override
    public FileChannel position(long l) throws IOException {
        this.channel.position(l);
        return this;
    }

    @Override
    public FileChannel truncate(long l) throws IOException {
        this.rec.log(7, this.name, null, l);
        this.channel.truncate(l);
        return this;
    }

    @Override
    public void force(boolean bl) throws IOException {
        this.channel.force(bl);
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        int n;
        byte[] byArray = byteBuffer.array();
        int n2 = byteBuffer.remaining();
        if (byteBuffer.position() != 0 || n2 != byArray.length) {
            n = byteBuffer.arrayOffset() + byteBuffer.position();
            byArray = Arrays.copyOfRange(byArray, n, n + n2);
        }
        n = this.channel.write(byteBuffer);
        this.rec.log(8, this.name, byArray, this.channel.position());
        return n;
    }

    @Override
    public int write(ByteBuffer byteBuffer, long l) throws IOException {
        int n;
        byte[] byArray = byteBuffer.array();
        int n2 = byteBuffer.remaining();
        if (byteBuffer.position() != 0 || n2 != byArray.length) {
            n = byteBuffer.arrayOffset() + byteBuffer.position();
            byArray = Arrays.copyOfRange(byArray, n, n + n2);
        }
        n = this.channel.write(byteBuffer, l);
        this.rec.log(8, this.name, byArray, l);
        return n;
    }

    @Override
    public synchronized FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        return this.channel.tryLock(l, l2, bl);
    }

    public String toString() {
        return this.name;
    }
}

