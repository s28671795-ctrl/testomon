/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.h2.store.fs.FileBase;

public abstract class FileBaseDefault
extends FileBase {
    private long position = 0L;

    @Override
    public final synchronized long position() throws IOException {
        return this.position;
    }

    @Override
    public final synchronized FileChannel position(long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException();
        }
        this.position = l;
        return this;
    }

    @Override
    public final synchronized int read(ByteBuffer byteBuffer) throws IOException {
        int n = this.read(byteBuffer, this.position);
        if (n > 0) {
            this.position += (long)n;
        }
        return n;
    }

    @Override
    public final synchronized int write(ByteBuffer byteBuffer) throws IOException {
        int n = this.write(byteBuffer, this.position);
        if (n > 0) {
            this.position += (long)n;
        }
        return n;
    }

    @Override
    public final synchronized FileChannel truncate(long l) throws IOException {
        this.implTruncate(l);
        if (l < this.position) {
            this.position = l;
        }
        return this;
    }

    protected abstract void implTruncate(long var1) throws IOException;
}

