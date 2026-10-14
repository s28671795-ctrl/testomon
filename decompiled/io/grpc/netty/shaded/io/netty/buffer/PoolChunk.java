/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.buffer;

import io.grpc.netty.shaded.io.netty.buffer.IntPriorityQueue;
import io.grpc.netty.shaded.io.netty.buffer.LongLongHashMap;
import io.grpc.netty.shaded.io.netty.buffer.PoolArena;
import io.grpc.netty.shaded.io.netty.buffer.PoolChunkList;
import io.grpc.netty.shaded.io.netty.buffer.PoolChunkMetric;
import io.grpc.netty.shaded.io.netty.buffer.PoolSubpage;
import io.grpc.netty.shaded.io.netty.buffer.PoolThreadCache;
import io.grpc.netty.shaded.io.netty.buffer.PooledByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.PooledByteBufAllocator;
import io.grpc.netty.shaded.io.netty.util.internal.LongCounter;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

final class PoolChunk<T>
implements PoolChunkMetric {
    private static final int SIZE_BIT_LENGTH = 15;
    private static final int INUSED_BIT_LENGTH = 1;
    private static final int SUBPAGE_BIT_LENGTH = 1;
    private static final int BITMAP_IDX_BIT_LENGTH = 32;
    static final int IS_SUBPAGE_SHIFT = 32;
    static final int IS_USED_SHIFT = 33;
    static final int SIZE_SHIFT = 34;
    static final int RUN_OFFSET_SHIFT = 49;
    final PoolArena<T> arena;
    final Object base;
    final T memory;
    final boolean unpooled;
    private final LongLongHashMap runsAvailMap;
    private final IntPriorityQueue[] runsAvail;
    private final ReentrantLock runsAvailLock;
    private final PoolSubpage<T>[] subpages;
    private final LongCounter pinnedBytes = PlatformDependent.newLongCounter();
    private final int pageSize;
    private final int pageShifts;
    private final int chunkSize;
    private final Deque<ByteBuffer> cachedNioBuffers;
    int freeBytes;
    PoolChunkList<T> parent;
    PoolChunk<T> prev;
    PoolChunk<T> next;

    PoolChunk(PoolArena<T> arena, Object base, T memory, int pageSize, int pageShifts, int chunkSize, int maxPageIdx) {
        this.unpooled = false;
        this.arena = arena;
        this.base = base;
        this.memory = memory;
        this.pageSize = pageSize;
        this.pageShifts = pageShifts;
        this.chunkSize = chunkSize;
        this.freeBytes = chunkSize;
        this.runsAvail = PoolChunk.newRunsAvailqueueArray(maxPageIdx);
        this.runsAvailLock = new ReentrantLock();
        this.runsAvailMap = new LongLongHashMap(-1L);
        this.subpages = new PoolSubpage[chunkSize >> pageShifts];
        int pages = chunkSize >> pageShifts;
        long initHandle = (long)pages << 34;
        this.insertAvailRun(0, pages, initHandle);
        this.cachedNioBuffers = new ArrayDeque<ByteBuffer>(8);
    }

    PoolChunk(PoolArena<T> arena, Object base, T memory, int size) {
        this.unpooled = true;
        this.arena = arena;
        this.base = base;
        this.memory = memory;
        this.pageSize = 0;
        this.pageShifts = 0;
        this.runsAvailMap = null;
        this.runsAvail = null;
        this.runsAvailLock = null;
        this.subpages = null;
        this.chunkSize = size;
        this.cachedNioBuffers = null;
    }

    private static IntPriorityQueue[] newRunsAvailqueueArray(int size) {
        IntPriorityQueue[] queueArray = new IntPriorityQueue[size];
        for (int i = 0; i < queueArray.length; ++i) {
            queueArray[i] = new IntPriorityQueue();
        }
        return queueArray;
    }

    private void insertAvailRun(int runOffset, int pages, long handle2) {
        int pageIdxFloor = this.arena.pages2pageIdxFloor(pages);
        IntPriorityQueue queue = this.runsAvail[pageIdxFloor];
        assert (PoolChunk.isRun(handle2));
        queue.offer((int)(handle2 >> 32));
        this.insertAvailRun0(runOffset, handle2);
        if (pages > 1) {
            this.insertAvailRun0(PoolChunk.lastPage(runOffset, pages), handle2);
        }
    }

    private void insertAvailRun0(int runOffset, long handle2) {
        long pre = this.runsAvailMap.put(runOffset, handle2);
        assert (pre == -1L);
    }

    private void removeAvailRun(long handle2) {
        int pageIdxFloor = this.arena.pages2pageIdxFloor(PoolChunk.runPages(handle2));
        this.runsAvail[pageIdxFloor].remove((int)(handle2 >> 32));
        this.removeAvailRun0(handle2);
    }

    private void removeAvailRun0(long handle2) {
        int runOffset = PoolChunk.runOffset(handle2);
        int pages = PoolChunk.runPages(handle2);
        this.runsAvailMap.remove(runOffset);
        if (pages > 1) {
            this.runsAvailMap.remove(PoolChunk.lastPage(runOffset, pages));
        }
    }

    private static int lastPage(int runOffset, int pages) {
        return runOffset + pages - 1;
    }

    private long getAvailRunByOffset(int runOffset) {
        return this.runsAvailMap.get(runOffset);
    }

    @Override
    public int usage() {
        int freeBytes;
        if (this.unpooled) {
            freeBytes = this.freeBytes;
        } else {
            this.runsAvailLock.lock();
            try {
                freeBytes = this.freeBytes;
            }
            finally {
                this.runsAvailLock.unlock();
            }
        }
        return this.usage(freeBytes);
    }

    private int usage(int freeBytes) {
        if (freeBytes == 0) {
            return 100;
        }
        int freePercentage = (int)((long)freeBytes * 100L / (long)this.chunkSize);
        if (freePercentage == 0) {
            return 99;
        }
        return 100 - freePercentage;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int sizeIdx, PoolThreadCache cache) {
        long handle2;
        block14: {
            if (sizeIdx <= this.arena.smallMaxSizeIdx) {
                PoolSubpage head = this.arena.smallSubpagePools[sizeIdx];
                head.lock();
                try {
                    PoolSubpage nextSub = head.next;
                    if (nextSub != head) {
                        assert (nextSub.doNotDestroy && nextSub.elemSize == this.arena.sizeIdx2size(sizeIdx)) : "doNotDestroy=" + nextSub.doNotDestroy + ", elemSize=" + nextSub.elemSize + ", sizeIdx=" + sizeIdx;
                        long handle3 = nextSub.allocate();
                        assert (handle3 >= 0L);
                        assert (PoolChunk.isSubpage(handle3));
                        nextSub.chunk.initBufWithSubpage(buf, null, handle3, reqCapacity, cache);
                        boolean bl = true;
                        return bl;
                    }
                    handle2 = this.allocateSubpage(sizeIdx, head);
                    if (handle2 < 0L) {
                        boolean bl = false;
                        return bl;
                    }
                    assert (PoolChunk.isSubpage(handle2));
                    break block14;
                }
                finally {
                    head.unlock();
                }
            }
            int runSize = this.arena.sizeIdx2size(sizeIdx);
            handle2 = this.allocateRun(runSize);
            if (handle2 < 0L) {
                return false;
            }
            assert (!PoolChunk.isSubpage(handle2));
        }
        ByteBuffer nioBuffer = this.cachedNioBuffers != null ? this.cachedNioBuffers.pollLast() : null;
        this.initBuf(buf, nioBuffer, handle2, reqCapacity, cache);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long allocateRun(int runSize) {
        int pages = runSize >> this.pageShifts;
        int pageIdx = this.arena.pages2pageIdx(pages);
        this.runsAvailLock.lock();
        try {
            int queueIdx = this.runFirstBestFit(pageIdx);
            if (queueIdx == -1) {
                long l = -1L;
                return l;
            }
            IntPriorityQueue queue = this.runsAvail[queueIdx];
            long handle2 = queue.poll();
            assert (handle2 != -1L);
            assert (!PoolChunk.isUsed(handle2 <<= 32)) : "invalid handle: " + handle2;
            this.removeAvailRun0(handle2);
            handle2 = this.splitLargeRun(handle2, pages);
            int pinnedSize = PoolChunk.runSize(this.pageShifts, handle2);
            this.freeBytes -= pinnedSize;
            long l = handle2;
            return l;
        }
        finally {
            this.runsAvailLock.unlock();
        }
    }

    private int calculateRunSize(int sizeIdx) {
        int nElements;
        int maxElements = 1 << this.pageShifts - 4;
        int runSize = 0;
        int elemSize = this.arena.sizeIdx2size(sizeIdx);
        while ((nElements = (runSize += this.pageSize) / elemSize) < maxElements && runSize != nElements * elemSize) {
        }
        while (nElements > maxElements) {
            nElements = (runSize -= this.pageSize) / elemSize;
        }
        assert (nElements > 0);
        assert (runSize <= this.chunkSize);
        assert (runSize >= elemSize);
        return runSize;
    }

    private int runFirstBestFit(int pageIdx) {
        if (this.freeBytes == this.chunkSize) {
            return this.arena.nPSizes - 1;
        }
        for (int i = pageIdx; i < this.arena.nPSizes; ++i) {
            IntPriorityQueue queue = this.runsAvail[i];
            if (queue == null || queue.isEmpty()) continue;
            return i;
        }
        return -1;
    }

    private long splitLargeRun(long handle2, int needPages) {
        assert (needPages > 0);
        int totalPages = PoolChunk.runPages(handle2);
        assert (needPages <= totalPages);
        int remPages = totalPages - needPages;
        if (remPages > 0) {
            int runOffset = PoolChunk.runOffset(handle2);
            int availOffset = runOffset + needPages;
            long availRun = PoolChunk.toRunHandle(availOffset, remPages, 0);
            this.insertAvailRun(availOffset, remPages, availRun);
            return PoolChunk.toRunHandle(runOffset, needPages, 1);
        }
        return handle2 |= 0x200000000L;
    }

    private long allocateSubpage(int sizeIdx, PoolSubpage<T> head) {
        int runSize = this.calculateRunSize(sizeIdx);
        long runHandle = this.allocateRun(runSize);
        if (runHandle < 0L) {
            return -1L;
        }
        int runOffset = PoolChunk.runOffset(runHandle);
        assert (this.subpages[runOffset] == null);
        int elemSize = this.arena.sizeIdx2size(sizeIdx);
        PoolSubpage<T> subpage = new PoolSubpage<T>(head, this, this.pageShifts, runOffset, PoolChunk.runSize(this.pageShifts, runHandle), elemSize);
        this.subpages[runOffset] = subpage;
        return subpage.allocate();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void free(long handle2, int normCapacity, ByteBuffer nioBuffer) {
        if (PoolChunk.isSubpage(handle2)) {
            int sIdx = PoolChunk.runOffset(handle2);
            PoolSubpage subpage = this.subpages[sIdx];
            assert (subpage != null);
            PoolSubpage head = subpage.chunk.arena.smallSubpagePools[subpage.headIndex];
            head.lock();
            try {
                assert (subpage.doNotDestroy);
                if (subpage.free(head, PoolChunk.bitmapIdx(handle2))) {
                    return;
                }
                assert (!subpage.doNotDestroy);
                this.subpages[sIdx] = null;
            }
            finally {
                head.unlock();
            }
        }
        int runSize = PoolChunk.runSize(this.pageShifts, handle2);
        this.runsAvailLock.lock();
        try {
            long finalRun = this.collapseRuns(handle2);
            finalRun &= 0xFFFFFFFDFFFFFFFFL;
            this.insertAvailRun(PoolChunk.runOffset(finalRun &= 0xFFFFFFFEFFFFFFFFL), PoolChunk.runPages(finalRun), finalRun);
            this.freeBytes += runSize;
        }
        finally {
            this.runsAvailLock.unlock();
        }
        if (nioBuffer != null && this.cachedNioBuffers != null && this.cachedNioBuffers.size() < PooledByteBufAllocator.DEFAULT_MAX_CACHED_BYTEBUFFERS_PER_CHUNK) {
            this.cachedNioBuffers.offer(nioBuffer);
        }
    }

    private long collapseRuns(long handle2) {
        return this.collapseNext(this.collapsePast(handle2));
    }

    private long collapsePast(long handle2) {
        while (true) {
            int runOffset = PoolChunk.runOffset(handle2);
            int runPages = PoolChunk.runPages(handle2);
            long pastRun = this.getAvailRunByOffset(runOffset - 1);
            if (pastRun == -1L) {
                return handle2;
            }
            int pastOffset = PoolChunk.runOffset(pastRun);
            int pastPages = PoolChunk.runPages(pastRun);
            if (pastRun == handle2 || pastOffset + pastPages != runOffset) break;
            this.removeAvailRun(pastRun);
            handle2 = PoolChunk.toRunHandle(pastOffset, pastPages + runPages, 0);
        }
        return handle2;
    }

    private long collapseNext(long handle2) {
        while (true) {
            int runPages;
            int runOffset;
            long nextRun;
            if ((nextRun = this.getAvailRunByOffset((runOffset = PoolChunk.runOffset(handle2)) + (runPages = PoolChunk.runPages(handle2)))) == -1L) {
                return handle2;
            }
            int nextOffset = PoolChunk.runOffset(nextRun);
            int nextPages = PoolChunk.runPages(nextRun);
            if (nextRun == handle2 || runOffset + runPages != nextOffset) break;
            this.removeAvailRun(nextRun);
            handle2 = PoolChunk.toRunHandle(runOffset, runPages + nextPages, 0);
        }
        return handle2;
    }

    private static long toRunHandle(int runOffset, int runPages, int inUsed) {
        return (long)runOffset << 49 | (long)runPages << 34 | (long)inUsed << 33;
    }

    void initBuf(PooledByteBuf<T> buf, ByteBuffer nioBuffer, long handle2, int reqCapacity, PoolThreadCache threadCache) {
        if (PoolChunk.isSubpage(handle2)) {
            this.initBufWithSubpage(buf, nioBuffer, handle2, reqCapacity, threadCache);
        } else {
            int maxLength = PoolChunk.runSize(this.pageShifts, handle2);
            buf.init(this, nioBuffer, handle2, PoolChunk.runOffset(handle2) << this.pageShifts, reqCapacity, maxLength, this.arena.parent.threadCache());
        }
    }

    void initBufWithSubpage(PooledByteBuf<T> buf, ByteBuffer nioBuffer, long handle2, int reqCapacity, PoolThreadCache threadCache) {
        int runOffset = PoolChunk.runOffset(handle2);
        int bitmapIdx = PoolChunk.bitmapIdx(handle2);
        PoolSubpage<T> s2 = this.subpages[runOffset];
        assert (s2.isDoNotDestroy());
        assert (reqCapacity <= s2.elemSize) : reqCapacity + "<=" + s2.elemSize;
        int offset = (runOffset << this.pageShifts) + bitmapIdx * s2.elemSize;
        buf.init(this, nioBuffer, handle2, offset, reqCapacity, s2.elemSize, threadCache);
    }

    void incrementPinnedMemory(int delta) {
        assert (delta > 0);
        this.pinnedBytes.add(delta);
    }

    void decrementPinnedMemory(int delta) {
        assert (delta > 0);
        this.pinnedBytes.add(-delta);
    }

    @Override
    public int chunkSize() {
        return this.chunkSize;
    }

    @Override
    public int freeBytes() {
        if (this.unpooled) {
            return this.freeBytes;
        }
        this.runsAvailLock.lock();
        try {
            int n = this.freeBytes;
            return n;
        }
        finally {
            this.runsAvailLock.unlock();
        }
    }

    public int pinnedBytes() {
        return (int)this.pinnedBytes.value();
    }

    public String toString() {
        int freeBytes;
        if (this.unpooled) {
            freeBytes = this.freeBytes;
        } else {
            this.runsAvailLock.lock();
            try {
                freeBytes = this.freeBytes;
            }
            finally {
                this.runsAvailLock.unlock();
            }
        }
        return "Chunk(" + Integer.toHexString(System.identityHashCode(this)) + ": " + this.usage(freeBytes) + "%, " + (this.chunkSize - freeBytes) + '/' + this.chunkSize + ')';
    }

    void destroy() {
        this.arena.destroyChunk(this);
    }

    static int runOffset(long handle2) {
        return (int)(handle2 >> 49);
    }

    static int runSize(int pageShifts, long handle2) {
        return PoolChunk.runPages(handle2) << pageShifts;
    }

    static int runPages(long handle2) {
        return (int)(handle2 >> 34 & 0x7FFFL);
    }

    static boolean isUsed(long handle2) {
        return (handle2 >> 33 & 1L) == 1L;
    }

    static boolean isRun(long handle2) {
        return !PoolChunk.isSubpage(handle2);
    }

    static boolean isSubpage(long handle2) {
        return (handle2 >> 32 & 1L) == 1L;
    }

    static int bitmapIdx(long handle2) {
        return (int)handle2;
    }
}

