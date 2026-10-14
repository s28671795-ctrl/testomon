/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.retry;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.h2.store.fs.FileBase;
import org.h2.store.fs.FileUtils;

class FileRetryOnInterrupt
extends FileBase {
    private final String fileName;
    private final String mode;
    private FileChannel channel;
    private FileLockRetry lock;

    FileRetryOnInterrupt(String string, String string2) throws IOException {
        this.fileName = string;
        this.mode = string2;
        this.open();
    }

    private void open() throws IOException {
        this.channel = FileUtils.open(this.fileName, this.mode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void reopen(int n, IOException iOException) throws IOException {
        if (n > 20) {
            throw iOException;
        }
        if (!(iOException instanceof ClosedByInterruptException) && !(iOException instanceof ClosedChannelException)) {
            throw iOException;
        }
        Thread.interrupted();
        FileChannel fileChannel = this.channel;
        FileRetryOnInterrupt fileRetryOnInterrupt = this;
        synchronized (fileRetryOnInterrupt) {
            if (fileChannel == this.channel) {
                this.open();
                this.reLock();
            }
        }
    }

    private void reLock() throws IOException {
        if (this.lock == null) {
            return;
        }
        try {
            this.lock.base.release();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        FileLock fileLock = this.channel.tryLock(this.lock.position(), this.lock.size(), this.lock.isShared());
        if (fileLock == null) {
            throw new IOException("Re-locking failed");
        }
        this.lock.base = fileLock;
    }

    @Override
    public void implCloseChannel() throws IOException {
        try {
            this.channel.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public long position() throws IOException {
        int n = 0;
        while (true) {
            try {
                return this.channel.position();
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public long size() throws IOException {
        int n = 0;
        while (true) {
            try {
                return this.channel.size();
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        long l = this.position();
        int n = 0;
        while (true) {
            try {
                return this.channel.read(byteBuffer);
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                this.position(l);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public int read(ByteBuffer byteBuffer, long l) throws IOException {
        int n = 0;
        while (true) {
            try {
                return this.channel.read(byteBuffer, l);
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public FileChannel position(long l) throws IOException {
        int n = 0;
        while (true) {
            try {
                this.channel.position(l);
                return this;
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public FileChannel truncate(long l) throws IOException {
        int n = 0;
        while (true) {
            try {
                this.channel.truncate(l);
                return this;
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public void force(boolean bl) throws IOException {
        int n = 0;
        while (true) {
            try {
                this.channel.force(bl);
                return;
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        long l = this.position();
        int n = 0;
        while (true) {
            try {
                return this.channel.write(byteBuffer);
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                this.position(l);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public int write(ByteBuffer byteBuffer, long l) throws IOException {
        int n = 0;
        while (true) {
            try {
                return this.channel.write(byteBuffer, l);
            }
            catch (IOException iOException) {
                this.reopen(n, iOException);
                ++n;
                continue;
            }
            break;
        }
    }

    @Override
    public synchronized FileLock tryLock(long l, long l2, boolean bl) throws IOException {
        FileLock fileLock = this.channel.tryLock(l, l2, bl);
        if (fileLock == null) {
            return null;
        }
        this.lock = new FileLockRetry(fileLock, this);
        return this.lock;
    }

    public String toString() {
        return "retry:" + this.fileName;
    }

    static class FileLockRetry
    extends FileLock {
        FileLock base;

        protected FileLockRetry(FileLock fileLock, FileChannel fileChannel) {
            super(fileChannel, fileLock.position(), fileLock.size(), fileLock.isShared());
            this.base = fileLock;
        }

        @Override
        public boolean isValid() {
            return this.base.isValid();
        }

        @Override
        public void release() throws IOException {
            this.base.release();
        }
    }
}

