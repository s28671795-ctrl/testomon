/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.concurrent.atomic.AtomicLong;
import org.h2.mvstore.type.DataType;

public final class MemoryEstimator {
    private static final int SKIP_SUM_SHIFT = 8;
    private static final int COUNTER_MASK = 255;
    private static final int SKIP_SUM_MASK = 65535;
    private static final int INIT_BIT_SHIFT = 24;
    private static final int INIT_BIT = 0x1000000;
    private static final int WINDOW_SHIFT = 8;
    private static final int MAGNITUDE_LIMIT = 7;
    private static final int WINDOW_SIZE = 256;
    private static final int WINDOW_HALF_SIZE = 128;
    private static final int SUM_SHIFT = 32;

    private MemoryEstimator() {
    }

    public static <T> int estimateMemory(AtomicLong atomicLong, DataType<T> dataType, T t2) {
        long l;
        long l2 = atomicLong.get();
        int n = MemoryEstimator.getCounter(l2);
        int n2 = MemoryEstimator.getSkipSum(l2);
        long l3 = l2 & 0x1000000L;
        long l4 = l2 >>> 32;
        int n3 = 0;
        int n4 = 0;
        if (l3 == 0L || n-- == 0) {
            n4 = 1;
            n3 = t2 == null ? 0 : dataType.getMemory(t2);
            l = ((long)n3 << 8) - l4;
            if (l3 == 0L) {
                if (++n == 256) {
                    l3 = 0x1000000L;
                }
                l4 = (l4 * (long)n + l + (long)(n >> 1)) / (long)n;
            } else {
                long l5 = l >= 0L ? l : -l;
                int n5 = MemoryEstimator.calculateMagnitude(l4, l5);
                l4 += (l >> 7 - n5) + 1L >> 1;
                n = (1 << n5) - 1 & 0xFF;
                l = (n << 8) - n2;
                n2 = (int)((long)n2 + (l + 128L >> 8));
            }
        }
        l = MemoryEstimator.updateStatsData(atomicLong, l2, n, n2, l3, l4, n4, n3);
        return MemoryEstimator.getAverage(l);
    }

    public static <T> int estimateMemory(AtomicLong atomicLong, DataType<T> dataType, T[] TArray, int n) {
        long l = atomicLong.get();
        int n2 = MemoryEstimator.getCounter(l);
        int n3 = MemoryEstimator.getSkipSum(l);
        long l2 = l & 0x1000000L;
        long l3 = l >>> 32;
        int n4 = 0;
        int n5 = 0;
        if (l2 != 0L && n2 >= n) {
            n2 -= n;
        } else {
            int n6 = n;
            while (n6-- > 0) {
                T t2;
                int n7 = (t2 = TArray[n4++]) == null ? 0 : dataType.getMemory(t2);
                n5 += n7;
                long l4 = ((long)n7 << 8) - l3;
                if (l2 == 0L) {
                    if (++n2 == 256) {
                        l2 = 0x1000000L;
                    }
                    l3 = (l3 * (long)n2 + l4 + (long)(n2 >> 1)) / (long)n2;
                    continue;
                }
                n6 -= n2;
                long l5 = l4 >= 0L ? l4 : -l4;
                int n8 = MemoryEstimator.calculateMagnitude(l3, l5);
                l3 += (l4 >> 7 - n8) + 1L >> 1;
                l4 = ((long)(n2 += (1 << n8) - 1 & 0xFF) << 8) - (long)n3;
                n3 = (int)((long)n3 + (l4 + 128L >> 8));
            }
        }
        long l6 = MemoryEstimator.updateStatsData(atomicLong, l, n2, n3, l2, l3, n4, n5);
        return (MemoryEstimator.getAverage(l6) + 8) * n;
    }

    public static int samplingPct(AtomicLong atomicLong) {
        long l = atomicLong.get();
        int n = (l & 0x1000000L) == 0L ? MemoryEstimator.getCounter(l) : 256;
        int n2 = MemoryEstimator.getSkipSum(l) + n;
        return (n * 100 + (n2 >> 1)) / n2;
    }

    private static int calculateMagnitude(long l, long l2) {
        int n;
        for (n = 0; l2 < l && n < 7; ++n, l2 <<= 1) {
        }
        return n;
    }

    private static long updateStatsData(AtomicLong atomicLong, long l, int n, int n2, long l2, long l3, int n3, int n4) {
        return MemoryEstimator.updateStatsData(atomicLong, l, MemoryEstimator.constructStatsData(l3, l2, n2, n), n3, n4);
    }

    private static long constructStatsData(long l, long l2, int n, int n2) {
        return l << 32 | l2 | (long)n << 8 | (long)n2;
    }

    private static long updateStatsData(AtomicLong atomicLong, long l, long l2, int n, int n2) {
        while (!atomicLong.compareAndSet(l, l2)) {
            l = atomicLong.get();
            long l3 = l >>> 32;
            if (n > 0) {
                l3 += (long)n2 - (l3 * (long)n + 128L >> 8);
            }
            l2 = l3 << 32 | l & 0x100FFFFL;
        }
        return l2;
    }

    private static int getCounter(long l) {
        return (int)(l & 0xFFL);
    }

    private static int getSkipSum(long l) {
        return (int)(l >> 8 & 0xFFFFL);
    }

    private static int getAverage(long l) {
        return (int)(l >>> 40);
    }
}

