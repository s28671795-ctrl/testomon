/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.niomem;

import java.nio.ByteBuffer;
import java.nio.channels.NonWritableChannelException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.h2.compress.CompressLZF;
import org.h2.util.MathUtils;

class FileNioMemData {
    private static final int CACHE_MIN_SIZE = 8;
    private static final int BLOCK_SIZE_SHIFT = 16;
    private static final int BLOCK_SIZE = 65536;
    private static final int BLOCK_SIZE_MASK = 65535;
    private static final ByteBuffer COMPRESSED_EMPTY_BLOCK;
    private static final ThreadLocal<CompressLZF> LZF_THREAD_LOCAL;
    private static final ThreadLocal<byte[]> COMPRESS_OUT_BUF_THREAD_LOCAL;
    final int nameHashCode;
    private final CompressLaterCache<CompressItem, CompressItem> compressLaterCache = new CompressLaterCache(8);
    private String name;
    private final boolean compress;
    private final float compressLaterCachePercent;
    private volatile long length;
    private volatile AtomicReference<ByteBuffer>[] buffers;
    private long lastModified;
    private boolean isReadOnly;
    private boolean isLockedExclusive;
    private int sharedLockCount;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    FileNioMemData(String string, boolean bl, float f) {
        this.name = string;
        this.nameHashCode = string.hashCode();
        this.compress = bl;
        this.compressLaterCachePercent = f;
        this.buffers = new AtomicReference[0];
        this.lastModified = System.currentTimeMillis();
    }

    synchronized boolean lockExclusive() {
        if (this.sharedLockCount > 0 || this.isLockedExclusive) {
            return false;
        }
        this.isLockedExclusive = true;
        return true;
    }

    synchronized boolean lockShared() {
        if (this.isLockedExclusive) {
            return false;
        }
        ++this.sharedLockCount;
        return true;
    }

    synchronized void unlock() {
        if (this.isLockedExclusive) {
            this.isLockedExclusive = false;
        } else {
            this.sharedLockCount = Math.max(0, this.sharedLockCount - 1);
        }
    }

