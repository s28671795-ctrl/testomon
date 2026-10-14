/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.cache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.h2.mvstore.DataUtils;

public class CacheLongKeyLIRS<V> {
    private long maxMemory;
    private final Segment<V>[] segments;
    private final int segmentCount;
    private final int segmentShift;
    private final int segmentMask;
    private final int stackMoveDistance;
    private final int nonResidentQueueSize;
    private final int nonResidentQueueSizeHigh;

    public CacheLongKeyLIRS(Config config) {
        this.setMaxMemory(config.maxMemory);
        this.nonResidentQueueSize = config.nonResidentQueueSize;
        this.nonResidentQueueSizeHigh = config.nonResidentQueueSizeHigh;
        DataUtils.checkArgument(Integer.bitCount(config.segmentCount) == 1, "The segment count must be a power of 2, is {0}", config.segmentCount);
        this.segmentCount = config.segmentCount;
        this.segmentMask = this.segmentCount - 1;
        this.stackMoveDistance = config.stackMoveDistance;
        this.segments = new Segment[this.segmentCount];
        this.clear();
        this.segmentShift = 32 - Integer.bitCount(this.segmentMask);
    }

    public void clear() {
        long l = this.getMaxItemSize();
        for (int i = 0; i < this.segmentCount; ++i) {
            this.segments[i] = new Segment(l, this.stackMoveDistance, 8, this.nonResidentQueueSize, this.nonResidentQueueSizeHigh);
        }
    }

    public long getMaxItemSize() {
        return Math.max(1L, this.maxMemory / (long)this.segmentCount);
    }

    private Entry<V> find(long l) {
        int n = CacheLongKeyLIRS.getHash(l);
        return this.getSegment(n).find(l, n);
    }

    public boolean containsKey(long l) {
        Entry<V> entry = this.find(l);
        return entry != null && entry.value != null;
    }

    public V peek(long l) {
        Entry<V> entry = this.find(l);
        return entry == null ? null : (V)entry.getValue();
    }

