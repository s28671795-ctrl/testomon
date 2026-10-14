/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.mem;

import java.io.IOException;
import java.nio.channels.NonWritableChannelException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.h2.compress.CompressLZF;
import org.h2.util.MathUtils;

class FileMemData {
    private static final int CACHE_SIZE = 8;
    private static final int BLOCK_SIZE_SHIFT = 10;
    private static final int BLOCK_SIZE = 1024;
    private static final int BLOCK_SIZE_MASK = 1023;
    private static final CompressLZF LZF = new CompressLZF();
    private static final byte[] BUFFER = new byte[2048];
    private static final byte[] COMPRESSED_EMPTY_BLOCK;
    private static final Cache<CompressItem, CompressItem> COMPRESS_LATER;
    private String name;
    private final int id;
    private final boolean compress;
    private volatile long length;
    private AtomicReference<byte[]>[] data;
    private long lastModified;
    private boolean isReadOnly;
    private boolean isLockedExclusive;
    private int sharedLockCount;

    FileMemData(String string, boolean bl) {
        this.name = string;
        this.id = string.hashCode();
        this.compress = bl;
        this.data = new AtomicReference[0];
        this.lastModified = System.currentTimeMillis();
    }

    private byte[] getPage(int n) {
        AtomicReference<byte[]>[] atomicReferenceArray = this.data;
        if (n >= atomicReferenceArray.length) {
            return null;
        }
        return atomicReferenceArray[n].get();
    }

    private void setPage(int n, byte[] byArray, byte[] byArray2, boolean bl) {
        AtomicReference<byte[]>[] atomicReferenceArray = this.data;
        if (n >= atomicReferenceArray.length) {
            return;
        }
        if (bl) {
            atomicReferenceArray[n].set(byArray2);
        } else {
            atomicReferenceArray[n].compareAndSet(byArray, byArray2);
        }
    }

    int getId() {
        return this.id;
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

    synchronized void unlock() throws IOException {
        if (this.isLockedExclusive) {
            this.isLockedExclusive = false;
        } else if (this.sharedLockCount > 0) {
            --this.sharedLockCount;
        } else {
            throw new IOException("not locked");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void compressLater(int n) {
        CompressItem compressItem = new CompressItem();
        compressItem.file = this;
        compressItem.page = n;
        CompressLZF compressLZF = LZF;
        synchronized (compressLZF) {
            COMPRESS_LATER.put(compressItem, compressItem);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] expand(int n) {
        byte[] byArray = this.getPage(n);
        if (byArray.length == 1024) {
            return byArray;
        }
        byte[] byArray2 = new byte[1024];
        if (byArray != COMPRESSED_EMPTY_BLOCK) {
            CompressLZF compressLZF = LZF;
            synchronized (compressLZF) {
                LZF.expand(byArray, 0, byArray.length, byArray2, 0, 1024);
            }
        }
        this.setPage(n, byArray, byArray2, false);
        return byArray2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void compress(int n) {
        byte[] byArray = this.getPage(n);
        if (byArray == null || byArray.length != 1024) {
            return;
        }
        CompressLZF compressLZF = LZF;
        synchronized (compressLZF) {
            int n2 = LZF.compress(byArray, 0, 1024, BUFFER, 0);
            if (n2 <= 1024) {
                byte[] byArray2 = Arrays.copyOf(BUFFER, n2);
                this.setPage(n, byArray, byArray2, false);
            }
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

    void truncate(long l) {
        this.changeLength(l);
        long l2 = MathUtils.roundUpLong(l, 1024L);
        if (l2 != l) {
            int n = (int)(l >>> 10);
            byte[] byArray = this.expand(n);
            byte[] byArray2 = Arrays.copyOf(byArray, byArray.length);
            for (int i = (int)(l & 0x3FFL); i < 1024; ++i) {
                byArray2[i] = 0;
            }
            this.setPage(n, byArray, byArray2, true);
            if (this.compress) {
                this.compressLater(n);
            }
        }
    }

    private void changeLength(long l) {
        this.length = l;
        int n = (int)((l = MathUtils.roundUpLong(l, 1024L)) >>> 10);
        if (n != this.data.length) {
            AtomicReference<byte[]>[] atomicReferenceArray = Arrays.copyOf(this.data, n);
            for (int i = this.data.length; i < n; ++i) {
                atomicReferenceArray[i] = new AtomicReference<byte[]>(COMPRESSED_EMPTY_BLOCK);
            }
            this.data = atomicReferenceArray;
        }
    }

    long readWrite(long l, byte[] byArray, int n, int n2, boolean bl) {
        long l2 = l + (long)n2;
        if (l2 > this.length) {
            if (bl) {
                this.changeLength(l2);
            } else {
                n2 = (int)(this.length - l);
            }
        }
        while (n2 > 0) {
            int n3 = (int)Math.min((long)n2, 1024L - (l & 0x3FFL));
            int n4 = (int)(l >>> 10);
            byte[] byArray2 = this.expand(n4);
            int n5 = (int)(l & 0x3FFL);
            if (bl) {
                byte[] byArray3 = Arrays.copyOf(byArray2, byArray2.length);
                System.arraycopy(byArray, n, byArray3, n5, n3);
                this.setPage(n4, byArray2, byArray3, true);
            } else {
                System.arraycopy(byArray2, n5, byArray, n, n3);
            }
            if (this.compress) {
                this.compressLater(n4);
            }
            n += n3;
            l += (long)n3;
            n2 -= n3;
        }
        return l;
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
        COMPRESS_LATER = new Cache(8);
        byte[] byArray = new byte[1024];
        int n = LZF.compress(byArray, 0, 1024, BUFFER, 0);
        COMPRESSED_EMPTY_BLOCK = Arrays.copyOf(BUFFER, n);
    }

    static class CompressItem {
        FileMemData file;
        int page;

        CompressItem() {
        }

        public int hashCode() {
            return this.page ^ this.file.getId();
        }

        public boolean equals(Object object) {
            if (object instanceof CompressItem) {
                CompressItem compressItem = (CompressItem)object;
                return compressItem.page == this.page && compressItem.file == this.file;
            }
            return false;
        }
    }

    static class Cache<K, V>
    extends LinkedHashMap<K, V> {
        private static final long serialVersionUID = 1L;
        private final int size;

        Cache(int n) {
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
            compressItem.file.compress(compressItem.page);
            return true;
        }
    }
}

