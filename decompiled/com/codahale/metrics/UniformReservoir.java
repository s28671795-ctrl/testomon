/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Reservoir;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.ThreadLocalRandom;
import com.codahale.metrics.UniformSnapshot;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public class UniformReservoir
implements Reservoir {
    private static final int DEFAULT_SIZE = 1028;
    private static final int BITS_PER_LONG = 63;
    private final AtomicLong count = new AtomicLong();
    private final AtomicLongArray values;

    public UniformReservoir() {
        this(1028);
    }

    public UniformReservoir(int size) {
        this.values = new AtomicLongArray(size);
        for (int i = 0; i < this.values.length(); ++i) {
            this.values.set(i, 0L);
        }
        this.count.set(0L);
    }

    @Override
    public int size() {
        long c = this.count.get();
        if (c > (long)this.values.length()) {
            return this.values.length();
        }
        return (int)c;
    }

    @Override
    public void update(long value) {
        long c = this.count.incrementAndGet();
        if (c <= (long)this.values.length()) {
            this.values.set((int)c - 1, value);
        } else {
            long r = UniformReservoir.nextLong(c);
            if (r < (long)this.values.length()) {
                this.values.set((int)r, value);
            }
        }
    }

    private static long nextLong(long n) {
        long val;
        long bits;
        while ((bits = ThreadLocalRandom.current().nextLong() & Long.MAX_VALUE) - (val = bits % n) + (n - 1L) < 0L) {
        }
        return val;
    }

    @Override
    public Snapshot getSnapshot() {
        int s2 = this.size();
        ArrayList<Long> copy = new ArrayList<Long>(s2);
        for (int i = 0; i < s2; ++i) {
            copy.add(this.values.get(i));
        }
        return new UniformSnapshot(copy);
    }
}