    private void addToCompressLaterCache(int n) {
        CompressItem compressItem = new CompressItem(this, n);
        this.compressLaterCache.put(compressItem, compressItem);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ByteBuffer expandPage(int n) {
        ByteBuffer byteBuffer = this.buffers[n].get();
        if (byteBuffer.capacity() == 65536) {
            return byteBuffer;
        }
        ByteBuffer byteBuffer2 = byteBuffer;
        synchronized (byteBuffer2) {
            if (byteBuffer.capacity() == 65536) {
                return byteBuffer;
            }
            ByteBuffer byteBuffer3 = ByteBuffer.allocateDirect(65536);
            if (byteBuffer != COMPRESSED_EMPTY_BLOCK) {
                byteBuffer.position(0);
                CompressLZF.expand(byteBuffer, byteBuffer3);
            }
            this.buffers[n].compareAndSet(byteBuffer, byteBuffer3);
            return byteBuffer3;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void compressPage(int n) {
        ByteBuffer byteBuffer;
        ByteBuffer byteBuffer2 = byteBuffer = this.buffers[n].get();
        synchronized (byteBuffer2) {
            if (byteBuffer.capacity() != 65536) {
                return;
            }
            byte[] byArray = COMPRESS_OUT_BUF_THREAD_LOCAL.get();
            int n2 = LZF_THREAD_LOCAL.get().compress(byteBuffer, 0, byArray, 0);
            ByteBuffer byteBuffer3 = ByteBuffer.allocateDirect(n2);
            byteBuffer3.put(byArray, 0, n2);
            this.buffers[n].compareAndSet(byteBuffer, byteBuffer3);
        }
    }

    void touch(boolean bl) {
        if (this.isReadOnly || bl) {
            throw new NonWritableChannelException();
        }
        this.lastModified = System.currentTimeMillis();
    }

    long length() {
        return this.length;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void truncate(long l) {
        this.rwLock.writeLock().lock();
        try {
            this.changeLength(l);
            long l2 = MathUtils.roundUpLong(l, 65536L);
            if (l2 != l) {
                int n = (int)(l >>> 16);
                ByteBuffer byteBuffer = this.expandPage(n);
                for (int i = (int)(l & 0xFFFFL); i < 65536; ++i) {
                    byteBuffer.put(i, (byte)0);
                }
                if (this.compress) {
                    this.addToCompressLaterCache(n);
                }
            }
        }
        finally {
            this.rwLock.writeLock().unlock();
        }
    }

    private void changeLength(long l) {
        this.length = l;
        int n = (int)((l = MathUtils.roundUpLong(l, 65536L)) >>> 16);
        if (n != this.buffers.length) {
            AtomicReference[] atomicReferenceArray = new AtomicReference[n];
            System.arraycopy(this.buffers, 0, atomicReferenceArray, 0, Math.min(this.buffers.length, atomicReferenceArray.length));
            for (int i = this.buffers.length; i < n; ++i) {
                atomicReferenceArray[i] = new AtomicReference<ByteBuffer>(COMPRESSED_EMPTY_BLOCK);
            }
            this.buffers = atomicReferenceArray;
        }
        this.compressLaterCache.setCacheSize(Math.max(8, (int)((float)n * this.compressLaterCachePercent / 100.0f)));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    long readWrite(long l, ByteBuffer byteBuffer, int n, int n2, boolean bl) {
        Lock lock = bl ? this.rwLock.writeLock() : this.rwLock.readLock();
        lock.lock();
        try {
            long l2 = l + (long)n2;
            if (l2 > this.length) {
                if (bl) {
                    this.changeLength(l2);
                } else {
                    n2 = (int)(this.length - l);
                }
            }
            while (n2 > 0) {
                ByteBuffer byteBuffer2;
                int n3 = (int)Math.min((long)n2, 65536L - (l & 0xFFFFL));
                int n4 = (int)(l >>> 16);
                ByteBuffer byteBuffer3 = this.expandPage(n4);
                int n5 = (int)(l & 0xFFFFL);
                if (bl) {
                    byteBuffer2 = byteBuffer.slice();
                    ByteBuffer byteBuffer4 = byteBuffer3.duplicate();
                    byteBuffer2.position(n);
                    byteBuffer2.limit(n + n3);
                    byteBuffer4.position(n5);
                    byteBuffer4.put(byteBuffer2);
                } else {
                    byteBuffer2 = byteBuffer3.duplicate();
                    byteBuffer2.position(n5);
                    byteBuffer2.limit(n3 + n5);
                    int n6 = byteBuffer.position();
                    byteBuffer.position(n);
                    byteBuffer.put(byteBuffer2);
                    byteBuffer.position(n6);
                }
                if (this.compress) {
                    this.addToCompressLaterCache(n4);
                }
                n += n3;
                l += (long)n3;
                n2 -= n3;
            }
            long l3 = l;
            return l3;
        }
        finally {
            lock.unlock();
        }
    }

    void setName(String string) {
        this.name = string;
    }

    String getName() {
        return this.name;
    }

    long getLastModified() {
        return this.lastModified;
    }

    boolean canWrite() {
        return !this.isReadOnly;
    }

    boolean setReadOnly() {
        this.isReadOnly = true;
        return true;
    }

    static {
        LZF_THREAD_LOCAL = ThreadLocal.withInitial(CompressLZF::new);
        COMPRESS_OUT_BUF_THREAD_LOCAL = ThreadLocal.withInitial(() -> new byte[131072]);
        byte[] byArray = new byte[65536];
        byte[] byArray2 = new byte[131072];
        int n = new CompressLZF().compress(byArray, 0, 65536, byArray2, 0);
        COMPRESSED_EMPTY_BLOCK = ByteBuffer.allocateDirect(n);
        COMPRESSED_EMPTY_BLOCK.put(byArray2, 0, n);
    }

    static class CompressItem {
        public final FileNioMemData data;
        public final int page;

        public CompressItem(FileNioMemData fileNioMemData, int n) {
            this.data = fileNioMemData;
            this.page = n;
        }

        public int hashCode() {
            return this.page ^ this.data.nameHashCode;
        }

        public boolean equals(Object object) {
            if (object instanceof CompressItem) {
                CompressItem compressItem = (CompressItem)object;
                return compressItem.data == this.data && compressItem.page == this.page;
            }
            return false;
        }
    }

    static class CompressLaterCache<K, V>
    extends LinkedHashMap<K, V> {
        private static final long serialVersionUID = 1L;
        private int size;

        CompressLaterCache(int n) {
            super(n, 0.75f, true);
            this.size = n;
        }

        @Override
        public synchronized V put(K k, V v) {
            return super.put(k, v);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
            if (this.size() < this.size) {
                return false;
            }
            CompressItem compressItem = (CompressItem)entry.getKey();
            compressItem.data.compressPage(compressItem.page);
            return true;
        }

        public void setCacheSize(int n) {
            this.size = n;
        }
    }
}

