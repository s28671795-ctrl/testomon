/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.jersey.internal.guava.TreeMultimap;
import org.glassfish.jersey.server.internal.monitoring.AggregatedValueObject;
import org.glassfish.jersey.server.internal.monitoring.core.SlidingWindowTrimmer;
import org.glassfish.jersey.server.internal.monitoring.core.TimeReservoir;

class AggregatingTrimmer
implements SlidingWindowTrimmer<Long> {
    private final List<TimeReservoir<AggregatedValueObject>> aggregatedReservoirListeners = new CopyOnWriteArrayList<TimeReservoir<AggregatedValueObject>>();
    private TimeReservoir<Long> timeReservoirNotifier;
    private final long startTime;
    private final TimeUnit startUnitTime;
    private final long chunkSize;
    private final AtomicBoolean locked = new AtomicBoolean(false);

    public AggregatingTrimmer(long startTime, TimeUnit startUnitTime, long chunkTimeSize, TimeUnit chunkTimeSizeUnit) {
        this.startTime = startTime;
        this.startUnitTime = startUnitTime;
        this.chunkSize = TimeUnit.NANOSECONDS.convert(chunkTimeSize, chunkTimeSizeUnit) << 8;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void trim(ConcurrentNavigableMap<Long, Long> map, long key) {
        if (!this.locked.compareAndSet(false, true)) {
            return;
        }
        TreeMultimap trimMultiMap = TreeMultimap.create();
        SortedMap trimMap = trimMultiMap.asMap();
        try {
            SortedMap headMap = map.headMap((Object)key);
            while (!headMap.isEmpty()) {
                Map.Entry entry = headMap.pollFirstEntry();
                trimMultiMap.put(entry.getKey(), entry.getValue());
            }
        }
        finally {
            this.locked.set(false);
        }
        Map.Entry firstEntry = trimMap.firstEntry();
        while (firstEntry != null) {
            long chunkLowerBound = this.lowerBound((Long)firstEntry.getKey());
            long chunkUpperBound = this.upperBound(chunkLowerBound, key);
            SortedMap chunkMap = trimMap.headMap(chunkUpperBound);
            AggregatedValueObject aggregatedValueObject = AggregatedValueObject.createFromMultiValues(chunkMap.values());
            for (TimeReservoir<AggregatedValueObject> aggregatedReservoir : this.aggregatedReservoirListeners) {
                aggregatedReservoir.update(aggregatedValueObject, chunkLowerBound >> 8, TimeUnit.NANOSECONDS);
            }
            chunkMap.clear();
            firstEntry = trimMap.firstEntry();
        }
    }

    private long upperBound(long chunkLowerBound, long key) {
        long chunkUpperBoundCandidate = chunkLowerBound + this.chunkSize;
        return chunkUpperBoundCandidate < key ? chunkUpperBoundCandidate : key;
    }

    private long lowerBound(Long key) {
        return AggregatingTrimmer.lowerBound(key, TimeUnit.NANOSECONDS.convert(this.startTime, this.startUnitTime), this.chunkSize, 8);
    }

    static long lowerBound(long key, long startTime, long chunkSize, int power) {
        long offset = startTime % chunkSize << power;
        if (key - offset >= 0L) {
            return (key - offset) / chunkSize * chunkSize + offset;
        }
        return (key - offset - chunkSize + 1L) / chunkSize * chunkSize + offset;
    }

    public void register(TimeReservoir<AggregatedValueObject> timeReservoirListener) {
        this.aggregatedReservoirListeners.add(timeReservoirListener);
    }

    @Override
    public void setTimeReservoir(TimeReservoir<Long> timeReservoirNotifier) {
        this.timeReservoirNotifier = timeReservoirNotifier;
    }

    public TimeReservoir<Long> getTimeReservoirNotifier() {
        return this.timeReservoirNotifier;
    }
}

