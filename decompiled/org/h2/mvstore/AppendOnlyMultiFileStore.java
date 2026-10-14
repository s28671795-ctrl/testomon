/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.ZipOutputStream;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.FileStore;
import org.h2.mvstore.MFChunk;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.cache.FilePathCache;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.encrypt.FileEncrypt;
import org.h2.store.fs.encrypt.FilePathEncrypt;

public final class AppendOnlyMultiFileStore
extends FileStore<MFChunk> {
    private final int maxFileCount;
    private long creationTime;
    private int volumeId;
    private int fileCount;
    private FileChannel fileChannel;
    private FileChannel originalFileChannel;
    private final FileChannel[] fileChannels;
    private FileLock fileLock;
    private final Map<String, Object> config;

    public AppendOnlyMultiFileStore(Map<String, Object> map) {
        super(map);
        this.config = map;
        this.maxFileCount = DataUtils.getConfigParam(map, "maxFileCount", 16);
        this.fileChannels = new FileChannel[this.maxFileCount];
    }

    @Override
    protected final MFChunk createChunk(int n) {
        return new MFChunk(n);
    }

    @Override
    public MFChunk createChunk(String string) {
        return new MFChunk(string);
    }

    @Override
    protected MFChunk createChunk(Map<String, String> map) {
        return new MFChunk(map);
    }

    @Override
    public boolean shouldSaveNow(int n, int n2) {
        return n > n2;
    }

    @Override
    public void open(String string, boolean bl, char[] cArray) {
        this.open(string, bl, cArray == null ? null : fileChannel -> new FileEncrypt(string, FilePathEncrypt.getPasswordBytes(cArray), (FileChannel)fileChannel));
    }

    public AppendOnlyMultiFileStore open(String string, boolean bl) {
        AppendOnlyMultiFileStore appendOnlyMultiFileStore = new AppendOnlyMultiFileStore(this.config);
        appendOnlyMultiFileStore.open(string, bl, this.originalFileChannel == null ? null : fileChannel -> new FileEncrypt(string, (FileEncrypt)this.fileChannel, (FileChannel)fileChannel));
        return appendOnlyMultiFileStore;
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
            try {
                this.fileLock = this.fileChannel.tryLock(0L, Long.MAX_VALUE, bl);
            }
            catch (OverlappingFileLockException overlappingFileLockException) {
                throw DataUtils.newMVStoreException(7, "The file is locked: {0}", string, overlappingFileLockException);
            }
            if (this.fileLock == null) {
                try {
                    this.close();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                throw DataUtils.newMVStoreException(7, "The file is locked: {0}", string);
            }
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
    protected void writeFully(MFChunk mFChunk, long l, ByteBuffer byteBuffer) {
        assert (mFChunk.volumeId == this.volumeId);
        int n = byteBuffer.remaining();
        this.setSize(Math.max(super.size(), l + (long)n));
        DataUtils.writeFully(this.fileChannels[this.volumeId], l, byteBuffer);
        this.writeCount.incrementAndGet();
        this.writeBytes.addAndGet(n);
    }

    @Override
    public ByteBuffer readFully(MFChunk mFChunk, long l, int n) {
        int n2 = mFChunk.volumeId;
        return this.readFully(this.fileChannels[n2], l, n);
    }

    @Override
    protected void initializeStoreHeader(long l) {
    }

    @Override
    protected void readStoreHeader(boolean bl) {
        ByteBuffer byteBuffer = this.readFully(new MFChunk(""), 0L, 4096);
        byte[] byArray = new byte[4096];
        byteBuffer.get(byArray);
        try {
            HashMap<String, String> hashMap = DataUtils.parseChecksummedMap(byArray);
            if (hashMap == null) {
                throw DataUtils.newMVStoreException(6, "Store header is corrupt: {0}", this);
            }
            this.storeHeader.putAll(hashMap);
        }
        catch (Exception exception) {
            throw DataUtils.newMVStoreException(6, "Store header is corrupt: {0}", this);
        }
        this.processCommonHeaderAttributes();
        long l = this.size();
        long l2 = l / 4096L;
        MFChunk mFChunk = (MFChunk)this.discoverChunk(l2);
        this.setLastChunk(mFChunk);
        for (MFChunk mFChunk2 : this.getChunksFromLayoutMap()) {
            if (mFChunk2.isLive()) continue;
            this.registerDeadChunk(mFChunk2);
        }
    }

    @Override
    protected void allocateChunkSpace(MFChunk mFChunk, WriteBuffer writeBuffer) {
        mFChunk.block = this.size() / 4096L;
        this.setSize((mFChunk.block + (long)mFChunk.len) * 4096L);
    }

    @Override
    protected void writeChunk(MFChunk mFChunk, WriteBuffer writeBuffer) {
        long l = mFChunk.block * 4096L;
        this.writeFully(mFChunk, l, writeBuffer.getBuffer());
    }

    @Override
    protected void writeCleanShutdownMark() {
    }

    @Override
    protected void adjustStoreToLastChunk() {
    }

    @Override
    protected void compactStore(int n, long l, int n2, MVStore mVStore) {
    }

    @Override
    protected void doHousekeeping(MVStore mVStore) throws InterruptedException {
    }

    @Override
    public int getFillRate() {
        return 0;
    }

    @Override
    protected void shrinkStoreIfPossible(int n) {
    }

    @Override
    public void markUsed(long l, int n) {
    }

    @Override
    protected void freeChunkSpace(Iterable<MFChunk> iterable) {
    }

    @Override
    protected boolean validateFileLength(String string) {
        return true;
    }

    @Override
    public void backup(ZipOutputStream zipOutputStream) throws IOException {
    }
}