    public V put(long l, V v) {
        return this.put(l, v, this.sizeOf(v));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public V put(long l, V v, long l2) {
        Segment<V> segment;
        if (v == null) {
            throw DataUtils.newIllegalArgumentException("The value may not be null", new Object[0]);
        }
        int n = CacheLongKeyLIRS.getHash(l);
        int n2 = this.getSegmentIndex(n);
        Segment<V> segment2 = segment = this.segments[n2];
        synchronized (segment2) {
            segment = this.resizeIfNeeded(segment, n2);
            return segment.put(l, n, v, l2);
        }
    }

    private Segment<V> resizeIfNeeded(Segment<V> segment, int n) {
        int n2 = segment.getNewMapLen();
        if (n2 == 0) {
            return segment;
        }
        Segment<V> segment2 = this.segments[n];
        if (segment == segment2) {
            segment = new Segment<V>(segment, n2);
            this.segments[n] = segment;
        }
        return segment;
    }

    protected long sizeOf(V v) {
        return 16L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public V remove(long l) {
        Segment<V> segment;
        int n = CacheLongKeyLIRS.getHash(l);
        int n2 = this.getSegmentIndex(n);
        Segment<V> segment2 = segment = this.segments[n2];
        synchronized (segment2) {
            segment = this.resizeIfNeeded(segment, n2);
            return segment.remove(l, n);
        }
    }

    public long getMemory(long l) {
        Entry<V> entry = this.find(l);
        return entry == null ? 0L : entry.getMemory();
    }

    public static int getMemoryOverhead() {
        return 112;
    }

    public V get(long l) {
        int n = CacheLongKeyLIRS.getHash(l);
        Segment<V> segment = this.getSegment(n);
        Entry<V> entry = segment.find(l, n);
        return segment.get(entry);
    }

    private Segment<V> getSegment(int n) {
        return this.segments[this.getSegmentIndex(n)];
    }

    private int getSegmentIndex(int n) {
        return n >>> this.segmentShift & this.segmentMask;
    }

    static int getHash(long l) {
        int n = (int)(l >>> 32 ^ l);
        n = (n >>> 16 ^ n) * 73244475;
        n = (n >>> 16 ^ n) * 73244475;
        n = n >>> 16 ^ n;
        return n;
    }

    public long getUsedMemory() {
        long l = 0L;
        for (Segment<V> segment : this.segments) {
            l += segment.usedMemory;
        }
        return l;
    }

    public void setMaxMemory(long l) {
        DataUtils.checkArgument(l > 0L, "Max memory must be larger than 0, is {0}", l);
        this.maxMemory = l;
        if (this.segments != null) {
            long l2 = 1L + l / (long)this.segments.length;
            for (Segment<V> segment : this.segments) {
                segment.setMaxMemory(l2);
            }
        }
    }

    public long getMaxMemory() {
        return this.maxMemory;
    }

    public synchronized Set<Map.Entry<Long, V>> entrySet() {
        return this.getMap().entrySet();
    }

    public Set<Long> keySet() {
        HashSet<Long> hashSet = new HashSet<Long>();
        for (Segment<V> segment : this.segments) {
            hashSet.addAll(segment.keySet());
        }
        return hashSet;
    }

    public int sizeNonResident() {
        int n = 0;
        for (Segment<V> segment : this.segments) {
            n += segment.queue2Size;
        }
        return n;
    }

    public int sizeMapArray() {
        int n = 0;
        for (Segment<V> segment : this.segments) {
            n += segment.entries.length;
        }
        return n;
    }

    public int sizeHot() {
        int n = 0;
        for (Segment<V> segment : this.segments) {
            n += segment.mapSize - segment.queueSize - segment.queue2Size;
        }
        return n;
    }

    public long getHits() {
        long l = 0L;
        for (Segment<V> segment : this.segments) {
            l += segment.hits;
        }
        return l;
    }

    public long getMisses() {
        int n = 0;
        for (Segment<V> segment : this.segments) {
            n = (int)((long)n + segment.misses);
        }
        return n;
    }

    public int size() {
        int n = 0;
        for (Segment<V> segment : this.segments) {
            n += segment.mapSize - segment.queue2Size;
        }
        return n;
    }

    public List<Long> keys(boolean bl, boolean bl2) {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        for (Segment<V> segment : this.segments) {
            arrayList.addAll(segment.keys(bl, bl2));
        }
        return arrayList;
    }

    public List<V> values() {
        ArrayList<V> arrayList = new ArrayList<V>();
        for (long l : this.keySet()) {
            V v = this.peek(l);
            if (v == null) continue;
            arrayList.add(v);
        }
        return arrayList;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean containsValue(V v) {
        return this.getMap().containsValue(v);
    }

    public Map<Long, V> getMap() {
        HashMap<Long, V> hashMap = new HashMap<Long, V>();
        for (long l : this.keySet()) {
            V v = this.peek(l);
            if (v == null) continue;
            hashMap.put(l, v);
        }
        return hashMap;
    }

    public void putAll(Map<Long, ? extends V> map) {
        for (Map.Entry<Long, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void trimNonResidentQueue() {
        Segment<V>[] segmentArray = this.segments;
        int n = segmentArray.length;
        for (int i = 0; i < n; ++i) {
            Segment<V> segment;
            Segment<V> segment2 = segment = segmentArray[i];
            synchronized (segment2) {
                segment.trimNonResidentQueue();
                continue;
            }
        }
    }

    public static class Config {
        public long maxMemory = 1L;
        public int segmentCount = 16;
        public int stackMoveDistance = 32;
        public final int nonResidentQueueSize = 3;
        public final int nonResidentQueueSizeHigh = 12;
    }

    static class Entry<V> {
        static final int TOTAL_MEMORY_OVERHEAD = 112;
        final long key;
        V value;
        WeakReference<V> reference;
        final long memory;
        int topMove;
        Entry<V> stackNext;
        Entry<V> stackPrev;
        Entry<V> queueNext;
        Entry<V> queuePrev;
        Entry<V> mapNext;

        Entry() {
            this(0L, null, 0L);
        }

        Entry(long l, V v, long l2) {
            this.key = l;
            this.memory = l2 + 112L;
            this.value = v;
        }

        Entry(Entry<V> entry) {
            this.key = entry.key;
            this.memory = entry.memory;
            this.value = entry.value;
            this.reference = entry.reference;
            this.topMove = entry.topMove;
        }

        boolean isHot() {
            return this.queueNext == null;
        }

        V getValue() {
            return (V)(this.value == null ? this.reference.get() : this.value);
        }

        long getMemory() {
            return this.value == null ? 0L : this.memory;
        }
    }

    private static class Segment<V> {
        int mapSize;
        int queueSize;
        int queue2Size;
        long hits;
        long misses;
        final Entry<V>[] entries;
        long usedMemory;
        private final int stackMoveDistance;
        private long maxMemory;
        private final int mask;
        private final int nonResidentQueueSize;
        private final int nonResidentQueueSizeHigh;
        private final Entry<V> stack;
        private int stackSize;
        private final Entry<V> queue;
        private final Entry<V> queue2;
        private int stackMoveCounter;

        Segment(long l, int n, int n2, int n3, int n4) {
            this.setMaxMemory(l);
            this.stackMoveDistance = n;
            this.nonResidentQueueSize = n3;
            this.nonResidentQueueSizeHigh = n4;
            this.mask = n2 - 1;
            this.stack = new Entry();
            this.stack.stackNext = this.stack;
            this.stack.stackPrev = this.stack.stackNext;
            this.queue = new Entry();
            this.queue.queueNext = this.queue;
            this.queue.queuePrev = this.queue.queueNext;
            this.queue2 = new Entry();
            this.queue2.queueNext = this.queue2;
            this.queue2.queuePrev = this.queue2.queueNext;
            Entry[] entryArray = new Entry[n2];
            this.entries = entryArray;
        }

        Segment(Segment<V> segment, int n) {
            this(segment.maxMemory, segment.stackMoveDistance, n, segment.nonResidentQueueSize, segment.nonResidentQueueSizeHigh);
            Entry entry;
            this.hits = segment.hits;
            this.misses = segment.misses;
            Entry entry2 = segment.stack.stackPrev;
            while (entry2 != segment.stack) {
                entry = new Entry(entry2);
                super.addToMap(entry);
                super.addToStack(entry);
                entry2 = entry2.stackPrev;
            }
            entry2 = segment.queue.queuePrev;
            while (entry2 != segment.queue) {
                entry = this.find(entry2.key, CacheLongKeyLIRS.getHash(entry2.key));
                if (entry == null) {
                    entry = new Entry(entry2);
                    super.addToMap(entry);
                }
                super.addToQueue(this.queue, entry);
                entry2 = entry2.queuePrev;
            }
            entry2 = segment.queue2.queuePrev;
            while (entry2 != segment.queue2) {
                entry = this.find(entry2.key, CacheLongKeyLIRS.getHash(entry2.key));
                if (entry == null) {
                    entry = new Entry(entry2);
                    super.addToMap(entry);
                }
                super.addToQueue(this.queue2, entry);
                entry2 = entry2.queuePrev;
            }
        }

        int getNewMapLen() {
            int n = this.mask + 1;
            if (n * 3 < this.mapSize * 4 && n < 0x10000000) {
                return n * 2;
            }
            if (n > 32 && n / 8 > this.mapSize) {
                return n / 2;
            }
            return 0;
        }

        private void addToMap(Entry<V> entry) {
            int n = CacheLongKeyLIRS.getHash(entry.key) & this.mask;
            entry.mapNext = this.entries[n];
            this.entries[n] = entry;
            this.usedMemory += entry.getMemory();
            ++this.mapSize;
        }

        synchronized V get(Entry<V> entry) {
            V v;
            V v2 = v = entry == null ? null : (V)entry.getValue();
            if (v == null) {
                ++this.misses;
            } else {
                this.access(entry);
                ++this.hits;
            }
            return v;
        }

        private void access(Entry<V> entry) {
            if (entry.isHot()) {
                if (entry != this.stack.stackNext && entry.stackNext != null && this.stackMoveCounter - entry.topMove > this.stackMoveDistance) {
                    boolean bl = entry == this.stack.stackPrev;
                    this.removeFromStack(entry);
                    if (bl) {
                        this.pruneStack();
                    }
                    this.addToStack(entry);
                }
            } else {
                V v = entry.getValue();
                if (v != null) {
                    this.removeFromQueue(entry);
                    if (entry.reference != null) {
                        entry.value = v;
                        entry.reference = null;
                        this.usedMemory += entry.memory;
                    }
                    if (entry.stackNext != null) {
                        this.removeFromStack(entry);
                        this.convertOldestHotToCold();
                    } else {
                        this.addToQueue(this.queue, entry);
                    }
                    this.addToStack(entry);
                    this.pruneStack();
                }
            }
        }

        synchronized V put(long l, int n, V v, long l2) {
            Entry<V> entry = this.find(l, n);
            boolean bl = entry != null;
            V v2 = null;
            if (bl) {
                v2 = entry.getValue();
                this.remove(l, n);
            }
            if (l2 + 112L > this.maxMemory) {
                return v2;
            }
            entry = new Entry<V>(l, v, l2);
            int n2 = n & this.mask;
            entry.mapNext = this.entries[n2];
            this.entries[n2] = entry;
            this.usedMemory += entry.memory;
            if (this.usedMemory > this.maxMemory) {
                this.evict();
                if (this.stackSize > 0) {
                    this.addToQueue(this.queue, entry);
                }
            }
            ++this.mapSize;
            this.addToStack(entry);
            if (bl) {
                this.access(entry);
            }
            return v2;
        }

        synchronized V remove(long l, int n) {
            Entry<V> entry;
            int n2 = n & this.mask;
            Entry<V> entry2 = this.entries[n2];
            if (entry2 == null) {
                return null;
            }
            if (entry2.key == l) {
                this.entries[n2] = entry2.mapNext;
            } else {
                do {
                    entry = entry2;
                    entry2 = entry2.mapNext;
                    if (entry2 != null) continue;
                    return null;
                } while (entry2.key != l);
                entry.mapNext = entry2.mapNext;
            }
            entry = entry2.getValue();
            --this.mapSize;
            this.usedMemory -= entry2.getMemory();
            if (entry2.stackNext != null) {
                this.removeFromStack(entry2);
            }
            if (entry2.isHot()) {
                entry2 = this.queue.queueNext;
                if (entry2 != this.queue) {
                    this.removeFromQueue(entry2);
                    if (entry2.stackNext == null) {
                        this.addToStackBottom(entry2);
                    }
                }
                this.pruneStack();
            } else {
                this.removeFromQueue(entry2);
            }
            return (V)entry;
        }

        private void evict() {
            do {
                this.evictBlock();
            } while (this.usedMemory > this.maxMemory);
        }

        private void evictBlock() {
            while (this.queueSize <= this.mapSize - this.queue2Size >>> 5 && this.stackSize > 0) {
                this.convertOldestHotToCold();
            }
            while (this.usedMemory > this.maxMemory && this.queueSize > 0) {
                Entry entry = this.queue.queuePrev;
                this.usedMemory -= entry.memory;
                this.removeFromQueue(entry);
                entry.reference = new WeakReference(entry.value);
                entry.value = null;
                this.addToQueue(this.queue2, entry);
                this.trimNonResidentQueue();
            }
        }

        void trimNonResidentQueue() {
            int n = this.mapSize - this.queue2Size;
            int n2 = this.nonResidentQueueSizeHigh * n;
            int n3 = this.nonResidentQueueSize * n;
            while (this.queue2Size > n3) {
                WeakReference weakReference;
                Entry entry = this.queue2.queuePrev;
                if (this.queue2Size <= n2 && (weakReference = entry.reference) != null && weakReference.get() != null) break;
                int n4 = CacheLongKeyLIRS.getHash(entry.key);
                this.remove(entry.key, n4);
            }
        }

        private void convertOldestHotToCold() {
            Entry entry = this.stack.stackPrev;
            if (entry == this.stack) {
                throw new IllegalStateException();
            }
            this.removeFromStack(entry);
            this.addToQueue(this.queue, entry);
            this.pruneStack();
        }

        private void pruneStack() {
            Entry entry;
            while (!(entry = this.stack.stackPrev).isHot()) {
                this.removeFromStack(entry);
            }
        }

        Entry<V> find(long l, int n) {
            int n2 = n & this.mask;
            Entry<V> entry = this.entries[n2];
            while (entry != null && entry.key != l) {
                entry = entry.mapNext;
            }
            return entry;
        }

        private void addToStack(Entry<V> entry) {
            entry.stackPrev = this.stack;
            entry.stackNext = this.stack.stackNext;
            entry.stackNext.stackPrev = entry;
            this.stack.stackNext = entry;
            ++this.stackSize;
            entry.topMove = this.stackMoveCounter++;
        }

        private void addToStackBottom(Entry<V> entry) {
            entry.stackNext = this.stack;
            entry.stackPrev = this.stack.stackPrev;
            entry.stackPrev.stackNext = entry;
            this.stack.stackPrev = entry;
            ++this.stackSize;
        }

        private void removeFromStack(Entry<V> entry) {
            entry.stackPrev.stackNext = entry.stackNext;
            entry.stackNext.stackPrev = entry.stackPrev;
            entry.stackNext = null;
            entry.stackPrev = null;
            --this.stackSize;
        }

        private void addToQueue(Entry<V> entry, Entry<V> entry2) {
            entry2.queuePrev = entry;
            entry2.queueNext = entry.queueNext;
            entry2.queueNext.queuePrev = entry2;
            entry.queueNext = entry2;
            if (entry2.value != null) {
                ++this.queueSize;
            } else {
                ++this.queue2Size;
            }
        }

        private void removeFromQueue(Entry<V> entry) {
            entry.queuePrev.queueNext = entry.queueNext;
            entry.queueNext.queuePrev = entry.queuePrev;
            entry.queueNext = null;
            entry.queuePrev = null;
            if (entry.value != null) {
                --this.queueSize;
            } else {
                --this.queue2Size;
            }
        }

        synchronized List<Long> keys(boolean bl, boolean bl2) {
            ArrayList<Long> arrayList = new ArrayList<Long>();
            if (bl) {
                Entry<V> entry = bl2 ? this.queue2 : this.queue;
                Entry entry2 = entry.queueNext;
                while (entry2 != entry) {
                    arrayList.add(entry2.key);
                    entry2 = entry2.queueNext;
                }
            } else {
                Entry entry = this.stack.stackNext;
                while (entry != this.stack) {
                    arrayList.add(entry.key);
                    entry = entry.stackNext;
                }
            }
            return arrayList;
        }

        synchronized Set<Long> keySet() {
            HashSet<Long> hashSet = new HashSet<Long>();
            Entry entry = this.stack.stackNext;
            while (entry != this.stack) {
                hashSet.add(entry.key);
                entry = entry.stackNext;
            }
            entry = this.queue.queueNext;
            while (entry != this.queue) {
                hashSet.add(entry.key);
                entry = entry.queueNext;
            }
            return hashSet;
        }

        void setMaxMemory(long l) {
            this.maxMemory = l;
        }
    }
}

