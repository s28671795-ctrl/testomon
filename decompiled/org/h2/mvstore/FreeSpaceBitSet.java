/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.util.BitSet;
import org.h2.mvstore.DataUtils;
import org.h2.util.MathUtils;

public class FreeSpaceBitSet {
    private static final boolean DETAILED_INFO = false;
    private final int firstFreeBlock;
    private final int blockSize;
    private final BitSet set = new BitSet();
    private int failureFlags;

    public FreeSpaceBitSet(int n, int n2) {
        this.firstFreeBlock = n;
        this.blockSize = n2;
        this.clear();
    }

    public void clear() {
        this.set.clear();
        this.set.set(0, this.firstFreeBlock);
    }

    public boolean isUsed(long l, int n) {
        int n2 = this.getBlock(l);
        int n3 = this.getBlockCount(n);
        for (int i = n2; i < n2 + n3; ++i) {
            if (this.set.get(i)) continue;
            return false;
        }
        return true;
    }

    public boolean isFree(long l, int n) {
        int n2 = this.getBlock(l);
        int n3 = this.getBlockCount(n);
        for (int i = n2; i < n2 + n3; ++i) {
            if (!this.set.get(i)) continue;
            return false;
        }
        return true;
    }

    public long allocate(int n) {
        return this.allocate(n, 0L, 0L);
    }

    long allocate(int n, long l, long l2) {
        return this.getPos(this.allocate(this.getBlockCount(n), (int)l, (int)l2, true));
    }

    long predictAllocation(int n, long l, long l2) {
        return this.allocate(n, (int)l, (int)l2, false);
    }

    boolean isFragmented() {
        return Integer.bitCount(this.failureFlags & 0xF) > 1;
    }

    private int allocate(int n, int n2, int n3, boolean bl) {
        int n4 = 0;
        int n5 = 0;
        while (true) {
            int n6 = this.set.nextClearBit(n5);
            int n7 = this.set.nextSetBit(n6 + 1);
            int n8 = n7 - n6;
            if (n7 < 0 || n8 >= n) {
                if ((n3 < 0 || n6 < n3) && n6 + n > n2) {
                    if (n3 < 0) {
                        n6 = this.getAfterLastBlock();
                        n7 = -1;
                    } else {
                        n5 = n3;
                        continue;
                    }
                }
                assert (this.set.nextSetBit(n6) == -1 || this.set.nextSetBit(n6) >= n6 + n) : "Double alloc: " + Integer.toHexString(n6) + "/" + Integer.toHexString(n) + " " + this;
                if (bl) {
                    this.set.set(n6, n6 + n);
                } else {
                    this.failureFlags <<= 1;
                    if (n7 < 0 && n4 > 4 * n) {
                        this.failureFlags |= 1;
                    }
                }
                return n6;
            }
            n4 += n8;
            n5 = n7;
        }
    }

    public void markUsed(long l, int n) {
        int n2 = this.getBlock(l);
        int n3 = this.getBlockCount(n);
        if (this.set.nextSetBit(n2) != -1 && this.set.nextSetBit(n2) < n2 + n3) {
            throw DataUtils.newMVStoreException(6, "Double mark: " + Integer.toHexString(n2) + "/" + Integer.toHexString(n3) + " " + this, new Object[0]);
        }
        this.set.set(n2, n2 + n3);
    }

    public void free(long l, int n) {
        int n2 = this.getBlock(l);
        int n3 = this.getBlockCount(n);
        assert (this.set.nextClearBit(n2) >= n2 + n3) : "Double free: " + Integer.toHexString(n2) + "/" + Integer.toHexString(n3) + " " + this;
        this.set.clear(n2, n2 + n3);
    }

    private long getPos(int n) {
        return (long)n * (long)this.blockSize;
    }

    private int getBlock(long l) {
        return (int)(l / (long)this.blockSize);
    }

    private int getBlockCount(int n) {
        return MathUtils.roundUpInt(n, this.blockSize) / this.blockSize;
    }

    int getFillRate() {
        int n = this.set.cardinality() - this.firstFreeBlock;
        int n2 = this.set.length() - this.firstFreeBlock;
        return n2 == 0 ? 0 : (int)((100L * (long)n + (long)n2 - 1L) / (long)n2);
    }

    long getFirstFree() {
        return this.getPos(this.set.nextClearBit(0));
    }

    long getLastFree() {
        return this.getPos(this.getAfterLastBlock());
    }

    int getAfterLastBlock() {
        return this.set.previousSetBit(this.set.size() - 1) + 1;
    }

    int getMovePriority(int n) {
        int n2;
        int n3 = this.set.previousClearBit(n);
        if (n3 < 0) {
            n3 = this.firstFreeBlock;
            n2 = 0;
        } else {
            n2 = n3 - this.set.previousSetBit(n3);
        }
        int n4 = this.set.nextClearBit(n);
        int n5 = this.set.nextSetBit(n4);
        if (n5 >= 0) {
            n2 += n5 - n4;
        }
        return (n4 - n3 - 1) * 1000 / (n2 + 1);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n = 0;
        while (true) {
            if (n > 0) {
                stringBuilder.append(", ");
            }
            int n2 = this.set.nextClearBit(n);
            stringBuilder.append(Integer.toHexString(n2)).append('-');
            int n3 = this.set.nextSetBit(n2 + 1);
            if (n3 < 0) break;
            stringBuilder.append(Integer.toHexString(n3 - 1));
            n = n3 + 1;
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

