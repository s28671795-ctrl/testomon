/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.niomapped;

import java.io.EOFException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.file.Paths;
import org.h2.engine.SysProperties;
import org.h2.store.fs.FileBaseDefault;
import org.h2.store.fs.FileUtils;
import org.h2.util.MemoryUnmapper;

class FileNioMapped
extends FileBaseDefault {
    private static final int GC_TIMEOUT_MS = 10000;
    private final String name;
    private final FileChannel.MapMode mode;
    private FileChannel channel;
    private MappedByteBuffer mapped;
    private long fileLength;

    FileNioMapped(String string, String string2) throws IOException {
        this.mode = "r".equals(string2) ? FileChannel.MapMode.READ_ONLY : FileChannel.MapMode.READ_WRITE;
        this.name = string;
        this.channel = FileChannel.open(Paths.get(string, new String[0]), FileUtils.modeToOptions(string2), FileUtils.NO_ATTRIBUTES);
        this.reMap();
    }

    private void unMap() throws IOException {
        if (this.mapped == null) {
            return;
        }
        this.mapped.force();
        if (SysProperties.NIO_CLEANER_HACK && MemoryUnmapper.unmap(this.mapped)) {
            this.mapped = null;
            return;
        }
        WeakReference<MappedByteBuffer> weakReference = new WeakReference<MappedByteBuffer>(this.mapped);
        this.mapped = null;
        long l = System.nanoTime() + 10000000000L;
        while (weakReference.get() != null) {
            if (System.nanoTime() - l > 0L) {
                throw new IOException("Timeout (10000 ms) reached while trying to GC mapped buffer");
            }
            System.gc();
            Thread.yield();
        }
    }

    private void reMap() throws IOException {
        if (this.mapped != null) {
            this.unMap();
        }
        this.fileLength = this.channel.size();
        FileNioMapped.checkFileSizeLimit(this.fileLength);
        this.mapped = this.channel.map(this.mode, 0L, this.fileLength);
        int n = this.mapped.limit();
        int n2 = this.mapped.capacity();
        if ((long)n < this.fileLength || (long)n2 < this.fileLength) {
            throw new IOException("Unable to map: length=" + n + " capacity=" + n2 + " length=" + this.fileLength);
        }
        if (SysProperties.NIO_LOAD_MAPPED) {
            this.mapped.load();
        }
    }

    private static void checkFileSizeLimit(long l) throws IOException {
        if (l > Integer.MAX_VALUE) {
            throw new IOException("File over 2GB is not supported yet when using this file system");
        }
    }

    @Override
    public void implCloseChannel() throws IOException {
        if (this.channel != null) {
            this.unMap();
            this.channel.close();
            this.channel = null;
        }
    }

    public String toString() {
        return "nioMapped:" + this.name;
    }

    @Override
    public synchronized long size() throws IOException {
        return this.fileLength;
    }

    @Override
    public synchronized int read(ByteBuffer byteBuffer, long l) throws IOException {
        FileNioMapped.checkFileSizeLimit(l);
        try {
            int n = byteBuffer.remaining();
            if (n == 0) {
                return 0;
            }
            if ((n = (int)Math.min((long)n, this.fileLength - l)) <= 0) {
                return -1;
            }
            this.mapped.position((int)l);
            this.mapped.get(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), n);
            byteBuffer.position(byteBuffer.position() + n);
            l += (long)n;
            return n;
        }
        catch (IllegalArgumentException | BufferUnderflowException runtimeException) {
            EOFException eOFException = new EOFException("EOF");
            eOFException.initCause(runtimeException);
            throw eOFException;
        }
    }

    @Override
    protected void implTruncate(long l) throws IOException {
        if (this.mode == FileChannel.MapMode.READ_ONLY) {
            throw new NonWritableChannelException();
        }
        if (l < this.size()) {
            this.setFileLength(l);
        }
    }

    public synchronized void setFileLength(long l) throws IOException {
        if (this.mode == FileChannel.MapMode.READ_ONLY) {
            throw new NonWritableChannelException();
        }
        FileNioMapped.checkFileSizeLimit(l);
        this.unMap();
        int n = 0;
        while (true) {
            try {
                long l2 = this.channel.size();
                if (l2 >= l) {
                    this.channel.truncate(l);
                    break;
                }
                this.channel.write(ByteBuffer.wrap(new byte[1]), l - 1L);
            }
            catch (IOException iOException) {
                if (n > 16 || !iOException.toString().contains("user-mapped section open")) {
                    throw iOException;
                }
                System.gc();
                ++n;
                continue;
            }
            break;
        }
        this.reMap();
    }

    @Override
    public void force(boolean bl) throws IOException {
        this.mapped.force();
        this.channel.force(bl);
    }

    @Override
    public synchronized int write(ByteBuffer byteBuffer, long l) throws IOException {
        FileNioMapped.checkFileSizeLimit(l);
        int n = byteBuffer.remaining();
        if ((long)this.mapped.capacity() < l + (long)n) {
            this.setFileLength(l + (long)n);
        }
        this.mapped.position((int)l);
        this.mapped.put(byteBuffer);
        return n;
    }

    @Override
    public synchronized FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        return this.channel.tryLock(l, l2, bl);
    }
}

