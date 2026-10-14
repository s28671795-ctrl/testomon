/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.RandomAccessStore;
import org.h2.mvstore.SFChunk;
import org.h2.mvstore.cache.FilePathCache;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.encrypt.FileEncrypt;
import org.h2.store.fs.encrypt.FilePathEncrypt;
import org.h2.util.IOUtils;

public class SingleFileStore
extends RandomAccessStore {
    private FileChannel fileChannel;
    private FileChannel originalFileChannel;
    private FileLock fileLock;
    private final Map<String, Object> config;

    public SingleFileStore(Map<String, Object> map) {
        super(map);
        this.config = map;
    }

    public String toString() {
        return this.getFileName();
    }

    @Override
    public ByteBuffer readFully(SFChunk sFChunk, long l, int n) {
        return this.readFully(this.fileChannel, l, n);
    }

    @Override
    protected void writeFully(SFChunk sFChunk, long l, ByteBuffer byteBuffer) {
        int n = byteBuffer.remaining();
        this.setSize(Math.max(super.size(), l + (long)n));
        DataUtils.writeFully(this.fileChannel, l, byteBuffer);
        this.writeCount.incrementAndGet();
        this.writeBytes.addAndGet(n);
    }

    @Override
    public void open(String string, boolean bl, char[] cArray) {
        this.open(string, bl, cArray == null ? null : fileChannel -> new FileEncrypt(string, FilePathEncrypt.getPasswordBytes(cArray), (FileChannel)fileChannel));
    }

    public SingleFileStore open(String string, boolean bl) {
        SingleFileStore singleFileStore = new SingleFileStore(this.config);
        singleFileStore.open(string, bl, this.originalFileChannel == null ? null : fileChannel -> new FileEncrypt(string, (FileEncrypt)this.fileChannel, (FileChannel)fileChannel));
        return singleFileStore;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void open(String string, boolean bl, Function<FileChannel, FileChannel> function) {
        if (this.fileChannel != null && this.fileChannel.isOpen()) {
            return;
        }
        FilePathCache.INSTANCE.getScheme();
        FilePath filePath = FilePath.get(string);
        FilePath filePath2 = filePath.getParent();
        if (filePath2 != null && !filePath2.exists()) {
            throw DataUtils.newIllegalArgumentException("Directory does not exist: {0}", filePath2);
        }
        if (filePath.exists() && !filePath.canWrite()) {
            bl = true;
        }
        this.init(string, bl);
        try {
            this.fileChannel = filePath.open(bl ? "r" : "rw");
            if (function != null) {
                this.originalFileChannel = this.fileChannel;
                this.fileChannel = function.apply(this.fileChannel);
            }
            this.fileLock = this.lockFileChannel(this.fileChannel, bl, string);
            this.saveChunkLock.lock();
            try {
                this.setSize(this.fileChannel.size());
            }
            finally {
                this.saveChunkLock.unlock();
            }
        }
        catch (IOException iOException) {
            try {
                this.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw DataUtils.newMVStoreException(1, "Could not open file {0}", string, iOException);
        }
    }

    private FileLock lockFileChannel(FileChannel fileChannel, boolean bl, String string) throws IOException {
        FileLock fileLock;
        try {
            fileLock = fileChannel.tryLock(0L, Long.MAX_VALUE, bl);
        }
        catch (OverlappingFileLockException overlappingFileLockException) {
            throw DataUtils.newMVStoreException(7, "The file is locked: {0}", string, overlappingFileLockException);
        }
        if (fileLock == null) {
            try {
                this.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw DataUtils.newMVStoreException(7, "The file is locked: {0}", string);
        }
        return fileLock;
    }

    @Override
    public void close() {
        try {
            if (this.fileChannel.isOpen()) {
                if (this.fileLock != null) {
                    this.fileLock.release();
                }
                this.fileChannel.close();
            }
        }
        catch (Exception exception) {
            throw DataUtils.newMVStoreException(2, "Closing failed for file {0}", this.getFileName(), exception);
        }
        finally {
            this.fileLock = null;
            super.close();
        }
    }

    @Override
    public void sync() {
        if (this.fileChannel.isOpen()) {
            try {
                this.fileChannel.force(true);
            }
            catch (IOException iOException) {
                throw DataUtils.newMVStoreException(2, "Could not sync file {0}", this.getFileName(), iOException);
            }
        }
    }

    @Override
    public void truncate(long l) {
        int n = 0;
        while (true) {
            try {
                this.writeCount.incrementAndGet();
                this.fileChannel.truncate(l);
                this.setSize(Math.min(super.size(), l));
                return;
            }
            catch (IOException iOException) {
                if (++n == 10) {
                    throw DataUtils.newMVStoreException(2, "Could not truncate file {0} to size {1}", this.getFileName(), l, iOException);
                }
                System.gc();
                Thread.yield();
                continue;
            }
            break;
        }
    }

    @Override
    public int getMovePriority(int n) {
        return this.freeSpace.getMovePriority(n);
    }

    @Override
    protected long getAfterLastBlock_() {
        return this.freeSpace.getAfterLastBlock();
    }

    @Override
    public void backup(ZipOutputStream zipOutputStream) throws IOException {
        boolean bl = this.isSpaceReused();
        this.setReuseSpace(false);
        try {
            SingleFileStore.backupFile(zipOutputStream, this.getFileName(), this.originalFileChannel != null ? this.originalFileChannel : this.fileChannel);
        }
        finally {
            this.setReuseSpace(bl);
        }
    }

    private static void backupFile(ZipOutputStream zipOutputStream, String string, FileChannel fileChannel) throws IOException {
        String string2 = FilePath.get(string).toRealPath().getName();
        string2 = SingleFileStore.correctFileName(string2);
        zipOutputStream.putNextEntry(new ZipEntry(string2));
        IOUtils.copy(fileChannel, (OutputStream)zipOutputStream);
        zipOutputStream.closeEntry();
    }

    public static String correctFileName(String string) {
        if ((string = string.replace('\\', '/')).startsWith("/")) {
            string = string.substring(1);
        }
        return string;
    }
}

