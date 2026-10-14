/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import org.h2.mvstore.Chunk;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.FileStore;
import org.h2.mvstore.FreeSpaceBitSet;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.SFChunk;
import org.h2.mvstore.WriteBuffer;

public abstract class RandomAccessStore
extends FileStore<SFChunk> {
    protected final FreeSpaceBitSet freeSpace = new FreeSpaceBitSet(2, 4096);
    private volatile boolean reuseSpace = true;
    private long reservedLow;
    private long reservedHigh;

    public RandomAccessStore(Map<String, Object> map) {
        super(map);
    }

    @Override
    protected final SFChunk createChunk(int n) {
        return new SFChunk(n);
    }

    @Override
    public SFChunk createChunk(String string) {
        return new SFChunk(string);
    }

    @Override
    protected SFChunk createChunk(Map<String, String> map) {
        return new SFChunk(map);
    }

    @Override
    public void markUsed(long l, int n) {
        this.freeSpace.markUsed(l, n);
    }

    private long allocate(int n, long l, long l2) {
        return this.freeSpace.allocate(n, l, l2);
    }

    private long predictAllocation(int n, long l, long l2) {
        return this.freeSpace.predictAllocation(n, l, l2);
    }

    @Override
    public boolean shouldSaveNow(int n, int n2) {
        return n > n2;
    }

    private boolean isFragmented() {
        return this.freeSpace.isFragmented();
    }

    @Override
    public boolean isSpaceReused() {
        return this.reuseSpace;
    }

    @Override
    public void setReuseSpace(boolean bl) {
        this.reuseSpace = bl;
    }

    @Override
    protected void freeChunkSpace(Iterable<SFChunk> iterable) {
        for (SFChunk sFChunk : iterable) {
            this.freeChunkSpace(sFChunk);
        }
        assert (this.validateFileLength(String.valueOf(iterable)));
    }

    private void freeChunkSpace(SFChunk sFChunk) {
        long l = sFChunk.block * 4096L;
        int n = sFChunk.len * 4096;
        this.free(l, n);
    }

    protected void free(long l, int n) {
        this.freeSpace.free(l, n);
    }

    @Override
    public int getFillRate() {
        this.saveChunkLock.lock();
        try {
            int n = this.freeSpace.getFillRate();
            return n;
        }
        finally {
            this.saveChunkLock.unlock();
        }
    }

    @Override
    protected final boolean validateFileLength(String string) {
        assert (this.saveChunkLock.isHeldByCurrentThread());
        assert (this.getFileLengthInUse() == this.measureFileLengthInUse()) : this.getFileLengthInUse() + " != " + this.measureFileLengthInUse() + " " + string;
        return true;
    }

    private long measureFileLengthInUse() {
        assert (this.saveChunkLock.isHeldByCurrentThread());
        long l = 2L;
        for (SFChunk sFChunk : this.getChunks().values()) {
            if (!sFChunk.isAllocated()) continue;
            l = Math.max(l, sFChunk.block + (long)sFChunk.len);
        }
        return l * 4096L;
    }

    long getFirstFree() {
        return this.freeSpace.getFirstFree();
    }

    long getFileLengthInUse() {
        return this.freeSpace.getLastFree();
    }

    @Override
    protected void readStoreHeader(boolean bl) {
        Object object;
        Object object2;
        long l;
        Object object3 = null;
        boolean bl2 = true;
        boolean bl3 = false;
        ByteBuffer byteBuffer = this.readFully((SFChunk)null, 0L, 8192);
        byte[] byArray = new byte[4096];
        for (int i = 0; i <= 4096; i += 4096) {
            byteBuffer.get(byArray);
            try {
                HashMap<String, String> hashMap = DataUtils.parseChecksummedMap(byArray);
                if (hashMap == null) {
                    bl2 = false;
                    continue;
                }
                l = DataUtils.readHexLong(hashMap, "version", 0L);
                boolean bl4 = bl2 = bl2 && (object3 == null || l == ((SFChunk)object3).version);
                if (object3 != null && l <= ((SFChunk)object3).version) continue;
                bl3 = true;
                this.storeHeader.putAll(hashMap);
                int n = DataUtils.readHexInt(hashMap, "chunk", 0);
                long l2 = DataUtils.readHexLong(hashMap, "block", 2L);
                object2 = (SFChunk)this.readChunkHeaderAndFooter(l2, n);
                if (object2 == null) continue;
                object3 = object2;
                continue;
            }
            catch (Exception exception) {
                bl2 = false;
            }
        }
        if (!bl3) {
            throw DataUtils.newMVStoreException(6, "Store header is corrupt: {0}", this);
        }
        this.processCommonHeaderAttributes();
        boolean bl5 = bl2 = bl2 && object3 != null && !bl;
        if (bl2) {
            bl2 = DataUtils.readHexInt(this.storeHeader, "clean", 0) != 0;
        }
        long l3 = this.size();
        l = l3 / 4096L;
        Comparator comparator = (sFChunk, sFChunk2) -> {
            int n = Long.compare(sFChunk2.version, sFChunk.version);
            if (n == 0) {
                n = Long.compare(sFChunk.block, sFChunk2.block);
            }
            return n;
        };
        HashMap<Long, Object> hashMap = new HashMap<Long, Object>();
        if (bl2) {
            object = new PriorityQueue(20, Collections.reverseOrder(comparator));
            try {
                SFChunk sFChunk3;
                this.setLastChunk(object3);
                object2 = this.getChunksFromLayoutMap().iterator();
                while (object2.hasNext()) {
                    sFChunk3 = (SFChunk)object2.next();
                    object.offer(sFChunk3);
                    if (object.size() != 20) continue;
                    object.poll();
                }
                while (bl2 && (object2 = (SFChunk)object.poll()) != null) {
                    sFChunk3 = (SFChunk)this.readChunkHeaderAndFooter(((SFChunk)object2).block, ((SFChunk)object2).id);
                    bl2 = sFChunk3 != null;
                    if (!bl2) continue;
                    hashMap.put(sFChunk3.block, sFChunk3);
                }
            }
            catch (IllegalStateException illegalStateException) {
                bl2 = false;
            }
        } else {
            object = (SFChunk)this.discoverChunk(l);
            if (object != null) {
                l = ((SFChunk)object).block;
                hashMap.put(l, object);
                if (object3 == null || ((SFChunk)object).version > ((SFChunk)object3).version) {
                    object3 = object;
                }
            }
            if (object3 != null) {
                while (true) {
                    hashMap.put(((SFChunk)object3).block, object3);
                    if (((SFChunk)object3).next == 0L || ((SFChunk)object3).next >= l || (object2 = (SFChunk)this.readChunkHeaderAndFooter(((SFChunk)object3).next, ((SFChunk)object3).id + 1)) == null || ((SFChunk)object2).version <= ((SFChunk)object3).version) break;
                    object3 = object2;
                }
            }
        }
        if (!bl2) {
            boolean bl6;
            boolean bl7 = bl6 = !bl && this.findLastChunkWithCompleteValidChunkSet(comparator, hashMap, false);
            if (!bl6) {
                SFChunk sFChunk4;
                long l4 = l;
                while ((sFChunk4 = (SFChunk)this.discoverChunk(l4)) != null) {
                    l4 = sFChunk4.block;
                    hashMap.put(l4, sFChunk4);
                }
                if (!this.findLastChunkWithCompleteValidChunkSet(comparator, hashMap, true) && this.hasPersistentData()) {
                    throw DataUtils.newMVStoreException(6, "File is corrupted - unable to recover a valid set of chunks", new Object[0]);
                }
            }
        }
        this.clear();
        for (SFChunk sFChunk5 : this.getChunks().values()) {
            if (sFChunk5.isAllocated()) {
                long l5 = sFChunk5.block * 4096L;
                int n = sFChunk5.len * 4096;
                this.markUsed(l5, n);
            }
            if (sFChunk5.isLive()) continue;
            this.registerDeadChunk(sFChunk5);
        }
        assert (this.validateFileLength("on open"));
    }

    @Override
    protected void initializeStoreHeader(long l) {
        this.initializeCommonHeaderAttributes(l);
        this.writeStoreHeader();
    }

    @Override
    protected final void allocateChunkSpace(SFChunk sFChunk, WriteBuffer writeBuffer) {
        long l = this.reservedLow;
        long l2 = this.reservedHigh > 0L ? this.reservedHigh : (this.isSpaceReused() ? 0L : this.getAfterLastBlock());
        long l3 = this.allocate(writeBuffer.limit(), l, l2);
        sFChunk.next = l > 0L || l2 == l ? this.predictAllocation(sFChunk.len, 0L, 0L) : 0L;
        sFChunk.block = l3 / 4096L;
    }

    @Override
    protected final void writeChunk(SFChunk sFChunk, WriteBuffer writeBuffer) {
        long l = sFChunk.block * 4096L;
        this.writeFully(sFChunk, l, writeBuffer.getBuffer());
        boolean bl = l + (long)writeBuffer.limit() >= this.size();
        boolean bl2 = this.shouldWriteStoreHeader(sFChunk, bl);
        this.lastChunk = sFChunk;
        if (bl2) {
            this.writeStoreHeader();
        }
        if (!bl) {
            this.shrinkStoreIfPossible(1);
        }
    }

    private boolean shouldWriteStoreHeader(SFChunk sFChunk, boolean bl) {
        boolean bl2 = false;
        if (!bl) {
            SFChunk sFChunk2 = (SFChunk)this.lastChunk;
            if (sFChunk2 == null) {
                bl2 = true;
            } else if (sFChunk2.next != sFChunk.block) {
                bl2 = true;
            } else {
                long l = DataUtils.readHexLong(this.storeHeader, "version", 0L);
                if (sFChunk2.version - l > 20L) {
                    bl2 = true;
                } else {
                    for (int i = DataUtils.readHexInt(this.storeHeader, "chunk", 0); !bl2 && i <= sFChunk2.id; ++i) {
                        bl2 = !this.getChunks().containsKey(i);
                    }
                }
            }
        }
        if (this.storeHeader.remove("clean") != null) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    protected final void writeCleanShutdownMark() {
        this.shrinkStoreIfPossible(0);
        this.storeHeader.put("clean", 1);
        this.writeStoreHeader();
    }

    @Override
    protected final void adjustStoreToLastChunk() {
        this.storeHeader.put("clean", 1);
        this.writeStoreHeader();
        this.readStoreHeader(false);
    }

    @Override
    protected void compactStore(int n, long l, int n2, MVStore mVStore) {
        this.setRetentionTime(0);
        long l2 = System.nanoTime() + l * 1000000L;
        while (this.compact(n, n2)) {
            this.sync();
            this.compactMoveChunks(n, n2, mVStore);
            if (System.nanoTime() - l2 <= 0L) continue;
            break;
        }
    }

    public void compactMoveChunks(int n, long l, MVStore mVStore) {
        if (this.isSpaceReused()) {
            mVStore.executeFilestoreOperation(() -> {
                this.dropUnusedChunks();
                this.saveChunkLock.lock();
                try {
                    if (this.hasPersistentData() && this.getFillRate() <= n) {
                        this.compactMoveChunks(l);
                    }
                }
                finally {
                    this.saveChunkLock.unlock();
                }
            });
        }
    }

    private void compactMoveChunks(long l) {
        long l2 = this.getFirstFree() / 4096L;
        Iterable<SFChunk> iterable = this.findChunksToMove(l2, l);
        if (iterable != null) {
            this.compactMoveChunks(iterable);
        }
    }

    private Iterable<SFChunk> findChunksToMove(long l, long l2) {
        long l3 = l2 / 4096L;
        ArrayList arrayList = null;
        if (l3 > 0L) {
            PriorityQueue<SFChunk> priorityQueue = new PriorityQueue<SFChunk>(this.getChunks().size() / 2 + 1, (sFChunk, sFChunk2) -> {
                int n = Integer.compare(sFChunk2.collectPriority, sFChunk.collectPriority);
                if (n != 0) {
                    return n;
                }
                return Long.signum(sFChunk2.block - sFChunk.block);
            });
            long l4 = 0L;
            for (SFChunk sFChunk3 : this.getChunks().values()) {
                Chunk chunk;
                if (!sFChunk3.isAllocated() || sFChunk3.block <= l) continue;
                sFChunk3.collectPriority = this.getMovePriority(sFChunk3);
                priorityQueue.offer(sFChunk3);
                l4 += (long)sFChunk3.len;
                while (l4 > l3 && (chunk = (Chunk)priorityQueue.poll()) != null) {
                    l4 -= (long)chunk.len;
                }
            }
            if (!priorityQueue.isEmpty()) {
                ArrayList arrayList2 = new ArrayList(priorityQueue);
                arrayList2.sort(Chunk.PositionComparator.instance());
                arrayList = arrayList2;
            }
        }
        return arrayList;
    }

    private int getMovePriority(SFChunk sFChunk) {
        return this.getMovePriority((int)sFChunk.block);
    }

    private void compactMoveChunks(Iterable<SFChunk> iterable) {
        assert (this.saveChunkLock.isHeldByCurrentThread());
        if (iterable != null) {
            this.writeStoreHeader();
            this.sync();
            Iterator<SFChunk> iterator2 = iterable.iterator();
            assert (iterator2.hasNext());
            long l = iterator2.next().block;
            long l2 = this.getAfterLastBlock();
            for (SFChunk sFChunk : iterable) {
                this.moveChunk(sFChunk, l, l2);
            }
            this.store(l, l2);
            this.sync();
            SFChunk sFChunk = (SFChunk)this.lastChunk;
            assert (sFChunk != null);
            long l3 = this.getAfterLastBlock();
            boolean bl = sFChunk.block < l;
            boolean bl2 = !bl;
            for (SFChunk sFChunk2 : iterable) {
                if (sFChunk2.block < l2 || !this.moveChunk(sFChunk2, l2, l3)) continue;
                assert (sFChunk2.block < l2);
                bl2 = true;
            }
            assert (l3 >= this.getAfterLastBlock());
            if (bl2) {
                boolean bl3 = this.moveChunkInside(sFChunk, l2);
                this.store(l2, l3);
                this.sync();
                long l4 = bl3 || bl ? l3 : sFChunk.block;
                boolean bl4 = bl3 = !bl3 && this.moveChunkInside(sFChunk, l4);
                if (this.moveChunkInside((SFChunk)this.lastChunk, l4) || bl3) {
                    this.store(l4, -1L);
                }
            }
            this.shrinkStoreIfPossible(0);
            this.sync();
        }
    }

    private void writeStoreHeader() {
        StringBuilder stringBuilder = new StringBuilder(112);
        if (this.hasPersistentData()) {
            this.storeHeader.put("block", ((SFChunk)this.lastChunk).block);
            this.storeHeader.put("chunk", ((SFChunk)this.lastChunk).id);
            this.storeHeader.put("version", ((SFChunk)this.lastChunk).version);
        }
        DataUtils.appendMap(stringBuilder, this.storeHeader);
        byte[] byArray = stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1);
        int n = DataUtils.getFletcher32(byArray, 0, byArray.length);
        DataUtils.appendMap(stringBuilder, "fletcher", n);
        stringBuilder.append('\n');
        byArray = stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
        byteBuffer.put(byArray);
        byteBuffer.position(4096);
        byteBuffer.put(byArray);
        byteBuffer.rewind();
        this.writeFully(null, 0L, byteBuffer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void store(long l, long l2) {
        this.reservedLow = l;
        this.reservedHigh = l2;
        this.saveChunkLock.unlock();
        try {
            this.store();
        }
        finally {
            this.saveChunkLock.lock();
            this.reservedLow = 0L;
            this.reservedHigh = 0L;
        }
    }

    private boolean moveChunkInside(SFChunk sFChunk, long l) {
        boolean bl;
        boolean bl2 = bl = sFChunk.block >= l && this.predictAllocation(sFChunk.len, l, -1L) < l && this.moveChunk(sFChunk, l, -1L);
        assert (!bl || sFChunk.block + (long)sFChunk.len <= l);
        return bl;
    }

    private boolean moveChunk(SFChunk sFChunk, long l, long l2) {
        if (!this.getChunks().containsKey(sFChunk.id)) {
            return false;
        }
        long l3 = sFChunk.block * 4096L;
        int n = sFChunk.len * 4096;
        long l4 = this.allocate(n, l, l2);
        long l5 = l4 / 4096L;
        assert (l2 > 0L || l5 <= sFChunk.block) : l5 + " " + sFChunk;
        ByteBuffer byteBuffer = this.readFully(sFChunk, l3, n);
        this.writeFully(null, l4, byteBuffer);
        this.free(l3, n);
        sFChunk.block = l5;
        sFChunk.next = 0L;
        this.saveChunkMetadataChanges(sFChunk);
        return true;
    }

    @Override
    protected void shrinkStoreIfPossible(int n) {
        assert (this.saveChunkLock.isHeldByCurrentThread());
        long l = this.getFileLengthInUse();
        assert (l == this.measureFileLengthInUse()) : l + " != " + this.measureFileLengthInUse();
        this.shrinkIfPossible(n);
    }

    private void shrinkIfPossible(int n) {
        long l;
        if (this.isReadOnly()) {
            return;
        }
        long l2 = this.getFileLengthInUse();
        if (l2 >= (l = this.size())) {
            return;
        }
        if (n > 0 && l - l2 < 4096L) {
            return;
        }
        int n2 = (int)(100L - l2 * 100L / l);
        if (n2 < n) {
            return;
        }
        this.sync();
        this.truncate(l2);
    }

    @Override
    protected void doHousekeeping(MVStore mVStore) throws InterruptedException {
        int n;
        int n2 = mVStore.getAutoCommitMemory();
        int n3 = this.getFillRate();
        if (this.isFragmented() && n3 < this.getAutoCompactFillRate()) {
            mVStore.tryExecuteUnderStoreLock(() -> {
                int n2 = 2 * n2;
                if (this.isIdle()) {
                    n2 *= 4;
                }
                this.compactMoveChunks(101, n2, mVStore);
                return true;
            });
        }
        int n4 = this.getRewritableChunksFillRate();
        int n5 = 100 - (100 - n4) / 2;
        int n6 = n = this.isIdle() ? n4 : n5;
        if (n < this.getTargetFillRate()) {
            mVStore.tryExecuteUnderStoreLock(() -> {
                int n4 = n2;
                if (!this.isIdle()) {
                    n4 /= 4;
                }
                if (this.rewriteChunks(n4, this.isIdle() ? n5 : n4)) {
                    this.dropUnusedChunks();
                }
                return true;
            });
        }
    }

    private int getTargetFillRate() {
        int n = this.getAutoCompactFillRate();
        if (!this.isIdle()) {
            n /= 2;
        }
        return n;
    }

    protected abstract void truncate(long var1);

    @Override
    public void clear() {
        this.freeSpace.clear();
    }

    public int getMovePriority(int n) {
        return this.freeSpace.getMovePriority(n);
    }

    private long getAfterLastBlock() {
        assert (this.saveChunkLock.isHeldByCurrentThread());
        return this.getAfterLastBlock_();
    }

    protected long getAfterLastBlock_() {
        return this.freeSpace.getAfterLastBlock();
    }

    @Override
    public Collection<SFChunk> getRewriteCandidates() {
        return this.isSpaceReused() ? null : Collections.emptyList();
    }
}

