/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.zip;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.h2.store.fs.FakeFileChannel;
import org.h2.store.fs.FileBase;
import org.h2.util.IOUtils;

class FileZip
extends FileBase {
    private static final byte[] SKIP_BUFFER = new byte[1024];
    private final ZipFile file;
    private final ZipEntry entry;
    private long pos;
    private InputStream in;
    private long inPos;
    private final long length;
    private boolean skipUsingRead;

    FileZip(ZipFile zipFile, ZipEntry zipEntry) {
        this.file = zipFile;
        this.entry = zipEntry;
        this.length = zipEntry.getSize();
    }

    @Override
    public long position() {
        return this.pos;
    }

    @Override
    public long size() {
        return this.length;
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        this.seek();
        int n = this.in.read(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
        if (n > 0) {
            byteBuffer.position(byteBuffer.position() + n);
            this.pos += (long)n;
            this.inPos += (long)n;
        }
        return n;
    }

    private void seek() throws IOException {
        if (this.inPos > this.pos) {
            if (this.in != null) {
                this.in.close();
            }
            this.in = null;
        }
        if (this.in == null) {
            this.in = this.file.getInputStream(this.entry);
            this.inPos = 0L;
        }
        if (this.inPos < this.pos) {
            long l = this.pos - this.inPos;
            if (!this.skipUsingRead) {
                try {
                    IOUtils.skipFully(this.in, l);
                }
                catch (NullPointerException nullPointerException) {
                    this.skipUsingRead = true;
                }
            }
            if (this.skipUsingRead) {
                while (l > 0L) {
                    int n = (int)Math.min((long)SKIP_BUFFER.length, l);
                    n = this.in.read(SKIP_BUFFER, 0, n);
                    l -= (long)n;
                }
            }
            this.inPos = this.pos;
        }
    }

    @Override
    public FileChannel position(long l) {
        this.pos = l;
        return this;
    }

    @Override
    public FileChannel truncate(long l) throws IOException {
        throw new IOException("File is read-only");
    }

    @Override
    public void force(boolean bl) throws IOException {
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        throw new NonWritableChannelException();
    }

    @Override
    public synchronized FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        if (bl) {
            return new FileLock(FakeFileChannel.INSTANCE, l, l2, bl){

                @Override
                public boolean isValid() {
                    return true;
                }

                @Override
                public void release() throws IOException {
                }
            };
        }
        return null;
    }

    @Override
    protected void implCloseChannel() throws IOException {
        if (this.in != null) {
            this.in.close();
            this.in = null;
        }
        this.file.close();
    }
}

